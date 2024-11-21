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
    private String image;             // 상품 이미지
    private String name;              // 상품 이름
    private Double paymentPrice;      // 상품 결제 가격

}