package com.ssg.wannavapibackend.service.serviceImpl;

<<<<<<< HEAD
import com.ssg.wannavapibackend.config.TossPaymentConfig;
=======
>>>>>>> parent of 45ce158 (refactor: merge 위한 구조 통일)
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
import com.ssg.wannavapibackend.service.ReservationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
<<<<<<< HEAD
import org.springframework.transaction.annotation.Transactional;
=======
>>>>>>> parent of 45ce158 (refactor: merge 위한 구조 통일)

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
<<<<<<< HEAD
    private final TossPaymentConfig tossPaymentConfig;
=======
>>>>>>> parent of 45ce158 (refactor: merge 위한 구조 통일)

    /**
     * 한 유저의 예약 전체 조회(마이페이지)
     */
    @Override
    public List<ReservationDTO> getReservationList(Long userId) {
        List<Reservation> reservations = reservationRepository.findAllByUserId(userId);

<<<<<<< HEAD
=======
        for(Reservation reservation : reservations)
            log.info(reservation);

>>>>>>> parent of 45ce158 (refactor: merge 위한 구조 통일)
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
<<<<<<< HEAD

    @Override
    public ReservationPaymentResponseDTO getReservationPayment(ReservationRequestDTO reservationRequestDTO) {

        if(reservationRepository.existsByMyReservaion(1L, reservationRequestDTO.getRestaurantId(), reservationRequestDTO.getSelectDate()))
            throw new RuntimeException("하루에 한 번만 예약이 가능합니다!");

        Restaurant restaurant = restaurantRepository.findById(reservationRequestDTO.getRestaurantId()).orElseThrow(() -> new IllegalArgumentException("Invalid ID value: "));

        LocalDateTime dateTime = LocalDateTime.of(reservationRequestDTO.getSelectDate(), reservationRequestDTO.getSelectTime());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일");
        String formattedDate = reservationRequestDTO.getSelectDate().format(formatter);
=======
    @Override
    public ReservationPaymentResponseDTO getReservationPayment(Long reservationId) {
        Reservation reservation = reservationRepository.findByReservationId(reservationId);

        LocalDateTime dateTime = LocalDateTime.of(reservation.getReservationDate(), reservation.getReservationTime());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일");
        String formattedDate = reservation.getReservationDate().format(formatter);
>>>>>>> parent of 45ce158 (refactor: merge 위한 구조 통일)

        String dayOfWeek = dateTime.getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.KOREAN);

        String amPm = dateTime.getHour() < 12 ? "오전" : "오후";

<<<<<<< HEAD
        return new ReservationPaymentResponseDTO(
                1L,
                reservationRequestDTO.getRestaurantId(),
                restaurant.getName(),
                restaurant.getAddress().getRoadAddress(),
                reservationRequestDTO.getSelectGuest(),
                formattedDate,
                reservationRequestDTO.getSelectTime(),
                reservationRequestDTO.getSelectGuest() * 10000,
                dayOfWeek,
                amPm,
                tossPaymentConfig.getTossClientKey());
=======
        return new ReservationPaymentResponseDTO(reservationId,
                reservation.getUser().getId(),
                reservation.getRestaurant().getId(),
                reservation.getRestaurant().getName(),
                reservation.getRestaurant().getAddress().getRoadAddress(),
                reservation.getGuest(),
                formattedDate,
                reservation.getReservationTime(),
                reservation.getGuest() * 10000,
                dayOfWeek,
                amPm);
>>>>>>> parent of 45ce158 (refactor: merge 위한 구조 통일)
    }

    /**
     * 예약 등록
     */
<<<<<<< HEAD

    @Transactional
    public ReservationSaveResponseDTO saveReservation(ReservationRequestDTO reservationRequestDTO) {
=======
    @Override
    public ReservationSaveResponseDTO saveReservation(ReservationRequestDTO reservationRequestDTO) {
        log.info("서비스 왔다리~");
>>>>>>> parent of 45ce158 (refactor: merge 위한 구조 통일)

        Restaurant restaurant = restaurantRepository.findById(reservationRequestDTO.getRestaurantId()).orElseThrow(() -> new IllegalArgumentException("식당이 없습니다."));

        User user = userRepository.findById(1L).orElseThrow(() -> new IllegalArgumentException("유저가 없습니다."));

<<<<<<< HEAD
        if (reservationRepository.existsByMyReservaion(user.getId(), reservationRequestDTO.getRestaurantId(), reservationRequestDTO.getSelectDate()))
            throw new IllegalArgumentException("당일 예약은 한 번만 가능합니다.");

        Reservation reservation = new Reservation(null, user, restaurant, null, reservationRequestDTO.getSelectGuest(),true, reservationRequestDTO.getSelectDate(), reservationRequestDTO.getSelectTime(),  LocalDateTime.now(), null);
=======
        Reservation reservation = new Reservation(null, user, restaurant, null, reservationRequestDTO.getSelectGuest(), reservationRequestDTO.getSelectDate(), reservationRequestDTO.getSelectTime(),  LocalDateTime.now(), null);
>>>>>>> parent of 45ce158 (refactor: merge 위한 구조 통일)

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
<<<<<<< HEAD
        Restaurant restaurant = restaurantRepository.findById(reservationRequestDTO.getRestaurantId()).orElseThrow(() -> new IllegalArgumentException("식당이 없습니다."));
=======
        log.info("안녕 난 서비스");

        log.info("잘 들어왔니? : " + reservationRequestDTO.getRestaurantId());
>>>>>>> parent of 45ce158 (refactor: merge 위한 구조 통일)

        List<Reservation> reservations = reservationRepository.findAllByRestaurantId(reservationRequestDTO.getRestaurantId());

        List<LocalTime> filteredTime;

        int guest = 0;

        if(reservationRequestDTO.getSelectTime() == null) {
<<<<<<< HEAD
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

=======
            filteredTime = Timefilter(reservations, reservationRequestDTO);
            return new ReservationDateResponseDTO(reservationRequestDTO.getRestaurantId(),null, reservationRequestDTO.getSelectDate(),filteredTime);
        }
        else {
            guest = calRemainingGuest(reservations, reservationRequestDTO);
            return new ReservationDateResponseDTO(reservationRequestDTO.getRestaurantId(), guest, reservationRequestDTO.getSelectDate(),null);
        }
    }

    /**
     * 선택한 날짜 및 시간의 예약 인원 수 확인
     */
    public int calRemainingGuest(List<Reservation> reservations, ReservationRequestDTO reservationRequestDTO) {
>>>>>>> parent of 45ce158 (refactor: merge 위한 구조 통일)
        int totalReservationGuest = reservations.stream().map(Reservation::getRestaurant).distinct()
                .flatMap(reservation -> reservation.getSeats().stream())
                .mapToInt(seat -> seat.getSeatCount() * seat.getSeatCapacity())
                .sum();

        int calGuest = 0;

        LocalDate reservationDate = reservationRequestDTO.getSelectDate();
<<<<<<< HEAD
        LocalTime reservationTime = reservationRequestDTO.getSelectTime();
=======
        log.info(reservationDate);
        LocalTime reservationTime = reservationRequestDTO.getSelectTime();
        log.info(reservationTime);
>>>>>>> parent of 45ce158 (refactor: merge 위한 구조 통일)

        Map<Integer, Integer> tables = null;

        for(Reservation reservation : reservations){
<<<<<<< HEAD
            if (tables == null)
                tables = new HashMap<>();

            for(Seat seat : reservation.getRestaurant().getSeats())
                tables.put(seat.getSeatCapacity(), seat.getSeatCount());
=======
            if (tables == null){
                tables = new HashMap<>();

                for(Seat seat : reservation.getRestaurant().getSeats())
                    tables.put(seat.getSeatCapacity(), seat.getSeatCount());
            }

            log.info("시작!!!!!!!!!!!!!!!!!");
>>>>>>> parent of 45ce158 (refactor: merge 위한 구조 통일)

            //달력에서 선택한 예약 날짜의 인원 수를 계산하는 로직
            if(reservationDate.equals(reservation.getReservationDate())
                    && reservationTime == null){
<<<<<<< HEAD

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
=======
                log.info("이곳은 날짜만 눌렀을 때다!");

                log.info("테이블 : " + tables.toString());

                //달력에서 선택한 날짜와 시간의 예약 하나 인원 수
                int guest = reservation.getGuest();
                log.info("인원 : " + guest);

                //몇 인용 테이블인지 우선 저장
                int[] seatCapacities = tables.keySet().stream().mapToInt(Integer::intValue).toArray();
                log.info(seatCapacities);

                for (int capacity : seatCapacities) {
                    while (guest > 0 && tables.containsKey(capacity) && tables.get(capacity) > 0) {
                        if (guest >= capacity) {
                            int tablesToAllocate = guest / capacity;

                            if (guest % capacity != 0)
                                tablesToAllocate++;

                            int availableTables = tables.get(capacity);
                            if (availableTables >= tablesToAllocate) {
                                tables.put(capacity, availableTables - tablesToAllocate);
                                calGuest += capacity * tablesToAllocate;
                                guest -= capacity * tablesToAllocate;
                            } else {
                                tables.put(capacity, 0);
                                calGuest += capacity * availableTables;
                                guest -= capacity * availableTables;
                            }
                        } else {
                            tables.put(capacity, tables.get(capacity) - 1);
                            guest = capacity;
                            calGuest += guest;
                            guest = 0;
                        }
                    }

                    // 남은 인원이 없다면 종료
                    if (guest == 0) {
                        break;
                    }
                }

                log.info("배정된 인원 수: " + calGuest);
            }

            else if(reservationDate.equals(reservation.getReservationDate())
                && reservationTime.equals(reservation.getReservationTime())
                && reservationTime != null){
                log.info("이곳은 날짜와 시간 모두 눌렀을 때다!");
                //테이블로 인원 수 계산하는 부분
                log.info("테이블 : " + tables.toString());

                //달력에서 선택한 날짜와 시간의 예약 하나 인원 수
                int guest = reservation.getGuest();
                log.info("인원 : " + guest);

                //몇 인용 테이블인지 우선 저장
                int[] seatCapacities = tables.keySet().stream().mapToInt(Integer::intValue).toArray();
                log.info(seatCapacities);


                for (int capacity : seatCapacities) {
                    while (guest > 0 && tables.containsKey(capacity) && tables.get(capacity) > 0) {
                        if (guest >= capacity) {
                            // 배정할 테이블 개수 계산
                            int tablesToAllocate = guest / capacity;
                            // 남은 인원이 있으면 한 테이블 더 배정
                            if (guest % capacity != 0) {
                                tablesToAllocate++;
                            }

                            // 필요한 테이블이 충분한지 확인
                            int availableTables = tables.get(capacity);
                            if (availableTables >= tablesToAllocate) {
                                tables.put(capacity, availableTables - tablesToAllocate);
                                calGuest += capacity * tablesToAllocate; // 배정된 테이블 수 만큼 인원 추가
                                guest -= capacity * tablesToAllocate;   // 배정된 인원 수만큼 차감
                            } else {
                                // 필요한 테이블이 부족한 경우, 가능한 만큼 배정
                                tables.put(capacity, 0);
                                calGuest += capacity * availableTables;  // 가능한 테이블만큼 배정
                                guest -= capacity * availableTables;     // 배정된 인원 차감
                            }
                        } else {
                            // 예약 신청 인원 수가 테이블 용량보다 적을 때, 해당 테이블 배정
                            tables.put(capacity, tables.get(capacity) - 1);
                            guest = capacity;
                            calGuest += guest;  // 남은 모든 인원을 배정
                            guest = 0;  // 모든 인원 배정 완료
                        }
                    }

                    // 남은 인원이 없다면 종료
                    if (guest == 0)
                        break;
                }

                log.info("배정된 인원 수: " + calGuest);
            }
        }

        log.info("예약 인원 수 :" + calGuest);

        return totalReservationGuest - calGuest;
    }

    /**
     * 예약 가능한 시간 필터링
     */
    public List<LocalTime> Timefilter(List<Reservation> reservations, ReservationRequestDTO reservationRequestDTO){
>>>>>>> parent of 45ce158 (refactor: merge 위한 구조 통일)
        List<LocalTime> reservationTimes = new ArrayList<>();

        LocalTime startTime = LocalTime.of(0, 0);

<<<<<<< HEAD
        int intervalMinutes = restaurant.getReservationTimeGap();

        // 예약 시간 갭 만큼의 시간을 모두 저장하는 부분
=======
        int intervalMinutes = reservations.get(0).getRestaurant().getReservationTimeGap();
        log.info("갭 : " + intervalMinutes);

>>>>>>> parent of 45ce158 (refactor: merge 위한 구조 통일)
        do {
            reservationTimes.add(startTime);
            startTime = startTime.plusMinutes(intervalMinutes);
        } while (!startTime.equals(LocalTime.of(0, 0)));

<<<<<<< HEAD
        for(LocalTime localTime : reservationTimes)
            log.info(localTime);

        LocalDate curDate = reservationRequestDTO.getSelectDate();

        String dayOfWeek = curDate.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREAN);
        log.info(dayOfWeek);

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
=======
        LocalDate curDate = reservationRequestDTO.getSelectDate();

        //내가 선택한 날짜의 요일
        String dayOfWeek = curDate.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREAN);

        Iterator<LocalTime> iterator = reservationTimes.iterator();
        while (iterator.hasNext()) {
            LocalTime localTime = iterator.next();
            log.info("가지고 온 것 : " + localTime);
            for (int i = 0; i < reservations.get(0).getRestaurant().getBusinessDays().size(); i++) {
                if (dayOfWeek.equals(reservations.get(0).getRestaurant().getBusinessDays().get(i).getDayOfWeek())) {
                    LocalTime openTime = reservations.get(0).getRestaurant().getBusinessDays().get(i).getOpenTime();
                    log.info("오픈 시간 : " + openTime);
                    LocalTime breakStartTime = reservations.get(0).getRestaurant().getBusinessDays().get(i).getBreakStartTime();
                    log.info("브레이스 시작 시간 : " + breakStartTime);
                    LocalTime breakEndTime = reservations.get(0).getRestaurant().getBusinessDays().get(i).getBreakEndTime();
                    log.info("브레이스 종료 시간 : " + breakEndTime);
                    LocalTime lastOrderTime = reservations.get(0).getRestaurant().getBusinessDays().get(i).getLastOrderTime();
                    log.info("라스트 오더 시간 : " + lastOrderTime);
                    boolean isOpen = reservations.get(0).getRestaurant().getBusinessDays().get(i).getRestaurant().getBusinessDays().get(i).getIsClose();
                    log.info("오픈 여부 : " + isOpen);

                    if(isOpen)
                        iterator.remove();
                    else{
                        if (openTime.isAfter(localTime) || lastOrderTime.isBefore(localTime)) {
                            log.info("잘가랑~ : " + localTime);
                            iterator.remove();
                        }

                        else if (!localTime.isBefore(breakStartTime) && localTime.isBefore(breakEndTime)) {
                            log.info("잘가랑~ : " + localTime);
                            iterator.remove();
                        }
                    }

                    int calRemainingGuest = calRemainingGuest(reservations, reservationRequestDTO);
                    log.info("예약 가능 인원 수 : " + calRemainingGuest);
>>>>>>> parent of 45ce158 (refactor: merge 위한 구조 통일)
                }
            }
        }

        return reservationTimes;
    }
}
<<<<<<< HEAD

=======
>>>>>>> parent of 45ce158 (refactor: merge 위한 구조 통일)
