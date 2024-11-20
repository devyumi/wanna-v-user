package com.ssg.wannavapibackend.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ProductCheckoutResponseDTO extends CheckoutResponseDTO {

    // 상품 정보 (단일 상품)
    private PaymentItemResponseDTO product;

    @Override
    public PaymentItemResponseDTO getProducts() {
        return product;
    }
}