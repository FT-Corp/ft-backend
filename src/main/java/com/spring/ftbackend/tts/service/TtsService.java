package com.spring.ftbackend.tts.service;

import com.spring.ftbackend.tts.client.TtsClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class TtsService {

    @Autowired
    private TtsClient ttsClient;
    private final String token = "Bearer __plti39HEKgoyGT89rWo8eXqJgSz5kpaoHpVHSj77bzC";
    private final String actorId = "6699eb5749dfac016c29445c";

    public String generateSpeech(String text) {
        // JSON 요청 바디 생성
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("actor_id", actorId);
        requestBody.put("text", text);
        requestBody.put("lang", "auto");
        requestBody.put("tempo", 1);
        requestBody.put("volume", 100);
        requestBody.put("pitch", 0);
        requestBody.put("xapi_hd", true);
        requestBody.put("max_seconds", 60);
        requestBody.put("model_version", "latest");
        requestBody.put("xapi_audio_format", "wav");

        return ttsClient.generateSpeech(requestBody, token);
    }
}
