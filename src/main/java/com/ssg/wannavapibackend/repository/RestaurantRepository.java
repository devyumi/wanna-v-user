package com.ssg.wannavapibackend.repository;

import com.ssg.wannavapibackend.domain.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

    Restaurant findByNameContaining(String name);

    Restaurant findByNameContainingAndNameContaining(String name1, String name2);
}
