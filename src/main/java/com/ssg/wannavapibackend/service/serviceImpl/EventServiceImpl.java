package com.ssg.wannavapibackend.service.serviceImpl;

import com.ssg.wannavapibackend.domain.Coupon;
import com.ssg.wannavapibackend.domain.Event;
import com.ssg.wannavapibackend.domain.User;
import com.ssg.wannavapibackend.domain.UserCoupon;
import com.ssg.wannavapibackend.dto.request.EventCouponRequestDTO;
import com.ssg.wannavapibackend.dto.response.EventCouponResponseDTO;
import com.ssg.wannavapibackend.dto.response.EventListResponseDTO;
import com.ssg.wannavapibackend.repository.CouponRepository;
import com.ssg.wannavapibackend.repository.EventRepository;
import com.ssg.wannavapibackend.repository.UserCouponRepository;
import com.ssg.wannavapibackend.repository.UserRepository;
//import com.ssg.wannavapibackend.security.util.JWTUtil;
import com.ssg.wannavapibackend.service.EventService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final CouponRepository couponRepository;
    private final UserRepository userRepository;
    private final UserCouponRepository userCouponRepository;
//    private final JWTUtil jwtUtil;

    @Override
    public List<EventListResponseDTO> getEventList() {
        List<Event> events = eventRepository.findAll(Sort.by(Sort.Direction.DESC, "startDate"));;
        return events.stream().map(event -> new EventListResponseDTO(
                        event.getId(),
                        event.getTitle(),
                        event.getThumbnail(),
                        event.getStartDate(),
                        event.getEndDate())).collect(Collectors.toList());
    }

    @Override
    public EventCouponResponseDTO getEvent(Long eventId) {
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new IllegalArgumentException("이벤트 없음"));
        Coupon coupon = couponRepository.findById(eventId).orElse(null);
        return new EventCouponResponseDTO(event, coupon);
    }

    @Transactional
    public void couponDistribution(EventCouponRequestDTO eventCouponRequestDTO) {
//        Long id = jwtUtil.getUserId();

        if(eventCouponRequestDTO.getCouponId() == null)
            throw new RuntimeException("아직 쿠폰을 받을 수 없습니다.");

        //쿠폰 액티브 검사
        if(!couponRepository.findIsActiveById(eventCouponRequestDTO.getCouponId()))
            throw new RuntimeException("이벤트가 종료되었습니다.");

        //유저 확인
        if(!userRepository.existsById(1L))
            throw new RuntimeException("회원가입 후 발급이 가능합니다.");

        //유저가 쿠폰을 가지고 있는지 확인
        if(userCouponRepository.findUserCouponByCouponId(1L, eventCouponRequestDTO.getCouponId()))
            throw new RuntimeException("쿠폰이 이미 존재합니다.");

        User user = userRepository.findById(1L).orElseThrow(() -> new IllegalArgumentException("유저 없음"));
        Coupon coupon = couponRepository.findById(eventCouponRequestDTO.getCouponId()).orElseThrow(() -> new IllegalArgumentException("쿠폰 없음"));

        UserCoupon userCoupon = new UserCoupon(1L, user, coupon, false);

        userCouponRepository.save(userCoupon);

    }
}
