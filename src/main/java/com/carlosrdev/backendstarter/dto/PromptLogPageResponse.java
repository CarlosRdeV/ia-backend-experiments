package com.carlosrdev.backendstarter.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record PromptLogPageResponse(
        long totalElements,
        int totalPages,
        int currentPage,
        int pageSize,
        List<PromptLogResponse> results
) {}
