package com.ssg.wannavapibackend.dto;

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
public class PaymentRefundDTO {
    private String paymentKey;      // 결제의 키 값 (필수)
    private String cancelReason;    // 결제를 취소하는 이유 (필수)
    private Double cancelAmount;    // 취소할 금액 (값이 없으면 전액 취소)
    private String approvedAt;      // 결제 취소 승인 시각 (canceledAt)
}
