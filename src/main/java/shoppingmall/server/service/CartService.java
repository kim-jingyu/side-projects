package shoppingmall.server.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shoppingmall.server.dto.CartDetailDto;
import shoppingmall.server.dto.CartItemDto;
import shoppingmall.server.dto.CartOrderDto;
import shoppingmall.server.dto.OrderRequestDto;
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
    private final OrderService orderService;

    @Transactional
    // 장바구니에 상품 담기
    public Long addCart(CartItemDto cartItemDto, String email) {
        Item item = itemRepository.findById(cartItemDto.getItemId())
                .orElseThrow(EntityNotFoundException::new);
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(email));

        Cart cart = cartRepository.findByMember_MemberId(member.getMemberId());    // 현재 로그인한 회원의 장바구니
        if (cart == null) {
            cart = Cart.creatCart(member);
            cartRepository.save(cart);
        }

        // 현재 상품이 장바구니에 이미 들어가있는지 조회
        CartItem savedCartItem = cartItemRepository.findByCart_CartIdAndItem_ItemId(cart.getCartId(), item.getItemId());

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
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(email));
        Cart cart = cartRepository.findByMember_MemberId(member.getMemberId());
        if (cart == null) {
            return new ArrayList<>();
        }

        List<CartDetailDto> cartDetailDtoList = cartItemRepository.findCartDetailDtoList(cart.getCartId());

        return cartDetailDtoList;
    }

    // 장바구니 상품 - 회원 권한 확인
    public boolean validateCartItem(Long cartItemId, String email) {
        Member findMember = memberRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(email));

        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(EntityNotFoundException::new);

        Member savedMember = cartItem.getCart().getMember();

        if (!findMember.getEmail().equals(savedMember.getEmail())) {
            return false;
        }

        return true;
    }

    // 장바구니 상품 수량 변경
    @Transactional
    public void updateCartItemCount(Long cartItemId, int count) {
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(EntityNotFoundException::new);

        cartItem.updateCount(count);
    }

    // 장바구니 상품 삭제
    @Transactional
    public void deleteCartItem(Long cartItemId) {
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(EntityNotFoundException::new);
        cartItemRepository.delete(cartItem);
    }

    // 장바구니에서 여러 상품 주문
    @Transactional
    public Long orderItemsFromCart(List<CartOrderDto> cartOrderDtoList, String email) {
        // 장바구니에서 상품들 주문
        List<OrderRequestDto> orderRequestDtoList = new ArrayList<>();
        for (CartOrderDto cartOrderDto : cartOrderDtoList) {
            CartItem cartItem = cartItemRepository.findById(cartOrderDto.getCartItemId())
                    .orElseThrow(EntityNotFoundException::new);

            OrderRequestDto orderRequestDto = new OrderRequestDto();
            orderRequestDto.setItemId(cartItem.getItem().getItemId());
            orderRequestDto.setCount(cartItem.getCount());
            orderRequestDtoList.add(orderRequestDto);
        }

        Long orderId = orderService.orderItemsFromCart(orderRequestDtoList, email);

        // 주문한 상품들 장바구니에서 삭제
        for (CartOrderDto cartOrderDto : cartOrderDtoList) {
            CartItem cartItem = cartItemRepository.findById(cartOrderDto.getCartItemId())
                    .orElseThrow(EntityNotFoundException::new);

            cartItemRepository.delete(cartItem);
        }

        return orderId;
    }
}
