package com.ssg.wannavapibackend.controller.web;

import com.ssg.wannavapibackend.domain.Restaurant;
import com.ssg.wannavapibackend.dto.response.OCRResponseDTO;
import com.ssg.wannavapibackend.service.OCRService;
import com.ssg.wannavapibackend.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ReviewController {

    private final ReviewService reviewService;
    private final OCRService ocrService;

    @GetMapping("upload-receipt")
    public String uploadReceipt() {
        return "review/receipt";
    }

    @PostMapping("upload-receipt")
    public String ProcessReceipt(@RequestParam("file") MultipartFile file, Model model,
                                 RedirectAttributes redirectAttributes) throws IOException {
        OCRResponseDTO responseDTO = ocrService.findReceiptData(file);

        //영수증 처리 불가
        if (responseDTO.getImages().get(0).getReceipt() == null
                || responseDTO.getImages().get(0).getReceipt().getResult() == null
                || responseDTO.getImages().get(0).getReceipt().getResult().getStoreInfo() == null
                || responseDTO.getImages().get(0).getReceipt().getResult().getStoreInfo().getBizNum() == null
                || responseDTO.getImages().get(0).getReceipt().getResult().getPaymentInfo() == null) {
            log.info("영수증 처리 불가");
            model.addAttribute("alertMessage", "영수증이 아니거나 식당명을 확인할 수 없습니다.");
            return "review/receipt";
        }

        OCRResponseDTO.StoreInfo storeInfo = responseDTO.getImages().get(0).getReceipt().getResult().getStoreInfo();
        Restaurant restaurant = ocrService.findCorrectRestaurant(storeInfo);

        //목록에 없는 식당 처리 불가
        if (restaurant == null) {
            log.info("목록에 없는 식당: {}", storeInfo.getName().getText());
            model.addAttribute("alertMessage", "목록에 없는 식당입니다.");
            return "review/receipt";
        }

        //정상 처리
        redirectAttributes.addFlashAttribute("restaurant", restaurant);
        redirectAttributes.addFlashAttribute("visitDate", ocrService.findCorrectVisitDate(responseDTO.getImages().get(0).getReceipt().getResult().getPaymentInfo().getDate().getText()));
        return "redirect:/reviews/write";
    }
}
