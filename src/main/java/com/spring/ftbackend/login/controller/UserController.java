package com.spring.ftbackend.login.controller;

import com.spring.ftbackend.login.domain.Users;
import com.spring.ftbackend.login.dto.UserLoginRegistrationRequest;
import com.spring.ftbackend.login.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/")
public class UserController {

    @Autowired
    private UserService userService;

    @Operation(summary = "회원가입", description = "회원가입을 수행합니다.")
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserLoginRegistrationRequest request) {
        try {
            userService.register(request.getUsername(), request.getPassword(),request.getNickname());
            return ResponseEntity.ok("User registered successfully.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Registration failed: " + e.getMessage());
        }
    }

    @Operation(summary = "로그인", description = "로그인을 수행합니다.")
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@io.swagger.v3.oas.annotations.parameters.RequestBody(
            content = @Content(mediaType = "application/json",
            examples = @ExampleObject(value ="{ \"username\": \"asdf\", \"password\": \"1234\" }")))
            @RequestBody Map<String,String> request) {
        Optional<Users> user = userService.login(request.get("username"), request.get("password"));

        if (user.isPresent()) {
            Map<String,Object> response = new HashMap<>();
            response.put("userId", user.get().getUserId());
            return ResponseEntity.ok(response);
        } else {
            Map<String, Object> response = new HashMap<>();
            response.put("error", "Invalid username or password.");
            return ResponseEntity.badRequest().body(response);
        }
    }
}