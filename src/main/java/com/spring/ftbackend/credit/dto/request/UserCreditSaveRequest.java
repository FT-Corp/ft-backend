package com.spring.ftbackend.credit.dto.request;

import lombok.Builder;
import lombok.NonNull;

@Builder
public record UserCreditSaveRequest(
       @NonNull Long userId,
       @NonNull Long creditId
) { }
