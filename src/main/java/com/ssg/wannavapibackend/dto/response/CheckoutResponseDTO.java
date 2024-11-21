package com.ssg.wannavapibackend.dto.response;

import com.ssg.wannavapibackend.domain.Address;
import com.ssg.wannavapibackend.domain.UserCoupon;
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
public abstract class CheckoutResponseDTO {

    // toss 클라이언트 키
    private String clientKey;

    // 배송 정보
    private String name;
    private String phone;
    private Address address;

    // 유저 포인트
    private Long point;

    // 보유 쿠폰
    private List<UserCoupon> coupons;
}
