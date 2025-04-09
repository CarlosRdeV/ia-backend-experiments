package com.carlosrdev.backendstarter.service.ia;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class OpenAiResponseParser {

    public String extractContentFromResponse(Map response) {
        Map choice = (Map) ((List) response.get("choices")).get(0);
        Map messageContent = (Map) choice.get("message");

        return messageContent.get("content").toString();
    }
}
