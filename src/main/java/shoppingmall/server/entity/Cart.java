package shoppingmall.server.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class Cart extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cartId;

    @OneToOne
    @JoinColumn(name = "member_id")
    private Member member;
}
