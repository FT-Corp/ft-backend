package com.spring.ftbackend.openAI.service;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.HashMap;
import java.util.Map;

@Service
public class OpenAiService {
    private final String apiKey = "sk-mB-h5B9TFAnjirscOJumBDAUi6NM1zO7LXqZiYrjwvT3BlbkFJSJsDhSbbhyuT01npdlZCokuvTG2tfvpaqunGJ0yWoA";

    private final OpenAiChatModel chatModel;

    public OpenAiService() {
        // OpenAI API 설정
        var openAiApi = new OpenAiApi("sk-mB-h5B9TFAnjirscOJumBDAUi6NM1zO7LXqZiYrjwvT3BlbkFJSJsDhSbbhyuT01npdlZCokuvTG2tfvpaqunGJ0yWoA");

        // GPT-4o-mini용 채팅 모델 설정
        var openAiChatOptionsGPT4 = OpenAiChatOptions.builder()
                .withModel("gpt-4o-mini")  // GPT-4o-mini 모델 사용
                .withTemperature(0.4F)
                .withMaxTokens(200)
                .build();
        this.chatModel = new OpenAiChatModel(openAiApi, openAiChatOptionsGPT4);
    }

    //텍스트 생성해주는 메소드
    public String generateChatMessage(String message) {
        return chatModel.call(message);
    }



    //이미지 생성해주는 메소드
    public String generateImage(String prompt) {
        String url = "https://api.openai.com/v1/images/generations";
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);

        // Request body 설정
        Map<String, Object> body = new HashMap<>();
        body.put("prompt", prompt+"에 해당하는 동화책 이미지 생성해줘");
        body.put("n", 1); // 생성할 이미지 수
        body.put("size", "256x256"); // 이미지 크기
        body.put("quality", "standard");
        body.put("model","dall-e-2");
//        body.put("model","dall-e-3");


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