package com.ssg.wannavapibackend.repository;

import com.ssg.wannavapibackend.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository  extends JpaRepository<Product, Long> {
}
