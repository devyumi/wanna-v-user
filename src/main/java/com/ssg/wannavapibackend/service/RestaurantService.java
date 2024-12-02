package com.ssg.wannavapibackend.service;

import com.ssg.wannavapibackend.domain.BusinessDay;
import com.ssg.wannavapibackend.domain.Restaurant;
import com.ssg.wannavapibackend.domain.Review;
import com.ssg.wannavapibackend.dto.request.RestaurantSaveDTO;
import com.ssg.wannavapibackend.dto.request.RestaurantSearchCond;
import com.ssg.wannavapibackend.dto.request.RestaurantUpdateDTO;

import java.util.List;

public interface RestaurantService {
    Long save(RestaurantSaveDTO restaurantSaveDto);


    List<Review> findReviewsByRating(Long id, Integer rating);

    Restaurant findOne(Long id);

    List<Restaurant> findSimilarRestaurants(Long id);

    List<Restaurant> findRestaurants(RestaurantSearchCond restaurantSearchCond);


    BusinessDay findToday(Restaurant restaurant);

    void updateRestaurant(Long id , RestaurantUpdateDTO restaurantUpdateDto);

    void updateBusinessStatus();

}