package com.ssg.wannavapibackend.dto;

import com.ssg.wannavapibackend.domain.Address;
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
public class PaymentPageInitDTO {

    private String clientKey;
    private String name;
    private String phone;
    private Address address;
}
