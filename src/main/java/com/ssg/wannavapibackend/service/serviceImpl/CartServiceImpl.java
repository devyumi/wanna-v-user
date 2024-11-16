package com.ssg.wannavapibackend.service.serviceImpl;

import com.ssg.wannavapibackend.common.ErrorCode;
import com.ssg.wannavapibackend.domain.Cart;
import com.ssg.wannavapibackend.domain.Product;
import com.ssg.wannavapibackend.domain.User;
import com.ssg.wannavapibackend.dto.request.CartItemQuantityUpdateDTO;
import com.ssg.wannavapibackend.dto.request.CartRequestDTO;
import com.ssg.wannavapibackend.dto.response.CartResponseDTO;
import com.ssg.wannavapibackend.exception.CustomException;
import com.ssg.wannavapibackend.repository.CartRepository;
import com.ssg.wannavapibackend.repository.ProductRepository;
import com.ssg.wannavapibackend.repository.UserRepository;
import com.ssg.wannavapibackend.service.CartService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Log4j2
@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final ProductRepository productRepository;
    private final CartRepository cartRepository;
    private final UserRepository userRepository;

    private static final int MIN_QUANTITY = 1;
    private static final int MAX_QUANTITY = 99;

    /**
     * 장바구니에 상품 추가
     *
     * @param requestDTO
     */
    @Transactional
    public void addCartItem(CartRequestDTO requestDTO) {
        long userId = requestDTO.getUserId();
        long productId = requestDTO.getProductId();

        User user = userRepository.findById(userId)
            .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        Product product = productRepository.findById(productId)
            .orElseThrow(() -> new CustomException(ErrorCode.PRODUCT_NOT_FOUND));

        try {
            cartRepository.save(Cart.builder()
                .product(product)
                .user(user)
                .quantity(requestDTO.getQuantity())
                .createdAt(requestDTO.getCreatedAt())
                .build());
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new CustomException(ErrorCode.CART_ITEM_ADD_FAILED);
        }
    }

    /**
     * 장바구니 상품 리스트 조회
     * @param userId - 로그인한 유저 ID
     */
    @Transactional(readOnly = true)
    public List<CartResponseDTO> getCartItemList(Long userId) {
        List<Cart> cartList = cartRepository.findAllByUserId(userId);

        for (Cart cart : cartList)  {
            log.info(cart.toString());
        }

        return cartList.stream()
            .map(cart -> new CartResponseDTO(
                cart.getId(),
                cart.getQuantity(),
                cart.getProduct().getName(),
                cart.getProduct().getImage(),
                cart.getProduct().getFinalPrice()
            )).collect(Collectors.toList());
    }


    /**
     * 장바구니 상품 수량 변경
     *
     * @param updateDTO
     */
    @Transactional
    public void updateCartItemQuantity(CartItemQuantityUpdateDTO updateDTO) {
        long cartId = updateDTO.getCartId();

        Cart cart = cartRepository.findById(cartId)
            .orElseThrow(() -> new CustomException(ErrorCode.CART_ITEM_NOT_FOUND));

        if (MIN_QUANTITY > updateDTO.getQuantity() || updateDTO.getQuantity() > MAX_QUANTITY) {
            throw new CustomException(ErrorCode.INVALID_PRODUCT_QUANTITY);
        }

        try {
            cart.updateQuantity(updateDTO);
            cartRepository.save(cart);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new CustomException(ErrorCode.CART_ITEM_UPDATE_FAILED);
        }
    }

    @Transactional
    public void deleteCartItem(Long cartId) {
        cartRepository.findById(cartId)
            .orElseThrow(() -> new CustomException(ErrorCode.CART_ITEM_NOT_FOUND));

        try {
            cartRepository.deleteById(cartId);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new CustomException(ErrorCode.CART_ITEM_DELETE_FAILED);
        }
    }
}
