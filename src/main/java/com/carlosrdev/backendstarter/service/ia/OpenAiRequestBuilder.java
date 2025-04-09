package com.carlosrdev.backendstarter.service.ia;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class OpenAiRequestBuilder {

    public Map<String, Object> buildChatRequest(String prompt, String model) {
        Map<String, Object> message = Map.of(
                "role", "user",
                "content", prompt
        );

        return Map.of(
                "model", model,
                "messages", List.of(message)
        );
    }
}

