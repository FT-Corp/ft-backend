package com.spring.ftbackend.openAI;

import org.json.JSONArray;
import org.json.JSONObject;
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

    //이미지 생성해주는 메소드
    public String generateImage(String prompt) {
        String url = "https://api.openai.com/v1/images/generations";
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);

        // Request body 설정
        Map<String, Object> body = new HashMap<>();
        body.put("prompt", prompt);
        body.put("n", 1); // 생성할 이미지 수
        body.put("size", "1024x1024"); // 이미지 크기
        body.put("quality", "standard");
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