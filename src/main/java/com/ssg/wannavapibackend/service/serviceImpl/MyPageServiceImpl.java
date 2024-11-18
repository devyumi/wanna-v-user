package com.ssg.wannavapibackend.service.serviceImpl;

import com.ssg.wannavapibackend.domain.Address;
import com.ssg.wannavapibackend.domain.Reservation;
import com.ssg.wannavapibackend.domain.User;
import com.ssg.wannavapibackend.dto.request.MyPageUpdateDTO;
import com.ssg.wannavapibackend.dto.request.MyReservationRequestDTO;
import com.ssg.wannavapibackend.dto.response.MyLikesResponseDTO;
import com.ssg.wannavapibackend.dto.response.MyPageResponseDTO;
import com.ssg.wannavapibackend.repository.ReservationRepository;
import com.ssg.wannavapibackend.repository.UserRepository;
import com.ssg.wannavapibackend.repository.mypage.query.MyLikesDTORepository;
import com.ssg.wannavapibackend.repository.mypage.query.MyPageDTORepository;
import com.ssg.wannavapibackend.service.MyPageService;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
public class MyPageServiceImpl implements MyPageService {

    private final UserRepository userRepository;
    private final MyPageDTORepository myPageDTORepository;
    private final MyLikesDTORepository myLikesDTORepository;
    private final ReservationRepository reservationRepository;

    /**
     * 마이페이지 메인 조회
     *
     * @param userId
     * @return
     */
    @Transactional(readOnly = true)
    public MyPageResponseDTO findMyPage(Long userId) {
        return myPageDTORepository.findMyPageById(userId);
    }

    /**
     * 마이페이지 수정 폼 - 사용자 정보 조회
     *
     * @param userId
     * @return
     */
    @Transactional(readOnly = true)
    public User findUserInfo(Long userId) {
        return userRepository.findById(userId).get();
    }

    /**
     * 마이페이지 수정
     *
     * @param userId
     * @param myPageUpdateDTO
     */
    @Transactional
    public void updateMyPage(Long userId, MyPageUpdateDTO myPageUpdateDTO) {
        User user = userRepository.findById(userId).get();

        userRepository.save(User.builder()
                .id(user.getId())
                .username(user.getUsername())
                .profile(user.getProfile())
                .email(user.getEmail())
                .name(myPageUpdateDTO.getName())
                .phone(user.getPhone())
                .address(new Address(myPageUpdateDTO.getZipCode(), myPageUpdateDTO.getRoadAddress(), myPageUpdateDTO.getLandLotAddress(), myPageUpdateDTO.getDetailAddress()))
                .code(user.getCode())
                .point(user.getPoint())
                .consent(user.getConsent())
                .unregistered(user.getUnregistered())
                .createdAt(user.getCreatedAt())
                .updatedAt(LocalDateTime.now())
                .unregisteredAt(user.getUnregisteredAt())
                .build());
    }

    /**
     * 마이페이지 찜 목록 조회
     *
     * @param userId
     * @return
     */
    @Transactional(readOnly = true)
    public List<MyLikesResponseDTO> findMyLikes(Long userId) {
        return myLikesDTORepository.findMyLikesById(userId);
    }

    /**
     * 마이페이지 예약 현황 목록 조회
     *
     * @param userId
     * @param myReservationRequestDTO
     * @return
     */
    @Transactional(readOnly = true)
    public List<Reservation> findMyReservations(Long userId, MyReservationRequestDTO myReservationRequestDTO) {
        return reservationRepository.findAllById(userId, myReservationRequestDTO);
    }
}
