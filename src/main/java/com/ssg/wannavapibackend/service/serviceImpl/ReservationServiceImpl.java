package com.ssg.wannavapibackend.service.serviceImpl;

import com.ssg.wannavapibackend.config.TossPaymentConfig;
import com.ssg.wannavapibackend.domain.Reservation;
import com.ssg.wannavapibackend.domain.Restaurant;
import com.ssg.wannavapibackend.domain.Seat;
import com.ssg.wannavapibackend.domain.User;
import com.ssg.wannavapibackend.dto.request.ReservationRequestDTO;
import com.ssg.wannavapibackend.dto.response.ReservationDTO;
import com.ssg.wannavapibackend.dto.response.ReservationPaymentResponseDTO;
import com.ssg.wannavapibackend.dto.response.ReservationSaveResponseDTO;
import com.ssg.wannavapibackend.dto.response.ReservationDateResponseDTO;
import com.ssg.wannavapibackend.repository.ReservationRepository;
import com.ssg.wannavapibackend.repository.RestaurantRepository;
import com.ssg.wannavapibackend.repository.UserRepository;
import com.ssg.wannavapibackend.security.util.JWTUtil;
import com.ssg.wannavapibackend.service.ReservationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.*;
import java.util.stream.Collectors;

@Log4j2
@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;
    private final RestaurantRepository restaurantRepository;
    private final UserRepository userRepository;
    private final TossPaymentConfig tossPaymentConfig;
    private final JWTUtil jwtUtil;

    /**
     * 한 유저의 예약 전체 조회(마이페이지)
     */
    @Override
    public List<ReservationDTO> getReservationList(Long userId) {
        List<Reservation> reservations = reservationRepository.findAllByUserId(userId);

        return reservations.stream()
                .map(reservation -> new ReservationDTO(
                        reservation.getId(),
                        reservation.getUser(),
                        reservation.getRestaurant(),
                        reservation.getPayment(),
                        reservation.getGuest(),
                        reservation.getReservationDate(),
                        reservation.getReservationTime()
                )).collect(Collectors.toList());
    }

    /**
     * 예약 상세 조회
     */
    @Override
    public ReservationDTO getReservation(Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId).orElseThrow(() -> new IllegalArgumentException("Invalid ID value: " + reservationId));

        return new ReservationDTO(
                reservation.getId(),
                reservation.getUser(),
                reservation.getRestaurant(),
                reservation.getPayment(),
                reservation.getGuest(),
                reservation.getReservationDate(),
                reservation.getReservationTime()
        );
    }

    /**
     * 예약하기를 눌렀을 때 저장된 예약 데이터 조회
     */

    @Override
    public ReservationPaymentResponseDTO getReservationPayment(ReservationRequestDTO reservationRequestDTO) {

        if(reservationRepository.existsByMyReservaion(jwtUtil.getUserId(), reservationRequestDTO.getRestaurantId(), reservationRequestDTO.getSelectDate()))
            throw new RuntimeException("하루에 한 번만 예약이 가능합니다!");

        Restaurant restaurant = restaurantRepository.findById(reservationRequestDTO.getRestaurantId()).orElseThrow(() -> new IllegalArgumentException("Invalid ID value: "));

        LocalDateTime dateTime = LocalDateTime.of(reservationRequestDTO.getSelectDate(), reservationRequestDTO.getSelectTime());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일");
        String formattedDate = reservationRequestDTO.getSelectDate().format(formatter);

        String dayOfWeek = dateTime.getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.KOREAN);

        String amPm = dateTime.getHour() < 12 ? "오전" : "오후";

        return ReservationPaymentResponseDTO.builder()
                .reservationId(jwtUtil.getUserId())
                .userId(jwtUtil.getUserId())
                .restaurantId(reservationRequestDTO.getRestaurantId())
                .restaurantName(restaurant.getName())
                .roadAddress(restaurant.getAddress().getRoadAddress())
                .guestAccount(reservationRequestDTO.getSelectGuest())
                .reservationDate(formattedDate)
                .reservationTime(reservationRequestDTO.getSelectTime())
                .penalty(reservationRequestDTO.getSelectGuest() * 10000)
                .dayOfWeek(dayOfWeek)
                .amPm(amPm)
                .clientKey(tossPaymentConfig.getTossClientKey())
                .build();
    }

    /**
     * 예약 등록
     */

    @Transactional
    public ReservationSaveResponseDTO saveReservation(ReservationRequestDTO reservationRequestDTO) {

        Restaurant restaurant = restaurantRepository.findById(reservationRequestDTO.getRestaurantId()).orElseThrow(() -> new IllegalArgumentException("식당이 없습니다."));

        User user = userRepository.findById(jwtUtil.getUserId()).orElseThrow(() -> new IllegalArgumentException("유저가 없습니다."));

        if (reservationRepository.existsByMyReservaion(user.getId(), reservationRequestDTO.getRestaurantId(), reservationRequestDTO.getSelectDate()))
            throw new IllegalArgumentException("당일 예약은 한 번만 가능합니다.");

        Reservation reservation = new Reservation(null, user, restaurant, null, reservationRequestDTO.getSelectGuest(),true, reservationRequestDTO.getSelectDate(), reservationRequestDTO.getSelectTime(),  LocalDateTime.now(), null);

        Reservation reservationComplete = reservationRepository.save(reservation);

        return new ReservationSaveResponseDTO(
                reservationComplete.getId(),
                reservationComplete.getRestaurant().getIsPenalty(),
                reservationRepository.existsById(reservation.getId()));
    }


    /**
     * 예약하기 : 예약 날짜를 선택했을 때 예약 시간 버튼 생성
     */
    @Override
    public ReservationDateResponseDTO getReservationTime(ReservationRequestDTO reservationRequestDTO) {
        Restaurant restaurant = restaurantRepository.findById(reservationRequestDTO.getRestaurantId()).orElseThrow(() -> new IllegalArgumentException("식당이 없습니다."));

        List<Reservation> reservations = reservationRepository.findAllByRestaurantId(reservationRequestDTO.getRestaurantId());

        List<LocalTime> filteredTime;

        int guest = 0;

        if(reservationRequestDTO.getSelectTime() == null) {
            filteredTime = filterAvailableTimes(reservations, reservationRequestDTO, restaurant);
            return new ReservationDateResponseDTO(reservationRequestDTO.getRestaurantId(),null, reservationRequestDTO.getSelectDate(),filteredTime, restaurant.getIsPenalty());
        }
        else {
            guest = calRemainingGuest(reservations, reservationRequestDTO, null, null);
            return new ReservationDateResponseDTO(reservationRequestDTO.getRestaurantId(), guest, reservationRequestDTO.getSelectDate(),null, restaurant.getIsPenalty());
        }
    }

    @Override
    public Boolean getPenalty(Long restaurantId) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId).orElseThrow(() -> new IllegalArgumentException("식당이 없습니다."));
        return restaurant.getIsPenalty();
    }

    /**
     * 선택한 날짜 및 시간의 예약 인원 수 확인
     */
    public int calRemainingGuest(List<Reservation> reservations, ReservationRequestDTO reservationRequestDTO, LocalTime localTime, LocalDate curDate) {
        if(reservations.isEmpty()){
            Restaurant restaurant = restaurantRepository.findById(reservationRequestDTO.getRestaurantId()).orElseThrow(() -> new IllegalArgumentException("식당이 없습니다."));

            return restaurant.getSeats().stream()
                    .mapToInt(seat -> seat.getSeatCount() * seat.getSeatCapacity())
                    .sum();
        }

        int totalReservationGuest = reservations.stream().map(Reservation::getRestaurant).distinct()
                .flatMap(reservation -> reservation.getSeats().stream())
                .mapToInt(seat -> seat.getSeatCount() * seat.getSeatCapacity())
                .sum();

        int calGuest = 0;

        LocalDate reservationDate = reservationRequestDTO.getSelectDate();
        LocalTime reservationTime = reservationRequestDTO.getSelectTime();

        Map<Integer, Integer> tables = null;

        for(Reservation reservation : reservations){
            if (tables == null)
                tables = new HashMap<>();

            for(Seat seat : reservation.getRestaurant().getSeats())
                tables.put(seat.getSeatCapacity(), seat.getSeatCount());

            //달력에서 선택한 예약 날짜의 인원 수를 계산하는 로직
            if(reservationDate.equals(reservation.getReservationDate())
                    && reservationTime == null){

                //각 시간 마다 순회해야 한다.
                if(!localTime.equals(reservation.getReservationTime()))
                    continue;

                int guest = reservation.getGuest();

                List<Integer> sortedCapacities = tables.keySet().stream().sorted(Comparator.reverseOrder()).toList();

                for (int capacity : sortedCapacities) {
                    while (guest > 0 && tables.containsKey(capacity) && tables.get(capacity) > 0) {
                        if (guest <= capacity) {
                            tables.put(capacity, tables.get(capacity) - 1);
                            calGuest += guest;
                            guest = 0;
                        }
                        else {
                            tables.put(capacity, tables.get(capacity) - 1);
                            calGuest += capacity;
                            guest -= capacity;
                        }
                    }
                    if(guest <= 0)
                        break;
                }
            }

            else if(reservationDate.equals(reservation.getReservationDate()) &&
                    reservationTime.equals(reservation.getReservationTime())){
                //달력에서 선택한 날짜와 시간의 하나의 예약 인원 수
                int guest = reservation.getGuest();

                //몇 인용 테이블인지 우선 저장
                List<Integer> sortedCapacities = tables.keySet().stream().sorted(Comparator.reverseOrder()).toList();

                for (int capacity : sortedCapacities) {
                    while (guest > 0 && tables.containsKey(capacity) && tables.get(capacity) > 0) {
                        if (guest <= capacity) {
                            tables.put(capacity, tables.get(capacity) - 1);
                            calGuest += guest;
                            guest = 0;
                        }
                        else {
                            tables.put(capacity, tables.get(capacity) - 1);
                            calGuest += capacity;
                            guest -= capacity;
                        }
                    }
                    if(guest <= 0)
                        break;
                }
            }
        }
        return totalReservationGuest - calGuest;
    }

    /**
     * 예약 가능한 시간 필터링
     */
    public List<LocalTime> filterAvailableTimes(List<Reservation> reservations, ReservationRequestDTO reservationRequestDTO, Restaurant restaurant){
        List<LocalTime> reservationTimes = new ArrayList<>();

        LocalTime startTime = LocalTime.of(0, 0);

        int intervalMinutes = restaurant.getReservationTimeGap();

        // 예약 시간 갭 만큼의 시간을 모두 저장하는 부분
        do {
            reservationTimes.add(startTime);
            startTime = startTime.plusMinutes(intervalMinutes);
        } while (!startTime.equals(LocalTime.of(0, 0)));

        LocalDate curDate = reservationRequestDTO.getSelectDate();

        String dayOfWeek = curDate.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREAN);

        //오픈 시간, 마감 시간, 브레이크 타임 시작 시간, 브레이크 타임 종료 시간, 공휴일, 예약이 꽉찬 시간, 현재 시간 보다 이전 시간 모두 필터링 하는 부분
        Iterator<LocalTime> iterator = reservationTimes.iterator();
        while (iterator.hasNext()) {
            LocalTime localTime = iterator.next();
            for (int i = 0; i < restaurant.getBusinessDays().size(); i++) {
                if (dayOfWeek.equals(restaurant.getBusinessDays().get(i).getDayOfWeek())) {
                    if(restaurant.getBusinessDays().get(i).getIsDayOff()) {
                        iterator.remove();
                        continue;
                    }
                    LocalTime openTime = restaurant.getBusinessDays().get(i).getOpenTime();
                    LocalTime breakStartTime = restaurant.getBusinessDays().get(i).getBreakStartTime();
                    LocalTime breakEndTime = restaurant.getBusinessDays().get(i).getBreakEndTime();
                    LocalTime lastOrderTime = restaurant.getBusinessDays().get(i).getLastOrderTime();
                    LocalTime currentTime = LocalTime.now();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
                    LocalTime formattedTime = LocalTime.parse(currentTime.format(formatter), formatter);
                    LocalDate currentDate = LocalDate.now();

                    if (openTime.isAfter(localTime) || lastOrderTime.isBefore(localTime))
                        iterator.remove();

                    else if (!localTime.isBefore(breakStartTime) && localTime.isBefore(breakEndTime))
                        iterator.remove();

                    else if (curDate.isEqual(currentDate)) {
                        if (localTime.isBefore(formattedTime) || localTime.equals(formattedTime))
                            iterator.remove();
                    }

                    if (!reservations.isEmpty()) {
                        int calRemainingGuest = calRemainingGuest(reservations, reservationRequestDTO, localTime, curDate);

                        if (calRemainingGuest == 0)
                            iterator.remove();
                    }
                }
            }
        }

        return reservationTimes;
    }
}