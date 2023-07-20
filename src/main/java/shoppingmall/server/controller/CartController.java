package shoppingmall.server.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import shoppingmall.server.dto.CartDetailDto;
import shoppingmall.server.dto.CartItemDto;
import shoppingmall.server.service.CartService;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    @PostMapping(value = "/cart")
    public @ResponseBody ResponseEntity addCart(@RequestBody @Validated CartItemDto cartItemDto, BindingResult bindingResult, Principal principal) {

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
    public @ResponseBody ResponseEntity getCartDetail(Principal principal) {
        String email = principal.getName();

        List<CartDetailDto> cartDetailList = cartService.getCartList(email);

        return ResponseEntity
                .ok()
                .body(cartDetailList);
    }

    // 장바구니 수량 변경
    @PatchMapping(value = "/cartItem/{cartItemId}")
    public @ResponseBody ResponseEntity updateCartItem(@PathVariable Long cartItemId, int count, Principal principal) {
        if (count <= 0) {
            return ResponseEntity
                    .badRequest()
                    .body("최소 1개이상 담아주세요.");
        } else if (!cartService.validateCartItem(cartItemId, principal.getName())) {
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body("수정 권한이 없습니다.");
        }

        cartService.updateCartItemCount(cartItemId, count);
        return ResponseEntity
                .ok()
                .body(cartItemId);
    }
}
