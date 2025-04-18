package com.carlosrdev.backendstarter.dto;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record PromptLogResponse(
        String prompt,
        String response,
        boolean success,
        String traceId,
        LocalDateTime timestamp
) {}
