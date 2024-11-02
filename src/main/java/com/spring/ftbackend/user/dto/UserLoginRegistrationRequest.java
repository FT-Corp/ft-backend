package com.spring.ftbackend.user.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UserLoginRegistrationRequest {
    private String username;
    private String password;
    private String nickname;

}