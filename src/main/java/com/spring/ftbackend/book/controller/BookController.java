package com.spring.ftbackend.book.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class BookController {

    @PostMapping("/getBookForChildren")
    public ResponseEntity<String> getBookForChildren(@RequestBody Map<String, String> request) {
        String bookname = request.get("bookname");
        String prompt = "책 " + bookname + " 어린이도 읽을 수 있게 써줘";

        //여기에 LLM 에서 prompt 주고 응답받는 코드 작성

        // OpenAPI 응답을 프론트엔드에 반환
        return ResponseEntity.ok(prompt);
    }

    @GetMapping("/test")
    public String test() {
        return "Test successful.";
    }
}