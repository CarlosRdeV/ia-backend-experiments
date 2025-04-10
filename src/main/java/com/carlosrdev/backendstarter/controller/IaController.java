package com.carlosrdev.backendstarter.controller;

import com.carlosrdev.backendstarter.dto.IaResponse;
import com.carlosrdev.backendstarter.dto.PromptRequest;
import com.carlosrdev.backendstarter.service.ia.OpenAiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/ia")
@RequiredArgsConstructor
@Slf4j
public class IaController {

    private final OpenAiService openAiService;

    @PostMapping("/complete")
    public ResponseEntity<IaResponse> completePrompt(@RequestBody PromptRequest request) {
        // Establecemos el traceId manualmente si no existe
        if (MDC.get("traceId") == null) {
            String traceId = UUID.randomUUID().toString();
            MDC.put("traceId", traceId);
            log.debug("🧵 traceId generado en controller: {}", traceId);
        }
        openAiService.enqueuePrompt(request.prompt(), MDC.get("traceId"));
        return ResponseEntity.accepted()
                .body(new IaResponse("🕐 Prompt recibido. Procesando en background..."));
    }
}
