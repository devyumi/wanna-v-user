package com.ssg.wannavapibackend.controller.web;

import com.ssg.wannavapibackend.dto.request.MyPageUpdateDTO;
import com.ssg.wannavapibackend.service.MyPageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MyPageController {

    private final MyPageService myPageService;

    @GetMapping("my")
    public String getMyPage(Model model) {
        model.addAttribute("my", myPageService.findMyPage(1L));
        return "user/mypage";
    }

    @GetMapping("my/edit")
    public String getMyPageEdit(Model model) {
        model.addAttribute("myPageUpdateDTO", myPageService.findUserInfo(1L));
        model.addAttribute("userInfo", myPageService.findUserInfo(1L));
        return "user/mypage-edit";
    }

    @PostMapping("my/edit")
    public String postMyPageEdit(Model model, @ModelAttribute @Validated MyPageUpdateDTO myPageUpdateDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("myPageUpdateDTO", myPageUpdateDTO);
            model.addAttribute("userInfo", myPageService.findUserInfo(1L));
            printErrorLog(bindingResult);
            return "user/mypage-edit";
        }
        myPageService.updateMyPage(1L, myPageUpdateDTO);
        log.info("마이페이지 수정 완료");
        return "redirect:/my";
    }

    private static void printErrorLog(BindingResult result) {
        log.info("{}", "*".repeat(20));
        for (FieldError fieldError : result.getFieldErrors()) {
            log.error("{}: {}", fieldError.getField(), fieldError.getDefaultMessage());
        }
        log.info("{}", "*".repeat(20));
    }
}
