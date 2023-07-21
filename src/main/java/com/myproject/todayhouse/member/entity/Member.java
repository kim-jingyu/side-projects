package com.myproject.todayhouse.member.entity;

import com.myproject.todayhouse.common.BaseTimeEntity;
import com.myproject.todayhouse.member.constants.Role;
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
    public Member(String email, String password, String nickName, Role role) {
        this.email = email;
        this.password = password;
        this.nickName = nickName;
        this.role = role;
    }
}
