package com.myproject.todayhouse.order.domain;

import com.myproject.todayhouse.common.BaseTimeEntity;
import com.myproject.todayhouse.member.domain.Address;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Delivery extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long deliveryId;

    @Enumerated(value = EnumType.STRING)
    private DeliveryStatus deliveryStatus;

    @Embedded
    private Address deliveryAddress;

    @OneToOne(mappedBy = "delivery")
    private Orders order;

    private Delivery(DeliveryStatus deliveryStatus, Address deliveryAddress) {
        this.deliveryStatus = deliveryStatus;
        this.deliveryAddress = deliveryAddress;
    }

    public void setOrder(Orders order) {
        this.order = order;
    }

    public static Delivery createDelivery(Address deliveryAddress) {
        return new Delivery(DeliveryStatus.READY, deliveryAddress);
    }
}
