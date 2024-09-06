package com.spring.ftbackend.login.dto;

public class UserLoginRegistrationRequest {
    private String username;
    private String password;
    private String nickname;

    // Getter and Setter methods
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickName() { return nickname; }

    public void setNickname(String nickname) { this.nickname = nickname; }
}