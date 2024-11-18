package com.ssg.wannavapibackend.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RestaurantResponseDTO {
  private Long id;
  private Boolean isOpen;
}
