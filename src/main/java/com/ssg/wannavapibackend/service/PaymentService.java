package com.ssg.wannavapibackend.service;


import com.ssg.wannavapibackend.dto.PaymentPageInitDTO;
import com.ssg.wannavapibackend.dto.request.PaymentConfirmRequestDTO;
import com.ssg.wannavapibackend.dto.response.PaymentConfirmResponseDTO;
import com.ssg.wannavapibackend.dto.response.PaymentResponseDTO;

public interface PaymentService {

    PaymentPageInitDTO getPaymentPageInitInfo(Long userId);

    PaymentResponseDTO generateOrderId();

    PaymentConfirmResponseDTO sendRequest(PaymentConfirmRequestDTO requestDTO);

}
