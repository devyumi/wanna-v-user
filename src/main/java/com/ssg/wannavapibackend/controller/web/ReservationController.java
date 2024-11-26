package com.ssg.wannavapibackend.controller.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Log4j2
@Controller
@RequiredArgsConstructor
@RequestMapping("/")
public class ReservationController {

    @GetMapping("restaurant/{restaurantId}")
    public String test2(@PathVariable("restaurantId") Long restaurantId) {
        return "redirect:/reservation/calendar?restaurantId=" + restaurantId;
    }

    @GetMapping("/reservation/calendar")
    public String reservation1(@RequestParam(value = "restaurantId") Long restaurantId, Model model) {
        if (restaurantId == null) {
            throw new IllegalArgumentException("Restaurant ID is required.");
        }
        model.addAttribute("restaurantId", restaurantId);
        return "/reservation/calendar";
    }

    @GetMapping("/reservation")
    public String reservation() {
        return "/payment/success";
    }
}
