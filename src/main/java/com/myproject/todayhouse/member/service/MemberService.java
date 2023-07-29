package com.myproject.todayhouse.member.service;

import com.myproject.todayhouse.member.domain.Role;
import com.myproject.todayhouse.member.dto.request.MemberRequest;
import com.myproject.todayhouse.member.dto.response.MemberResponse;
import com.myproject.todayhouse.member.domain.Member;
import com.myproject.todayhouse.member.exception.MemberAlreadyExistsException;
import com.myproject.todayhouse.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    public MemberResponse saveMember(MemberRequest memberRequest, Role role) {
        validateMemberExists(memberRequest);

        Member savedMember = memberRepository.save(Member.builder()
                .memberRequest(memberRequest)
                .role(role)
                .build());

        return MemberResponse.builder()
                .member(savedMember)
                .build();
    }

    private void validateMemberExists(MemberRequest memberRequest) {
        Optional<Member> findMember = memberRepository.findByEmail(memberRequest.getEmail());
        if (findMember.isPresent()) {
            throw new MemberAlreadyExistsException();
        }
    }
}
