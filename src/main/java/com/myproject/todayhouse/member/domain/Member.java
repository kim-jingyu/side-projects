package com.myproject.todayhouse.member.domain;

import com.myproject.todayhouse.common.BaseTimeEntity;
import com.myproject.todayhouse.member.constants.Role;
import com.myproject.todayhouse.member.dto.request.MemberRequest;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Member extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String nickName;

    @Enumerated(value = EnumType.STRING)
    private Role role;

    @Builder
    public Member(MemberRequest memberRequest, Role role) {
        this.email = memberRequest.getEmail();
        this.password = memberRequest.getPassword();
        this.nickName = memberRequest.getNickName();
        this.role = role;
    }
}
