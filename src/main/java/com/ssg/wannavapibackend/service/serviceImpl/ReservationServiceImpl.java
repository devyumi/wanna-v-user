package com.ssg.wannavapibackend.service.serviceImpl;

import com.ssg.wannavapibackend.domain.Reservation;
import com.ssg.wannavapibackend.dto.response.ReservationDTO;
import com.ssg.wannavapibackend.repository.ReservationRepository;
import com.ssg.wannavapibackend.service.ReservationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;

    /**
     * 한 유저의 예약 전체 조회(마이페이지)
     */
    @Override
    public List<ReservationDTO> getReservationList(Long userId) {
        List<Reservation> reservations = reservationRepository.findAllByUserId(userId);

        for(Reservation reservation : reservations)
            log.info(reservation);

        return reservations.stream()
                .map(reservation -> new ReservationDTO(
                        reservation.getId(),
                        reservation.getUser(),
                        reservation.getRestaurant(),
                        reservation.getPayment(),
                        reservation.getGuest(),
                        reservation.getScheduled()
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
                reservation.getScheduled()
        );
    }

    @Override
    public Reservation saveReservation(Reservation reservation) {
        return reservationRepository.save(reservation);
    }

}
