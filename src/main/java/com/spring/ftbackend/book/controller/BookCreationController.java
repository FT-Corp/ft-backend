package com.spring.ftbackend.book.controller;

import com.spring.ftbackend.book.service.SseEmitterService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
public class BookCreationController {

    @Autowired
    private SseEmitterService sseEmitterService;

    @Operation(summary = "책 생성 진행 상황 SSE 스트리밍")
    @GetMapping(value = "/progress")
    public SseEmitter streamProgress() {
        return sseEmitterService.createEmitter();
    }

}
