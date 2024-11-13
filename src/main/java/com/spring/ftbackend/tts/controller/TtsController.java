package com.spring.ftbackend.tts.controller;

import com.spring.ftbackend.tts.service.TtsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class TtsController {

    @Autowired
    private TtsService ttsService;


    @PostMapping("/typecast/speak")
    public ResponseEntity<String> generateSpeech(@RequestBody Map<String, String> requestBody) {
        String text = requestBody.get("text");
        String result = ttsService.generateSpeech(text);
        System.out.println(result);
        return ResponseEntity.ok(result);
    }
}