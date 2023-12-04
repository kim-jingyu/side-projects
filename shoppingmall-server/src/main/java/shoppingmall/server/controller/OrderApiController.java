package shoppingmall.server.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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

@Tag(name = "주문 페이지", description = "사용자가 상품을 주문할 수 있는 페이지입니다.")
@RestController
@RequiredArgsConstructor
public class OrderApiController {
    private final OrderService orderService;

    @Operation(summary = "상품 주문")
    @PostMapping("/order")
    public @ResponseBody ResponseEntity order(@RequestBody @Validated OrderRequestDto requestDto, BindingResult bindingResult, Principal principal) {
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

        String email = principal.getName();
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
    @Operation(summary = "주문 이력 조회")
    @GetMapping(value = {"/orders", "/orders/{page}"})
    public @ResponseBody ResponseEntity getOrderHistory(@PathVariable("page") Optional<Integer> page, Principal principal) {
        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 4);

        String email = principal.getName();
        Page<OrderHistoryDto> orderHistoryDtoList = orderService.getOrderList(email, pageable);

        return ResponseEntity
                .ok()
                .body(orderHistoryDtoList);
    }

    // 주문 취소
    @Operation(summary = "주문 취소")
    @DeleteMapping("/order/{orderId}")
    public @ResponseBody ResponseEntity cancelOrder(@PathVariable("orderId") Long orderId, Principal principal) {
        String email = principal.getName();

        orderService.cancelOrder(orderId, email);
        return ResponseEntity
                .ok()
                .body(orderId);
    }
}
