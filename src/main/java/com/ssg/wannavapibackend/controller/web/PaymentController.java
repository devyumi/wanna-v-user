package com.ssg.wannavapibackend.controller.web;

import com.ssg.wannavapibackend.dto.response.CartCheckoutResponseDTO;
import com.ssg.wannavapibackend.dto.request.DirectProductCheckoutRequestDTO;
import com.ssg.wannavapibackend.dto.response.ReservationPaymentResponseDTO;
import com.ssg.wannavapibackend.service.PaymentService;
import com.ssg.wannavapibackend.service.ReservationService;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Log4j2
@Controller
@RequiredArgsConstructor
@RequestMapping("/checkout")
public class PaymentController {

    private final PaymentService paymentService;
    private final ReservationService reservationService;
    final Long userId = 1L; // Security 적용 후 삭제 예정

    @PostMapping("/product")
    public String redirectToProductPaymentPage(@RequestBody(required = false) List<Long> cartIds,
        @RequestBody(required = false) DirectProductCheckoutRequestDTO productRequestDTO,
        Model model) {

         if (cartIds != null && !cartIds.isEmpty()) {
             // 장바구니 -> 결제
            CartCheckoutResponseDTO responseDTO = paymentService.getPaymentPageInitInfo(userId, cartIds);
            model.addAttribute("pageInitData", responseDTO);
        } else if (productRequestDTO != null) {
             // 상품 페잊지 -> 결제
         }


        return "payment/product";
    }

    @GetMapping("/reservation/{reservationId}")
    public String reservationPayment(@PathVariable Long reservationId, Model model) {

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
        return "redirect:/success";
    }

    @GetMapping("/fail")
    public String reservationPayFail(
        @RequestParam(value = "orderId") String orderId,
        @RequestParam(value = "amount") Integer amount,
        @RequestParam(value = "paymentKey") String paymentKey) {
        return "redirect:/success";
    }
}
