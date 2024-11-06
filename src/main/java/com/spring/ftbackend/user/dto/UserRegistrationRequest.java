package com.spring.ftbackend.user.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UserRegistrationRequest {
    private String name;
    private String username;
    private String password;
    private String nickname;
    private String phoneNumber;
}