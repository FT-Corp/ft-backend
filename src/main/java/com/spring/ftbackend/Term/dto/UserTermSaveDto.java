package com.spring.ftbackend.Term.dto;

import lombok.Getter;

@Getter
public class UserTermSaveDto {
    private Long userId;
    private Long termId;
    private boolean agreed;
}
