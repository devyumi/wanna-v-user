package com.ssg.wannavapibackend.repository;

import com.ssg.wannavapibackend.domain.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    List<Payment> findAllByUserIdOrderByCreatedAtDesc(Long userId);
}
