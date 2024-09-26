package com.spring.ftbackend.book.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
public class ProgressController {

    private SseEmitter emitter = new SseEmitter();


}
