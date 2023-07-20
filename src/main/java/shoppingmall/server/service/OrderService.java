package shoppingmall.server.service;

import com.querydsl.core.types.Order;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shoppingmall.server.dto.OrderHistoryDto;
import shoppingmall.server.dto.OrderItemDto;
import shoppingmall.server.dto.OrderRequestDto;
import shoppingmall.server.dto.OrderResponseDto;
import shoppingmall.server.entity.*;
import shoppingmall.server.repository.ItemImgRepository;
import shoppingmall.server.repository.ItemRepository;
import shoppingmall.server.repository.MemberRepository;
import shoppingmall.server.repository.OrderRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final ItemRepository itemRepository;
    private final MemberRepository memberRepository;
    private final ItemImgRepository itemImgRepository;

    @Transactional
    public OrderResponseDto order(OrderRequestDto requestDto, String email) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(email));
        Item item = itemRepository.findById(requestDto.getItemId())
                .orElseThrow(EntityNotFoundException::new);

        // 주문할 상품 생성
        OrderItem orderItem = OrderItem.createOrderItem(item, requestDto.getCount());

        Orders order = Orders.createOrder(member, List.of(orderItem));
        orderRepository.save(order);

        return OrderResponseDto
                .builder()
                .orderId(order.getOrderId())
                .totalPrice(order.getTotalPrice())
                .memberName(order.getMember().getMemberName())
                .build();
    }

    // 주문 목록 조회
    public Page<OrderHistoryDto> getOrderList(String email, Pageable pageable) {
        List<Orders> orderList = orderRepository.findByMember_EmailOrderByOrderDateDesc(email, pageable);
        Long totalCount = orderRepository.countByMember_Email(email);

        List<OrderHistoryDto> orderHistoryDtoList = new ArrayList<>();

        for (Orders order : orderList) {
            OrderHistoryDto orderHistoryDto = OrderHistoryDto.builder().order(order).build();
            List<OrderItem> orderItems = order.getOrderItems();
            for (OrderItem orderItem : orderItems) {
                ItemImg itemImg = itemImgRepository.findByItem_ItemIdAndRepresentYn(orderItem.getItem().getItemId(), "Y");
                OrderItemDto orderItemDto = new OrderItemDto(orderItem, itemImg.getStoredFileUrl());
                orderHistoryDto.addOrderItemDto(orderItemDto);
            }
            orderHistoryDtoList.add(orderHistoryDto);
        }

        return new PageImpl<>(orderHistoryDtoList, pageable, totalCount);
    }

    // 주문 취소
    public void cancelOrder(Long orderId, String email) {
        Orders order = orderRepository.findByMember_EmailAndOrderId(email, orderId)
                .orElseThrow(EntityNotFoundException::new);
        order.cancelOrder();
    }

    public boolean validateOrder(Long orderId, String email) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(email));
        Orders order = orderRepository.findById(orderId)
                .orElseThrow(EntityNotFoundException::new);
        Member savedMember = order.getMember();

        if (!member.getEmail().equals(savedMember.getEmail())) return false;

        return true;
    }

    // 장바구니에서 상품 주문하기
    public Long orderItemsFromCart(List<OrderRequestDto> orderRequestDtoList, String email) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(email));
        List<OrderItem> orderItemList = new ArrayList<>();

        for (OrderRequestDto orderRequestDto : orderRequestDtoList) {
            Item item = itemRepository.findById(orderRequestDto.getItemId())
                    .orElseThrow(EntityNotFoundException::new);

            OrderItem orderItem = OrderItem.createOrderItem(item, orderRequestDto.getCount());
            orderItemList.add(orderItem);
        }

        Orders order = Orders.createOrder(member, orderItemList);
        orderRepository.save(order);

        return order.getOrderId();
    }
}
