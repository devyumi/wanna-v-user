package com.ssg.wannavapibackend.service;

import com.ssg.wannavapibackend.dto.request.ReviewSaveDTO;

public interface ReviewService {

    void saveReview(Long userId, ReviewSaveDTO reviewSaveDTO);
}
