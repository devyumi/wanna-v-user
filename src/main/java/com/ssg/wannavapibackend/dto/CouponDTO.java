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
public class CouponDTO {
    private Long id;
    private String name;            // 쿠폰 명
    private String type;            // 타입 (F (Fixed; 정액 쿠폰), P (Percentage; 정률 쿠폰))
    private Integer discountAmount; // 정액 쿠폰 할인액
    private Integer discountRate;   // 정률 쿠폰 할인율
    private String endDate;         // 쿠폰 종료일
}
