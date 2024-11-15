package com.ssg.wannavapibackend.service;

import com.ssg.wannavapibackend.dto.request.OCRRequestDTO;
import com.ssg.wannavapibackend.dto.response.OCRResponseDTO;
import org.springframework.stereotype.Service;

@Service
public interface OCRService {

    OCRResponseDTO findReceiptData(OCRRequestDTO ocrRequest);
}
