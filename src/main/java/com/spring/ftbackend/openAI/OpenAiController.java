package com.spring.ftbackend.openAI;

import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.openai.OpenAiChatModel;

import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import reactor.core.publisher.Flux;


import java.util.HashMap;
import java.util.Map;

@RestController
public class OpenAiController {

    @Autowired
    private OpenAiService openAiService;

    private OpenAiChatModel chatModel;

    public OpenAiController() {
        // OpenAI API 설정
        var openAiApi = new OpenAiApi("sk-mB-h5B9TFAnjirscOJumBDAUi6NM1zO7LXqZiYrjwvT3BlbkFJSJsDhSbbhyuT01npdlZCokuvTG2tfvpaqunGJ0yWoA");

        // GPT-4o-mini용 채팅 모델 설정
        var openAiChatOptionsGPT4 = OpenAiChatOptions.builder()
                .withModel("gpt-4o-mini")  // GPT-4 모델 사용
                .withTemperature(0.4F)
                .withMaxTokens(200)
                .build();
        this.chatModel = new OpenAiChatModel(openAiApi, openAiChatOptionsGPT4);

    }

    // open ai 채팅 사용
    //동기식(한번에 전송)
    @GetMapping("/ai/generate")
    public Map generate(@RequestParam(value = "message", defaultValue = "데미안 동화 내용 간략히 설명해줘") String message) {
        return Map.of("generation", chatModel.call(message));
    }

//    @PostMapping("/ai/generateImage")
//    public String generateImage(@RequestParam String prompt) {
//        return openAiService.generateImage(prompt);
//    }
}
