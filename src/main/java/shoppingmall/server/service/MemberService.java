package shoppingmall.server.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shoppingmall.server.constant.Role;
import shoppingmall.server.dto.MemberResponseDto;
import shoppingmall.server.dto.SignUpRequest;
import shoppingmall.server.entity.Member;
import shoppingmall.server.repository.MemberRepository;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class MemberService {
    private final MemberRepository memberRepository;

    @Transactional
    public MemberResponseDto saveMember(SignUpRequest requestDto, Role role) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();  // 패스워드 암호화

        Member member;
        if (role.equals(Role.USER)) {
            member = Member.createUserMember(requestDto, bCryptPasswordEncoder);
        } else if (role.equals(Role.ADMIN)) {
            member = Member.createAdminMember(requestDto, bCryptPasswordEncoder);
        } else {
            member = null;
        }

        validateDuplicateMember(member);

        Member savedMember = memberRepository.save(member);

        return MemberResponseDto
                .builder()
                .memberName(savedMember.getMemberName())
                .email(savedMember.getEmail())
                .build();
    }

    public Member findById(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("회원 정보가 없습니다!"));
    }

    private void validateDuplicateMember(Member member) {
        Member findMember = memberRepository.findByEmail(member.getEmail())
                .orElse(null);
        if (findMember != null) {
            throw new IllegalStateException("이미 가입된 회원입니다.");
        }
    }
}
