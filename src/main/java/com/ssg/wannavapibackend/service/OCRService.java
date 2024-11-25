package com.ssg.wannavapibackend.service;

import com.ssg.wannavapibackend.domain.Restaurant;
import com.ssg.wannavapibackend.dto.request.OCRRequestDTO;
import com.ssg.wannavapibackend.dto.response.OCRResponseDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;

@Service
public interface OCRService {

    OCRResponseDTO findReceiptData(MultipartFile file) throws IOException;

    Restaurant findCorrectRestaurant(OCRResponseDTO.StoreInfo storeInfo);

    LocalDate findCorrectVisitDate(String visitDate);
}
