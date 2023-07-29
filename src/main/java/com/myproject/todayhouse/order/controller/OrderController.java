package com.myproject.todayhouse.order.controller;

import com.myproject.todayhouse.order.dto.request.OrderRequest;
import com.myproject.todayhouse.order.dto.response.OrderResponse;
import com.myproject.todayhouse.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/order")
    public ResponseEntity order(@RequestBody OrderRequest orderRequest, BindingResult bindingResult, String email) { // 나중에 Principal
        OrderResponse orderResponse = orderService.order(orderRequest, email);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(orderResponse);
    }

    @GetMapping("/orders")
    public ResponseEntity getOrderList(String email) {
        return ResponseEntity
                .ok(orderService.getOrderList(email));
    }
}
