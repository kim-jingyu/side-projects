package com.myproject.todayhouse.member.dto.response;

import com.myproject.todayhouse.member.constants.Role;
import com.myproject.todayhouse.member.domain.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MemberResponse {
    private Long memberId;
    private String email;
    private String password;
    private String nickName;
    private Role role;

    @Builder
    public MemberResponse(Member member) {
        this.memberId = member.getMemberId();
        this.email = member.getEmail();
        this.password = member.getPassword();
        this.nickName = member.getNickName();
        this.role = member.getRole();
    }
}
