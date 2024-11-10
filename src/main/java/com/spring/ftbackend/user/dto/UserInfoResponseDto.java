package com.spring.ftbackend.user.dto;

import lombok.Builder;

@Builder
public record UserInfoResponseDto(
        String nickname,
        String username,
        String name,
        String phoneNumber
){ }
