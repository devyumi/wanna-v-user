package com.ssg.wannavapibackend.domain;

import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@Getter @Setter(AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode // 값 타입 비교 == 비교 가능하게 함
public class Address {
    
  private String roadAddress;
  private String landLotAddress;
  private String detailsAddress;
  private String zipCode;
}
