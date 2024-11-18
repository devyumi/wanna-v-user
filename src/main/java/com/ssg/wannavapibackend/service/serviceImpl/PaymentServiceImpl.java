package com.ssg.wannavapibackend.service.serviceImpl;

import com.ssg.wannavapibackend.dto.request.TossProductRequestDTO;
import com.ssg.wannavapibackend.dto.response.TossProductResponseDTO;
import com.ssg.wannavapibackend.service.PaymentService;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    /**
     * 결제 번호 생성
     * 결제일(YYYYMMDD) + 랜덤 8자리 숫자
     * @return
     */
    @Override
    public String generateOrderId() {
        // 결제일(YYYYMMDD) 가져오기
        String dateStr = new SimpleDateFormat("yyyyMMdd").format(new Date());

        // 랜덤 8자리 숫자 생성
        int randomNumber = 10000000 + new Random().nextInt(90000000);

        // 결제일과 랜덤 숫자를 결합하여 결제번호 생성
        return dateStr + randomNumber;
    }
}
