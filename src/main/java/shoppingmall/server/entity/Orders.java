package shoppingmall.server.entity;

import jakarta.persistence.*;
import lombok.Getter;
import shoppingmall.server.constant.OrderStatus;

import java.time.LocalDateTime;

@Entity
@Table(name = "order")
@Getter
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private LocalDateTime orderDate;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
