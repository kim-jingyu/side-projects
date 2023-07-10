package shoppingmall.server.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import shoppingmall.server.dto.OrderHistoryDto;
import shoppingmall.server.dto.OrderRequestDto;
import shoppingmall.server.dto.OrderResponseDto;
import shoppingmall.server.service.OrderService;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class OrderApiController {
    private final OrderService orderService;

    @PostMapping("/order")
    public @ResponseBody ResponseEntity order(@RequestBody @Validated OrderRequestDto requestDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder sb = new StringBuilder();
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for (FieldError fieldError : fieldErrors) {
                sb.append(fieldError.getDefaultMessage());
            }

            return ResponseEntity
                    .badRequest()
                    .body(sb.toString());
        }

        String email = null;
        OrderResponseDto orderResponseDto;
        try {
            orderResponseDto = orderService.order(requestDto, email);
        } catch (Exception e) {
            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }

        return ResponseEntity
                .ok()
                .body(orderResponseDto);
    }

    // 주문 이력 조회
    @GetMapping(value = {"/orders", "/orders/{page}"})
    public @ResponseBody ResponseEntity getOrderHistory(@PathVariable("page") Optional<Integer> page) {
        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 4);

        Page<OrderHistoryDto> orderHistoryDtoList = orderService.getOrderList(null, pageable);

        return ResponseEntity
                .ok()
                .body(orderHistoryDtoList);
    }
}
