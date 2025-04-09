package com.carlosrdev.backendstarter.controller;

import com.carlosrdev.backendstarter.dto.IaResponse;
import com.carlosrdev.backendstarter.dto.PromptRequest;
import com.carlosrdev.backendstarter.service.ia.OpenAiService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ia")
@RequiredArgsConstructor
public class IaController {

    private final OpenAiService openAiService;

    @PostMapping("/complete")
    public ResponseEntity<IaResponse> completePrompt(@RequestBody PromptRequest request) {
        openAiService.enqueuePrompt(request.prompt());
        return ResponseEntity.accepted()
                .body(new IaResponse("🕐 Prompt recibido. Procesando en background..."));
    }
}
