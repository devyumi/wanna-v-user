package com.ssg.wannavapibackend.service;


import com.ssg.wannavapibackend.dto.response.PaymentResponseDTO;

public interface PaymentService {

    PaymentResponseDTO generateOrderId();

}
