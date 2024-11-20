package com.ssg.wannavapibackend.dto.response;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PaymentItemResponseDTO {

    private String image;           // 상품 이미지
    private String name;            // 상품 이름
    private Integer quantity;       // 상품 구매 수량
    private BigDecimal finalPrice;  // 상품별 가격
}
