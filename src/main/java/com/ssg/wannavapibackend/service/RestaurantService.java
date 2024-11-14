package com.ssg.wannavapibackend.service;

import com.ssg.wannavapibackend.dto.response.RestaurantResponseDTO;

import java.util.List;

public interface RestaurantService {
    RestaurantResponseDTO getRestaurant(Long restaurantId);
}
