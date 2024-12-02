package com.ssg.wannavapibackend.repository;


import com.ssg.wannavapibackend.domain.Restaurant;
import com.ssg.wannavapibackend.dto.request.RestaurantSearchCond;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RestaurantRepository {
    Long save(Restaurant restaurant);

    Optional<Restaurant> findById(Long id);

    List<Restaurant> findAll(RestaurantSearchCond restaurantSearchCond);


    List<Restaurant> findSimilarRestaurantsAll(RestaurantSearchCond restaurantSearchCond);


}
