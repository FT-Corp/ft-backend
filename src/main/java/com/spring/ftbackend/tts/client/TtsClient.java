package com.spring.ftbackend.tts.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.Map;

@FeignClient(name = "ttsClient", url = "https://typecast.ai/api")
public interface TtsClient {

    @PostMapping(value = "/speak", consumes = MediaType.APPLICATION_JSON_VALUE)
    String generateSpeech(@RequestBody Map<String, Object> requestBody,
                          @RequestHeader("Authorization") String token);
}