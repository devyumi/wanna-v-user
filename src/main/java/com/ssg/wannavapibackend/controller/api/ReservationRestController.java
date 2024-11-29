package com.ssg.wannavapibackend.controller.api;

import com.ssg.wannavapibackend.dto.request.ReservationRequestDTO;
import com.ssg.wannavapibackend.dto.response.ReservationDateResponseDTO;
import com.ssg.wannavapibackend.dto.response.ReservationPaymentResponseDTO;
import com.ssg.wannavapibackend.dto.response.ReservationSaveResponseDTO;
import com.ssg.wannavapibackend.exception.LockException;
import com.ssg.wannavapibackend.facade.RedissonLockReservationFacade;
import com.ssg.wannavapibackend.service.ReservationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reservation")
public class ReservationRestController {

    private final ReservationService reservationService;
    private final RedissonLockReservationFacade redissonLockReservationFacade;

    @GetMapping("/date")
    public ReservationDateResponseDTO getReservationTime(@ModelAttribute("reservationRequestDTO") ReservationRequestDTO reservationRequestDTO) {
        return reservationService.getReservationTime(reservationRequestDTO);
    }

    @GetMapping("/time")
    public ReservationDateResponseDTO getReservationGuest(@ModelAttribute("reservationRequestDTO") ReservationRequestDTO reservationRequestDTO) {
        return reservationService.getReservationTime(reservationRequestDTO);
    }

    @PostMapping("/confirm")
    public ResponseEntity<Map<String, Object>> saveReservation(@Valid @ModelAttribute("reservationRequestDTO") ReservationRequestDTO reservationRequestDTO, BindingResult bindingResult) {
        Map<String, Object> response = new HashMap<>();

        if (bindingResult.hasErrors()) {
            response.put("message", "예약 날짜, 예약 시간, 인원 수 모두 선택해야 합니다!");
            response.put("status", "error");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        try {
            Boolean penalty = reservationService.getPenalty(reservationRequestDTO.getRestaurantId());

            if (!penalty) {
                redissonLockReservationFacade.reservationRock(reservationRequestDTO);
                response.put("message", "예약이 성공적으로 완료되었습니다.");
                response.put("status", "success");
            }

            response.put("status", "payment");

            return ResponseEntity.ok(response);

        }   catch (IllegalArgumentException e) {
            response.put("message", "예약은 하루에 한 번 가능합니다!");
            response.put("status", "error");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }   catch (LockException e) {
            response.put("message", e.getMessage());
            response.put("status", "error");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
        }   catch (Exception e) {
            response.put("message", "예약 중 오류가 발생했습니다.");
            response.put("status", "error");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}