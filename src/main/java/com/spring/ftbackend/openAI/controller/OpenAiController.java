package com.spring.ftbackend.openAI.controller;

import com.spring.ftbackend.openAI.service.OpenAiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.Map;

@RestController
public class OpenAiController {

    @Autowired
    private OpenAiService openAiService;

    // open ai 채팅 사용
    @GetMapping("/ai/generate")
    public Map<String, String> generate(@RequestParam(value = "message", defaultValue = "데미안 동화 내용 간략히 설명해줘") String message) {
        String response = openAiService.generateChatMessage(message);
        return Map.of("generation", response);
    }

//    @PostMapping("/ai/generateImage")
//    public String generateImage(@RequestParam String prompt) {
//        return openAiService.generateImage(prompt);
//    }
}
