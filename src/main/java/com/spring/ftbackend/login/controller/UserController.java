package com.spring.ftbackend.login.controller;

import com.spring.ftbackend.login.dto.UserLoginRegistrationRequest;
import com.spring.ftbackend.login.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/test")
    public String test() {
        return "Test successful.";
    }
    // 회원 가입
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserLoginRegistrationRequest request) {
        try {
            userService.register(request.getUsername(), request.getPassword(),request.getNickName());
            return ResponseEntity.ok("User registered successfully.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Registration failed: " + e.getMessage());
        }
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserLoginRegistrationRequest request) {
        boolean success = userService.login(request.getUsername(), request.getPassword());
        if (success) {
            return ResponseEntity.ok("Login successful.");
        } else {
            return ResponseEntity.badRequest().body("Invalid username or password.");
        }
    }
}