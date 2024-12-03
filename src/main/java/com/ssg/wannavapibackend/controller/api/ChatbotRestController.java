package com.ssg.wannavapibackend.controller.api;

import com.ssg.wannavapibackend.service.ChatbotService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ChatbotRestController {

    private final ChatbotService chatbotService;
    final Long userId = 1L; // Security 적용 후 삭제 예정

    @PostMapping("/send-message")
    public String sendMessage(@RequestBody String requestMessage) {
        return chatbotService.sendMessage(userId, requestMessage);
    }
}
