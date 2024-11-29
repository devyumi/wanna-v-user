package com.ssg.wannavapibackend.repository;

import com.ssg.wannavapibackend.domain.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long>, ReservationCustomRepository {
    List<Reservation> findAllByUserId(Long userId);

    //예약이 없을 경우도 있으므로 LEFT JOIN 사용
    @Query("""
    SELECT DISTINCT r1 FROM Reservation r1
    LEFT JOIN r1.restaurant r2
    LEFT JOIN r2.businessDays b
    LEFT JOIN r2.seats s
    WHERE r1.restaurant.id = :restaurantId
    """)
    List<Reservation> findAllByRestaurantId(Long restaurantId);

    @Query("""
    SELECT r1
    FROM Reservation r1
    LEFT JOIN r1.restaurant r2
    LEFT JOIN r1.user u
    WHERE r1.id = :reservationId
    """)
    Reservation findByReservationId(Long reservationId);

    @Query("""
    SELECT r.reservationDate, r.reservationTime
    FROM Reservation r
    WHERE r.reservationDate = :reservationDate
    AND r.reservationTime = :reservationTime
    """)
    List<Reservation> findAllByReservation(LocalDate reservationDate, LocalTime reservationTime);

    @Query("""
    SELECT CASE WHEN COUNT(r) > 0
                THEN true ELSE false END
                FROM Reservation r
                WHERE r.user.id = :userId
                AND r.restaurant.id = :restaurantId
                AND r.reservationDate = :selectDate
    """)
    Boolean existsByMyReservaion(Long userId, Long restaurantId, LocalDate selectDate);
}