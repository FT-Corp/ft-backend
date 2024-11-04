package com.spring.ftbackend.credit.dto.response;


import lombok.Builder;
import lombok.NonNull;

import java.time.LocalDateTime;

@Builder
public record CreditResponse(
        @NonNull Integer amount,
        @NonNull String context,
        LocalDateTime issueDate,
        LocalDateTime expireDate,
        LocalDateTime redeemDate
) {}
