package com.ssg.wannavapibackend.controller.web;

import com.ssg.wannavapibackend.config.TossPaymentConfig;
import com.ssg.wannavapibackend.dto.PaymentPageInitDTO;
import com.ssg.wannavapibackend.dto.response.ReservationPaymentResponseDTO;
import com.ssg.wannavapibackend.service.PaymentService;
import com.ssg.wannavapibackend.service.ReservationService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Log4j2
@Controller
@RequiredArgsConstructor
@RequestMapping("/payment")
public class PaymentController {

    private final PaymentService paymentService;
    private final ReservationService reservationService;
    private final TossPaymentConfig tossPaymentConfig;
    final Long userId = 1L; // Security 적용 후 삭제 예정

    @GetMapping("/product")
    public String productPayment(Model model) {
        PaymentPageInitDTO paymentPageInitDTO = paymentService.getPaymentPageInitInfo(userId);
        model.addAttribute("pageInitData", paymentPageInitDTO);
        return "payment/product";
    }

    @GetMapping("/reservation/{reservationId}")
    public String reservationPayment(@PathVariable Long reservationId, Model model) {
        log.info("결제로왔다!");

        ReservationPaymentResponseDTO reservationPaymentResponseDTO = reservationService.getReservationPayment(
            reservationId);

        model.addAttribute("reservationPaymentResponseDTO", reservationPaymentResponseDTO);

        return "/payment/reservation";
    }

    @GetMapping("/toss-success")
    public String paymentSuccess() {
        return "/payment/toss-success";
    }

    @GetMapping("/toss-fail")
    public String failPayment(HttpServletRequest request, Model model) {
        model.addAttribute("code", request.getParameter("code"));
        model.addAttribute("message", request.getParameter("message"));
        return "/payment/toss-fail";
    }

    @GetMapping("/success")
    public String reservationPaySuccess(

            @RequestParam(value = "orderId") String orderId,
            @RequestParam(value = "amount") Integer amount,
            @RequestParam(value = "paymentKey") String paymentKey) {

        log.info(orderId);
        log.info(amount);
        log.info(paymentKey);
        log.info("성공~");
        return "redirect:/success";
    }

    @GetMapping("/fail")
    public String reservationPayFail(

            @RequestParam(value = "orderId") String orderId,
            @RequestParam(value = "amount") Integer amount,
            @RequestParam(value = "paymentKey") String paymentKey) {

        log.info("실패~");
        return "redirect:/success";
    }
}
