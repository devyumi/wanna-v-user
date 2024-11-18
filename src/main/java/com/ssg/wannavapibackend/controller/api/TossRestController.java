package com.ssg.wannavapibackend.controller.api;

import com.ssg.wannavapibackend.dto.request.TossProductRequestDTO;
import com.ssg.wannavapibackend.dto.response.TossProductResponseDTO;
import com.ssg.wannavapibackend.service.PaymentService;
import jakarta.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/payment")
public class TossRestController {

    private final PaymentService paymentService;
    final Long userId = 1L; // Security 적용 후 삭제 예정

    @PostMapping("/generate-order-id")
    public ResponseEntity<Map<String, String>> generateOrderId() {
        String orderId = paymentService.generateOrderId();

        Map<String, String> response = new HashMap<>();
        response.put("status", "success");
        response.put("orderId", orderId);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
