package com.ssg.wannavapibackend.service.serviceImpl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssg.wannavapibackend.common.ErrorCode;
import com.ssg.wannavapibackend.config.TossPaymentConfig;
import com.ssg.wannavapibackend.domain.Product;
import com.ssg.wannavapibackend.domain.User;
import com.ssg.wannavapibackend.domain.UserCoupon;
import com.ssg.wannavapibackend.dto.request.DirectProductCheckoutRequestDTO;
import com.ssg.wannavapibackend.dto.request.PaymentConfirmRequestDTO;
import com.ssg.wannavapibackend.dto.response.AvailableUserCouponResponseDTO;
import com.ssg.wannavapibackend.dto.response.CheckoutResponseDTO;
import com.ssg.wannavapibackend.dto.response.PaymentConfirmResponseDTO;
import com.ssg.wannavapibackend.dto.response.PaymentItemResponseDTO;
import com.ssg.wannavapibackend.dto.response.PaymentResponseDTO;
import com.ssg.wannavapibackend.exception.CustomException;
import com.ssg.wannavapibackend.repository.PaymentRepository;
import com.ssg.wannavapibackend.repository.ProductRepository;
import com.ssg.wannavapibackend.repository.UserCouponRepository;
import com.ssg.wannavapibackend.repository.UserRepository;
import com.ssg.wannavapibackend.service.PaymentService;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final TossPaymentConfig tossPaymentConfig;
    private final ObjectMapper objectMapper;
    private final PaymentRepository paymentRepository;
    private final UserRepository userRepository;
    private final UserCouponRepository userCouponRepository;
    private final ProductRepository productRepository;

    @Override
    public CheckoutResponseDTO processCartCheckout(Long userId, List<Long> cartIds) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        List<PaymentItemResponseDTO> itemList = paymentRepository.findCartsForPayment(userId,
            cartIds);

        List<UserCoupon> userCoupons = userCouponRepository.findAllByUserIdAndEndDate(userId);
        List<AvailableUserCouponResponseDTO> availableUserCoupons = userCoupons.stream()
            .map(userCoupon -> AvailableUserCouponResponseDTO.builder()
                .id(userCoupon.getCoupon().getId())
                .code(userCoupon.getCoupon().getCode())
                .name(userCoupon.getCoupon().getName())
                .type(userCoupon.getCoupon().getType())
                .discountAmount(userCoupon.getCoupon().getDiscountAmount())
                .discountRate(userCoupon.getCoupon().getDiscountRate())
                .endDate(userCoupon.getCoupon().getEndDate())
                .build())
            .collect(Collectors.toList());

        return CheckoutResponseDTO.builder()
            .clientKey(tossPaymentConfig.getTossClientKey())
            .name(user.getName())
            .phone(user.getPhone())
            .address(user.getAddress())
            .email(user.getEmail())
            .point(user.getPoint())
            .coupons(availableUserCoupons)
            .products(itemList)
            .build();
    }

    @Override
    public CheckoutResponseDTO processDirectProductCheckout(Long userId,
        DirectProductCheckoutRequestDTO productRequestDTO) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        Product product = productRepository.findById(productRequestDTO.getProductId())
            .orElseThrow(() -> new CustomException(ErrorCode.PRODUCT_NOT_FOUND));

        List<UserCoupon> userCoupons = userCouponRepository.findAllByUserIdAndEndDate(userId);
        List<AvailableUserCouponResponseDTO> availableUserCoupons = userCoupons.stream()
            .map(userCoupon -> AvailableUserCouponResponseDTO.builder()
                .id(userCoupon.getCoupon().getId())
                .code(userCoupon.getCoupon().getCode())
                .name(userCoupon.getCoupon().getName())
                .type(userCoupon.getCoupon().getType())
                .discountAmount(userCoupon.getCoupon().getDiscountAmount())
                .discountRate(userCoupon.getCoupon().getDiscountRate())
                .endDate(userCoupon.getCoupon().getEndDate())
                .build())
            .collect(Collectors.toList());

        PaymentItemResponseDTO item = PaymentItemResponseDTO.builder()
            .image(product.getImage())
            .name(product.getName())
            .quantity(productRequestDTO.getQuantity())
            .paymentPrice(product.getFinalPrice() * productRequestDTO.getQuantity())
            .build();

        return CheckoutResponseDTO.builder()
            .clientKey(tossPaymentConfig.getTossClientKey())
            .name(user.getName())
            .phone(user.getPhone())
            .address(user.getAddress())
            .email(user.getEmail())
            .point(user.getPoint())
            .coupons(availableUserCoupons)
            .products(Collections.singletonList(item))
            .build();
    }

    /**
     * 결제 번호 생성 결제일(YYYYMMDD) + 랜덤 8자리 숫자
     *
     * @return
     */
    @Override
    public PaymentResponseDTO generateOrderId() {
        // 결제일(YYYYMMDD) 가져오기
        String dateStr = new SimpleDateFormat("yyyyMMdd").format(new Date());

        // 랜덤 8자리 숫자 생성
        int randomNumber = 10000000 + new Random().nextInt(90000000);

        // 결제일과 랜덤 숫자를 결합하여 결제번호 생성
        String orderId = dateStr + randomNumber;

        return PaymentResponseDTO.builder()
            .orderId(orderId)
            .successUrl(tossPaymentConfig.getSuccessUrl())
            .failUrl(tossPaymentConfig.getFailUrl())
            .build();
    }

    /**
     * 결제 확인 요청을 처리하는 메서드.
     *
     * @param requestDTO 결제 확인에 필요한 데이터가 담긴 요청 DTO.
     * @return 결제 확인 응답을 담은 DTO 객체.
     * @throws IOException IO 예외가 발생할 수 있음.
     */
    @Override
    public PaymentConfirmResponseDTO sendRequest(PaymentConfirmRequestDTO requestDTO) {
        try {
            // 결제 확인 요청을 위한 HTTP 연결 설정
            HttpURLConnection connection = createConnection();

            // 요청 데이터(requestDTO)를 JSON 문자열로 변환하여 HTTP 요청 본문에 포함시켜 전송
            try (OutputStream os = connection.getOutputStream()) {
                os.write(requestDTO.toJson().getBytes(StandardCharsets.UTF_8));
            }

            // 응답을 받아와서 JSON 스트림을 읽고, 그 데이터를 PaymentConfirmResponseDTO로 변환
            try (InputStream responseStream = connection.getResponseCode() == 200
                ? connection.getInputStream()  // 응답 코드가 200이면 정상 응답 스트림 사용
                : connection.getErrorStream(); // 아니면 오류 응답 스트림 사용
                Reader reader = new InputStreamReader(responseStream, StandardCharsets.UTF_8)) {
                // JSON 응답을 PaymentConfirmResponseDTO 객체로 변환
                return objectMapper.readValue(reader, PaymentConfirmResponseDTO.class);
            } catch (Exception e) { // 응답 읽기 오류가 발생
                log.error("Error reading response", e);// 예외 발생 시, 오류 상태를 반환하는 DTO 객체 생성

                return PaymentConfirmResponseDTO.builder()
                    .status("error")
                    .message("Error reading response: " + e)
                    .build();
            }
        } catch (IOException e) { // 연결 생성 중 오류 발생
            log.error("IOException in sendRequest", e);
            throw new CustomException(ErrorCode.PAYMENT_CONNECTION_FAILED);
        } catch (Exception e) { // 예기치 못한 오류
            log.error("Unexpected error", e);
            throw new CustomException(ErrorCode.PAYMENT_UNKNOWN_ERROR);
        }
    }

    /**
     * HTTP 연결을 생성하는 메서드.
     *
     * @return 생성된 HttpURLConnection 객체.
     * @throws IOException 연결을 설정하는 과정에서 IO 예외가 발생할 수 있음.
     */
    private HttpURLConnection createConnection() {
        try {
            // Toss 결제 API URL을 가져와 URL 객체 생성
            URL url = new URL(tossPaymentConfig.getUrl());
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // 인증 헤더 설정: Widget Secret Key를 Base64 인코딩하여 요청 헤더에 추가
            connection.setRequestProperty("Authorization", "Basic " + Base64.getEncoder()
                .encodeToString((tossPaymentConfig.getWidgetSecretKey() + ":").getBytes(
                    StandardCharsets.UTF_8)));

            // 요청 본문의 데이터 타입을 JSON으로 설정
            connection.setRequestProperty("Content-Type", "application/json");

            // HTTP 요청 방식 설정: POST 방식
            connection.setRequestMethod("POST");

            // 요청 본문을 전송할 수 있도록 설정
            connection.setDoOutput(true);

            // 설정된 connection 객체를 반환
            return connection;
        } catch (IOException e) { // 연결 생성 실패
            log.error("Error creating connection", e);
            throw new CustomException(ErrorCode.PAYMENT_CONNECTION_FAILED);
        } catch (Exception e) { // 예기치 못한 오류
            log.error("Unexpected error in connection creation", e);
            throw new CustomException(ErrorCode.PAYMENT_UNKNOWN_ERROR);
        }
    }
}
