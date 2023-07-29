package com.myproject.todayhouse.order.service;

import com.myproject.todayhouse.item.domain.Item;
import com.myproject.todayhouse.item.exception.ItemNotFoundException;
import com.myproject.todayhouse.item.repository.ItemRepository;
import com.myproject.todayhouse.member.domain.Member;
import com.myproject.todayhouse.member.exception.MemberNotFoundException;
import com.myproject.todayhouse.member.repository.MemberRepository;
import com.myproject.todayhouse.order.domain.Delivery;
import com.myproject.todayhouse.order.domain.OrderItem;
import com.myproject.todayhouse.order.domain.Orders;
import com.myproject.todayhouse.order.dto.request.OrderRequest;
import com.myproject.todayhouse.order.dto.response.OrderResponse;
import com.myproject.todayhouse.order.exception.OrderNotFoundException;
import com.myproject.todayhouse.order.exception.OrderNotValidatedException;
import com.myproject.todayhouse.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final ItemRepository itemRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public OrderResponse order(OrderRequest orderRequest, String email) {
        Item item = itemRepository.findById(orderRequest.getItemId())
                .orElseThrow(ItemNotFoundException::new);

        OrderItem orderItem = OrderItem.createOrderItem(item, orderRequest.getCount());

        Member member = memberRepository.findByEmail(email)
                .orElseThrow(MemberNotFoundException::new);

        Delivery delivery = Delivery.createDelivery(member.getAddress());

        Orders order = Orders.createOrder(member, delivery, orderItem);
        orderRepository.save(order);

        return OrderResponse.builder()
                .orderId(order.getOrderId())
                .totalPrice(order.totalPrice())
                .count(orderRequest.getCount())
                .build();
    }

    public List<OrderResponse> getOrderList(String email) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(MemberNotFoundException::new);

        return orderRepository.findOrderList(member.getMemberId());
    }

    public void cancelOrder(Long orderId, String email) {
        if (!validateOrder(orderId, email)) {
            throw new OrderNotValidatedException();
        }

        Orders order = orderRepository.findByOrders_OrderIdAndMember_Email(orderId, email)
                .orElseThrow(OrderNotFoundException::new);
        order.cancelOrder();
    }

    private boolean validateOrder(Long orderId, String email) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(MemberNotFoundException::new);

        Orders order = orderRepository.findById(orderId)
                .orElseThrow(OrderNotFoundException::new);
        Member orderMember = order.getMember();

        return member.getMemberId().equals(orderMember.getMemberId());
    }
}
