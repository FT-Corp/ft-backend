package com.spring.ftbackend.user.controller;

import com.spring.ftbackend.user.domain.User;
import com.spring.ftbackend.user.dto.LoginRequestDto;
import com.spring.ftbackend.user.dto.LoginResponseDto;
import com.spring.ftbackend.user.dto.UserRegistrationRequest;
import com.spring.ftbackend.user.service.UserService;
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

    @Operation(summary = "회원가입",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = "{ \"name\": \"무지\", \"username\": \"asdf\", \"password\": \"1234\", \"nickname\": \"aa\", \"phoneNumber\": \"010-1234-5678\" }")
                    )
            )
    )
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserRegistrationRequest request) {
        try {
            userService.register(request);
            return ResponseEntity.ok("User registered successfully.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Registration failed: " + e.getMessage());
        }
    }

    @Operation(summary = "로그인",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = "{ \"username\": \"asdf\", \"password\": \"1234\" }")
                    )
            )
    )
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto request) {
        Optional<User> user = userService.login(request.getUsername(), request.getPassword());

        if (user.isPresent()) {
            LoginResponseDto loginResponseDto = new LoginResponseDto(user.get().getUserId(), user.get().getName(), user.get().getUsername(), user.get().getNickname(), user.get().getPhoneNumber());
            return ResponseEntity.ok(loginResponseDto);
        } else {
            return ResponseEntity.badRequest().body(null);
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

    // 프로필 이미지 반환해주는 api
    @Operation(summary = "프로필 이미지 반환",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = "{ \"userId\": \"1\" }")
                    )
            )
    )
    @PostMapping("/profileImage")
    public ResponseEntity<Map<String, Object>> profileImage(@RequestBody Map<String, Long> request) {
        Map<String, Object> response = new HashMap<>();
        Long userId = request.get("userId");
        String profileImageUrl = userService.getProfileImageUrl(userId);
        response.put("profileImageUrl", profileImageUrl);
        return ResponseEntity.ok(response);
    }

}