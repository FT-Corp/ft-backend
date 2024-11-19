package com.spring.ftbackend.AI.openAI.service;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Service
public class OpenAiService {

    @Value("${spring.ai.openai.api-key}")
    private String openAIApiKey;
    private OpenAiChatModel chatModel;

    @PostConstruct
    public void init() {
        // OpenAI API 설정
        OpenAiApi openAiApi = new OpenAiApi(this.openAIApiKey);

        // GPT-4o용 채팅 모델 설정
        OpenAiChatOptions openAiChatOptionsGPT4 = OpenAiChatOptions.builder()
                .withModel("gpt-4o")
                // .withTemperature(0.7F) // 필요 시 주석 해제
                .build();
        this.chatModel = new OpenAiChatModel(openAiApi, openAiChatOptionsGPT4);
    }

    //텍스트 생성해주는 메소드
    public String generateChatMessage(String message) {
        return chatModel.call(message);
    }

    //이미지 생성해주는 메소드
    public String generateImage(String prompt) {
        // 테스트용으로 임시로 이미지 URL 반환
        try {
            return "https://th.bing.com/th/id/OIG1.wQ7nqzXG6LLji1s3MrOP";
        }
        catch (Exception e) {
        }

        // OpenAI API 호출
        String url = "https://api.openai.com/v1/images/generations";
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(openAIApiKey);

        // Request body 설정
        Map<String, Object> body = new HashMap<>();
        body.put("prompt", prompt+"에 해당하는 동화책 이미지 생성해줘");
        body.put("n", 1); // 생성할 이미지 수
        body.put("size", "1024x1024"); // 이미지 크기
        body.put("quality", "standard");
//        body.put("model","dall-e-2");
        body.put("model","dall-e-3");


        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);

        // API 호출
        ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class);

        // JSON 파싱
        JSONObject jsonResponse = new JSONObject(response.getBody());
        JSONArray dataArray = jsonResponse.getJSONArray("data");
        String imageUrl = dataArray.getJSONObject(0).getString("url");

        return imageUrl; // 이미지 URL 반환
    }


}