package com.ssg.wannavapibackend.repository;

import com.ssg.wannavapibackend.domain.ReviewTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewTagRepository extends JpaRepository<ReviewTag, Long> {

    List<ReviewTag> findAllByReviewId(Long reviewId);
}
