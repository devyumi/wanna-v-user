package com.ssg.wannavapibackend.repository;

import com.ssg.wannavapibackend.domain.UserCoupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserCouponRepository extends JpaRepository<UserCoupon, Long> {

    @Query("SELECT uc FROM UserCoupon uc " +
            "JOIN uc.coupon c " +
            "JOIN c.event e " +
            "WHERE uc.user.id = :userId " +
            "AND e.endDate >= CURRENT_TIMESTAMP " +
            "AND uc.used = false")
    List<UserCoupon> findAllByUserIdAndEndDate(Long userId);
}
