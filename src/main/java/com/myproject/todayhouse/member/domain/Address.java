package com.myproject.todayhouse.member.domain;

import jakarta.persistence.*;
import lombok.*;

@Embeddable
@Getter @Setter
public class Address {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long addressId;

    private String addressName;
    private String receiver;
    private String phoneNumber;
    private String address1;
    private String address2;
    private boolean isDefault;
}
