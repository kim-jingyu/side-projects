package shoppingmall.server.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import shoppingmall.server.dto.CartDetailDto;
import shoppingmall.server.dto.CartItemDto;
import shoppingmall.server.dto.CartOrderDto;
import shoppingmall.server.service.CartService;

import java.security.Principal;
import java.util.List;

@Tag(name = "장바구니 페이지", description = "사용자가 장바구니를 이용할 수 있는 페이지입니다.")
@RestController
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    @Operation(summary = "장바구니에 상품 등록")
    @PostMapping(value = "/cart")
    public ResponseEntity addCart(@RequestBody @Validated CartItemDto cartItemDto, BindingResult bindingResult, Principal principal) {

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
    @Operation(summary = "장바구니 상품 목록", description = "장바구니에 담긴 상품 목록 조회")
    @GetMapping(value = "/cart")
    public ResponseEntity getCartDetail(Principal principal) {
        String email = principal.getName();

        List<CartDetailDto> cartDetailList = cartService.getCartList(email);

        return ResponseEntity
                .ok()
                .body(cartDetailList);
    }

    // 장바구니 수량 변경
    @Operation(summary = "장바구니 상품 수량 변경", description = "장바구니에 담긴 상품 수량 변경")
    @PatchMapping(value = "/cartItem/{cartItemId}")
    public ResponseEntity updateCartItem(@PathVariable Long cartItemId, int count, Principal principal) {
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

    @Operation(summary = "장바구니 상품 삭제", description = "장바구니에 담긴 상품을 삭제")
    @DeleteMapping(value = "/cartItem/{cartItemId}")
    public ResponseEntity deleteCartItem(@PathVariable Long cartItemId, Principal principal) {
        if (!cartService.validateCartItem(cartItemId, principal.getName())) {
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body("수정 권한이 없습니다.");
        }

        cartService.deleteCartItem(cartItemId);
        return ResponseEntity
                .ok()
                .body(cartItemId);
    }

    // 장바구니에서 상품 주문
    @Operation(summary = "장바구니 상품 주문", description = "장바구니에 담긴 상품을 주문")
    @PostMapping(value = "/cart/orders")
    public ResponseEntity orderCartItem(@RequestBody CartOrderDto cartOrderDto, Principal principal) {
        List<CartOrderDto> cartOrderDtoList = cartOrderDto.getCartOrderDtoList();

        if (cartOrderDtoList.isEmpty()) {
            return ResponseEntity
                    .badRequest()
                    .body("주문할 상품을 선택해주세요.");
        }

        for (CartOrderDto cartOrder : cartOrderDtoList) {
            if (!cartService.validateCartItem(cartOrder.getCartItemId(), principal.getName())) {
                return ResponseEntity
                        .status(HttpStatus.FORBIDDEN)
                        .body("주문 권한이 없습니다.");
            }
        }

        Long orderId = cartService.orderItemsFromCart(cartOrderDtoList, principal.getName());
        return ResponseEntity
                .ok()
                .body(orderId);
    }
}
