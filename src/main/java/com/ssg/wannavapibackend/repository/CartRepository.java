package com.ssg.wannavapibackend.repository;

import com.ssg.wannavapibackend.domain.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {

}
