package com.ssg.wannavapibackend.repository;

import com.ssg.wannavapibackend.domain.Cart;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
    List<Cart> findAllByUserId(Long userId);
}
