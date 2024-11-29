package com.ssg.wannavapibackend.service;

import com.ssg.wannavapibackend.domain.BusinessDay;
import com.ssg.wannavapibackend.domain.Restaurant;
import com.ssg.wannavapibackend.domain.Review;
import com.ssg.wannavapibackend.dto.request.RestaurantSaveDto;
import com.ssg.wannavapibackend.dto.request.RestaurantSearchCond;
import com.ssg.wannavapibackend.dto.request.RestaurantUpdateDto;

import java.util.List;

public interface RestaurantService {
    Long save(RestaurantSaveDto restaurantSaveDto);


    List<Review> findReviewsByRating(Long id, Integer rating);

    Restaurant findOne(Long id);

    List<Restaurant> findSimilarRestaurants(Long id);

    List<Restaurant> findRestaurants(RestaurantSearchCond restaurantSearchCond);


    BusinessDay findToday(Restaurant restaurant);

    void updateRestaurant(Long id , RestaurantUpdateDto restaurantUpdateDto);

    void updateBusinessStatus();

}
