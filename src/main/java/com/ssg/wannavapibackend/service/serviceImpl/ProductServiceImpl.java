package com.ssg.wannavapibackend.service.serviceImpl;

import com.ssg.wannavapibackend.common.ErrorCode;
import com.ssg.wannavapibackend.domain.Cart;
import com.ssg.wannavapibackend.domain.Product;
import com.ssg.wannavapibackend.domain.User;
import com.ssg.wannavapibackend.dto.request.CartRequestDTO;
import com.ssg.wannavapibackend.dto.response.ProductResponseDTO;
import com.ssg.wannavapibackend.exception.CustomException;
import com.ssg.wannavapibackend.repository.CartRepository;
import com.ssg.wannavapibackend.repository.ProductRepository;
import com.ssg.wannavapibackend.repository.UserRepository;
import com.ssg.wannavapibackend.service.ProductService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Log4j2
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CartRepository cartRepository;
    private final UserRepository userRepository;

    /**
     * 상품 전체 조회
     */
    @Transactional(readOnly = true)
    public List<ProductResponseDTO> getProductList() {
        List<Product> products = productRepository.findAll(Sort.by(Direction.DESC, "id"));

        return products.stream()
            .map(product -> new ProductResponseDTO(product.getId(), product.getName(),
                product.getImage(), product.getSellingPrice(), product.getDiscountRate(),
                product.getFinalPrice()))
            .collect(Collectors.toList());
    }

    /**
     * 상품 상세 조회
     *
     * @param productId → 상품 ID
     */
    @Transactional(readOnly = true)
    public Product getProduct(Long productId) {
        return productRepository.findById(productId)
            .orElseThrow(() -> new CustomException(ErrorCode.PRODUCT_NOT_FOUND));
    }

    /**
     * 장바구니에 상품 추가
     *
     * @param requestDTO
     */
    @Transactional(readOnly = true)
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
            throw new CustomException(ErrorCode.CART_ADD_FAILED);
        }
    }
}
