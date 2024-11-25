package com.ssg.wannavapibackend.service.serviceImpl;

import com.ssg.wannavapibackend.domain.Review;
import com.ssg.wannavapibackend.domain.ReviewTag;
import com.ssg.wannavapibackend.dto.request.FileDTO;
import com.ssg.wannavapibackend.dto.request.ReviewSaveDTO;
import com.ssg.wannavapibackend.repository.ReviewRepository;
import com.ssg.wannavapibackend.repository.ReviewTagRepository;
import com.ssg.wannavapibackend.repository.TagRepository;
import com.ssg.wannavapibackend.repository.UserRepository;
import com.ssg.wannavapibackend.service.BadWordService;
import com.ssg.wannavapibackend.service.FileService;
import com.ssg.wannavapibackend.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final FileService fileService;
    private final BadWordService badWordService;
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final TagRepository tagRepository;
    private final ReviewTagRepository reviewTagRepository;

    @Value("${file.upload.review}")
    private String path;

    /**
     * 리뷰 저장
     *
     * @param userId
     * @param reviewSaveDTO
     */
    @Transactional
    public void saveReview(Long userId, ReviewSaveDTO reviewSaveDTO) {
        String imgUrl = "";

        if (!reviewSaveDTO.getFiles().get(0).isEmpty()) {
            List<FileDTO> files = fileService.uploadFiles(reviewSaveDTO.getFiles(), path);
            imgUrl = createImageUrl(files);
        }

        Review review = reviewRepository.save(Review.builder()
                .restaurant(reviewSaveDTO.getRestaurant())
                .user(userRepository.findById(userId).get())
                .rating(reviewSaveDTO.getRating())
                .content(badWordService.changeBadWord(reviewSaveDTO.getContent()))
                .image(imgUrl.equals("") ? null : imgUrl)
                .visitDate(reviewSaveDTO.getVisitDate())
                .isActive(true)
                .createdAt(LocalDateTime.now())
                .build());

        for (String tagName : reviewSaveDTO.getTagNames()) {
            reviewTagRepository.save(ReviewTag.builder()
                    .review(review)
                    .tag(tagRepository.findByName(tagName))
                    .build());
        }
    }

    /**
     * DB에 저장되는 imgUrl 생성 - "url,url,url" 형식
     *
     * @param fileDTOS
     * @return
     */
    private String createImageUrl(List<FileDTO> fileDTOS) {
        StringBuilder imgUrl = new StringBuilder();
        for (FileDTO fileDTO : fileDTOS) {
            imgUrl.append(fileDTO.getUploadFileUrl()).append(",");
        }
        return imgUrl.deleteCharAt(imgUrl.length() - 1).toString();
    }
}
