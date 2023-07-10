package shoppingmall.server.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import shoppingmall.server.dto.CartDetailDto;
import shoppingmall.server.dto.CartItemDto;
import shoppingmall.server.service.CartService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    @PostMapping(value = "/cart")
    public @ResponseBody ResponseEntity addCart(@RequestBody @Validated CartItemDto cartItemDto, BindingResult bindingResult) {

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
        Long cartItemId;

        try {
            cartItemId = cartService.addCart(cartItemDto, email);
        } catch (Exception e) {
            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }

        return ResponseEntity
                .ok()
                .body(cartItemId);
    }

    // 장바구니 목록 조회
    @GetMapping(value = "/cart")
    public @ResponseBody ResponseEntity getCartDetail() {
        List<CartDetailDto> cartDetailList = cartService.getCartList(null);

        return ResponseEntity
                .ok()
                .body(cartDetailList);
    }
}
