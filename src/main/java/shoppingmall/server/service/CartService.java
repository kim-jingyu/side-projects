package shoppingmall.server.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shoppingmall.server.dto.CartDetailDto;
import shoppingmall.server.dto.CartItemDto;
import shoppingmall.server.entity.Cart;
import shoppingmall.server.entity.CartItem;
import shoppingmall.server.entity.Item;
import shoppingmall.server.entity.Member;
import shoppingmall.server.repository.CartItemRepository;
import shoppingmall.server.repository.CartRepository;
import shoppingmall.server.repository.ItemRepository;
import shoppingmall.server.repository.MemberRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CartService {
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;

    @Transactional
    // 장바구니에 상품 담기
    public Long addCart(CartItemDto cartItemDto, String email) {
        Item item = itemRepository.findById(cartItemDto.getItemId())
                .orElseThrow(EntityNotFoundException::new);
        Member member = memberRepository.findByEmail(email);

        Cart cart = cartRepository.findByMemberId(member.getMemberId());    // 현재 로그인한 회원의 장바구니
        if (cart == null) {
            cart = Cart.creatCart(member);
            cartRepository.save(cart);
        }

        // 현재 상품이 장바구니에 이미 들어가있는지 조회
        CartItem savedCartItem = cartItemRepository.findByCartIdAndItemId(cart.getCartId(), item.getItemId());

        if (savedCartItem != null) {
            // 장바구니에 이미 있던 상품일 경우
            savedCartItem.addCount(cartItemDto.getCount());
            return savedCartItem.getCartItemId();
        } else {
            // 장바구니에 새로 담기는 상품일 경우
            CartItem cartItem = CartItem.createCartItem(cart, item, cartItemDto.getCount());
            cartItemRepository.save(cartItem);
            return cartItem.getCartItemId();
        }
    }

    // 장바구니에 들어있는 상품 조회
    public List<CartDetailDto> getCartList(String email) {
        Member member = memberRepository.findByEmail(email);
        Cart cart = cartRepository.findByMemberId(member.getMemberId());
        if (cart == null) {
            return new ArrayList<>();
        }

        List<CartDetailDto> cartDetailDtoList = cartItemRepository.findCartDetailDtoList(cart.getCartId());

        return cartDetailDtoList;
    }
}
