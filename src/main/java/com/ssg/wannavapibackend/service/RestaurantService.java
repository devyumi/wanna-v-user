package com.ssg.wannavapibackend.service;

import com.ssg.wannavapibackend.domain.Reservation;
import com.ssg.wannavapibackend.dto.ReservationDTO;
import com.ssg.wannavapibackend.dto.response.ReservationResponeseDTO;

import java.util.List;

public interface ReservationService {
    List<ReservationDTO> getReservationList(Long UserId);

    ReservationDTO getReservation(Long ReservationId);

    Reservation saveReservation(Reservation reservation);
}
