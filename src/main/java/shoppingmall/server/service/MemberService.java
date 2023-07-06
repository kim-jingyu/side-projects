package shoppingmall.server.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shoppingmall.server.dto.MemberResponseDto;
import shoppingmall.server.dto.SignUpRequest;
import shoppingmall.server.entity.Member;
import shoppingmall.server.repository.MemberRepository;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;



    @Transactional
    public MemberResponseDto saveMember(SignUpRequest requestDto, PasswordEncoder passwordEncoder) {
        Member member = Member.createMember(requestDto, passwordEncoder);
        validateDuplicateMember(member);

        Member savedMember = memberRepository.save(member);

        return MemberResponseDto
                .builder()
                .memberName(savedMember.getMemberName())
                .build();
    }

    private void validateDuplicateMember(Member member) {
        Member findMember = memberRepository.findByEmail(member.getEmail());
        if (findMember != null) {
            throw new IllegalStateException("이미 가입된 회원입니다.");
        }
    }
}
