package com.carlosrdev.backendstarter.controller;

import com.carlosrdev.backendstarter.dto.PromptLogPageResponse;
import com.carlosrdev.backendstarter.dto.PromptLogResponse;
import com.carlosrdev.backendstarter.model.PromptLog;
import com.carlosrdev.backendstarter.repository.PromptLogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/prompts")
public class PromptLogController {

    private final PromptLogRepository repository;

    @GetMapping
    public PromptLogPageResponse getPrompts(
            @RequestParam(required = false) String traceId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "timestamp"));
        Page<PromptLog> logs;

        if (traceId != null && !traceId.isEmpty()) {
            logs = repository.findByTraceId(traceId, pageable);
        } else {
            logs = repository.findAll(pageable);
        }

        List<PromptLogResponse> content = logs.getContent().stream()
                .map(log -> PromptLogResponse.builder()
                        .prompt(log.getPrompt())
                        .response(log.getResponse())
                        .success(log.isSuccess())
                        .traceId(log.getTraceId())
                        .timestamp(log.getTimestamp())
                        .build())
                .toList();

        return PromptLogPageResponse.builder()
                .totalElements(logs.getTotalElements())
                .totalPages(logs.getTotalPages())
                .currentPage(logs.getNumber())
                .pageSize(logs.getSize())
                .results(content)
                .build();
    }

}
