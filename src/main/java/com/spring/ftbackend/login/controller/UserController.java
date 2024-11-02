package com.spring.ftbackend.login.controller;

import com.spring.ftbackend.login.domain.Users;
import com.spring.ftbackend.login.dto.UserLoginRegistrationRequest;
import com.spring.ftbackend.login.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/")
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

    // username 중복 검사 API
    @Operation(summary = "username 중복 검사",requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "사용자 이름을 검사하기 위한 요청 본문",
            required = true,
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(
                            type = "object",
                            example = "{ \"username\": \"asdf\" }"
                    ),
                    examples = @ExampleObject(
                            name = "Username Example",
                            value = "{ \"username\": \"asdf\" }"
                    )
            )
    ))
    @PostMapping("/validate")
    public ResponseEntity<Map<String, Object>> checkUsername(@RequestBody Map<String, String> request) {
        Map<String, Object> response = new HashMap<>();

        String username = request.get("username");
        boolean isTaken = userService.isUsernameTaken(username);

        if (isTaken) {
            response.put("available", false);
            response.put("message", "이미 사용 중인 아이디입니다.");
            return ResponseEntity.status(409).body(response); // 409 Conflict
        } else {
            response.put("available", true);
            response.put("message", "사용 가능한 아이디입니다.");
            return ResponseEntity.ok(response); // 200 OK
        }
    }
}