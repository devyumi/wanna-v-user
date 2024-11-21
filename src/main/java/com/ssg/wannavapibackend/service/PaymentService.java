package com.ssg.wannavapibackend.service;


import com.ssg.wannavapibackend.dto.response.CartCheckoutResponseDTO;
import com.ssg.wannavapibackend.dto.request.PaymentConfirmRequestDTO;
import com.ssg.wannavapibackend.dto.response.PaymentConfirmResponseDTO;
import com.ssg.wannavapibackend.dto.response.PaymentResponseDTO;
import java.util.List;

public interface PaymentService {

    CartCheckoutResponseDTO getPaymentPageInitInfo(Long userId, List<Long> cartIds);

    PaymentResponseDTO generateOrderId();

    PaymentConfirmResponseDTO sendRequest(PaymentConfirmRequestDTO requestDTO);

}
