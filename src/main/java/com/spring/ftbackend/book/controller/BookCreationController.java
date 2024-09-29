package com.spring.ftbackend.book.controller;

import com.spring.ftbackend.book.service.SseEmitterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequestMapping("/api/")
public class BookCreationController {

    @Autowired
    private SseEmitterService sseEmitterService;

    @GetMapping(value = "/progress")
    public SseEmitter streamProgress() {
        return sseEmitterService.createEmitter();
    }

}
