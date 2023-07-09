package shoppingmall.server.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import shoppingmall.server.dto.OrderRequestDto;
import shoppingmall.server.dto.OrderResponseDto;
import shoppingmall.server.service.OrderService;

import java.security.Principal;
import java.util.List;

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
}
