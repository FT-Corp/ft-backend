package com.spring.ftbackend.term.dto;

import lombok.Getter;

@Getter
public class UserTermSaveDto {
    private Long userId;
    private Long termId;
    private boolean agreed;
}
