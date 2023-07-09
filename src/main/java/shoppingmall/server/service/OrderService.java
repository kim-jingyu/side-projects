package shoppingmall.server.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shoppingmall.server.dto.OrderRequestDto;
import shoppingmall.server.dto.OrderResponseDto;
import shoppingmall.server.entity.Item;
import shoppingmall.server.entity.Member;
import shoppingmall.server.entity.OrderItem;
import shoppingmall.server.entity.Orders;
import shoppingmall.server.repository.ItemRepository;
import shoppingmall.server.repository.MemberRepository;
import shoppingmall.server.repository.OrderRepository;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final ItemRepository itemRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public OrderResponseDto order(OrderRequestDto requestDto, String email) {
        Member member = memberRepository.findByEmail(email);
        Item item = itemRepository.findById(requestDto.getItemId())
                .orElseThrow(EntityNotFoundException::new);

        // 주문할 상품 생성
        OrderItem orderItem = OrderItem.createOrderItem(item, requestDto.getCount());

        Orders order = Orders.createOrder(member, orderItem);
        orderRepository.save(order);

        return OrderResponseDto
                .builder()
                .orderId(order.getOrderId())
                .totalPrice(order.getTotalPrice())
                .build();
    }
}
