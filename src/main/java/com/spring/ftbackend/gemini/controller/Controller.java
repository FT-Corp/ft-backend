package com.spring.ftbackend.gemini.controller;

import com.spring.ftbackend.gemini.service.GeminiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class Controller {

    @Autowired
    private GeminiService geminiService;


}