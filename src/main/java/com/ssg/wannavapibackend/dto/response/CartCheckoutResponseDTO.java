package com.ssg.wannavapibackend.dto.response;

import java.util.List;
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
public class CartCheckoutResponseDTO extends CheckoutResponseDTO {

    // 상품 정보 (다수 상품)
    private List<PaymentItemResponseDTO> products;

    @Override
    public List<PaymentItemResponseDTO> getProducts() {
        return products;
    }
}