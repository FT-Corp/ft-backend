package com.spring.ftbackend.credit.dto.request;

import lombok.Builder;
import lombok.NonNull;

@Builder
public record CreditSaveRequest(
        @NonNull Integer amount,
        @NonNull Integer duration,
        String context
) {}
