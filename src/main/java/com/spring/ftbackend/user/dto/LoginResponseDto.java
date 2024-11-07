package com.spring.ftbackend.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponseDto {
    private Long userId;
    private String name;
    private String username;
    private String nickname;
    private String phoneNumber;
}