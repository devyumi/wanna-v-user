package com.ssg.wannavapibackend.dto.request;

import com.ssg.wannavapibackend.common.Status;
import com.ssg.wannavapibackend.domain.Address;
import com.ssg.wannavapibackend.dto.PaymentProductDTO;
import java.time.LocalDateTime;
import java.util.List;
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
public class ProductPaymentRequestDTO {

    private String orederId;
    private Double actualPrice;
    private Double finalPrice;
    private Integer pointsUsed;
    private Double finalDiscountRate;
    private Double finalDiscountAmount;
    private Long couponCode;
    private Status status;
    private Address address;
    private String note;

    private List<PaymentProductDTO> products;

    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();
}
