package com.myproject.todayhouse.member.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Address {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long addressId;

    private String addressName;
    private String receiver;
    private String phoneNumber;
    private String address1;
    private String address2;
    private boolean isDefault;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Builder
    public Address(String addressName, String receiver, String phoneNumber, String address1, String address2, boolean isDefault) {
        this.addressName = addressName;
        this.receiver = receiver;
        this.phoneNumber = phoneNumber;
        this.address1 = address1;
        this.address2 = address2;
        this.isDefault = isDefault;
    }
}
