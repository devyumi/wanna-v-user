package com.ssg.wannavapibackend.service.serviceImpl;

import com.ssg.wannavapibackend.dto.request.OCRRequestDTO;
import com.ssg.wannavapibackend.dto.response.OCRResponseDTO;
import com.ssg.wannavapibackend.service.OCRService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
public class OCRServiceImpl implements OCRService {

    @Value("${naver.ocr.invoke_url}")
    private String url;

    @Value("${naver.ocr.secret_key}")
    private String secretKey;

    /**
     * 영수증 정보 조회 - 식당명, 주소, 방문일자 추출
     *
     * @param ocrRequest
     * @return
     */
    public OCRResponseDTO findReceiptData(OCRRequestDTO ocrRequest) {
        RestTemplate restTemplate = new RestTemplate();

        //Request - Header
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("X-OCR-SECRET", secretKey);

        //Request - Body
        HttpEntity<OCRRequestDTO> body = new HttpEntity<>(ocrRequest, headers);

        return restTemplate.postForObject(url, body, OCRResponseDTO.class);
    }
}