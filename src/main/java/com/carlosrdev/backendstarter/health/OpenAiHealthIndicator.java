package com.carlosrdev.backendstarter.health;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class OpenAiHealthIndicator implements HealthIndicator {

    private final WebClient webClient;

    @Value("${openai.api-key}")
    private String apiKey;

    public OpenAiHealthIndicator(WebClient.Builder builder) {
        this.webClient = builder
                .baseUrl("https://api.openai.com")
                .build();
    }

    @Override
    public Health health() {
        try {
            long start = System.currentTimeMillis();
            webClient.get()
                    .uri("/v1/models") // no consume tokens y responde rápido
                    .header("Authorization", "Bearer " + apiKey) // o usa @Value si prefieres
                    .retrieve()
                    .toBodilessEntity()
                    .block();

            long latency = System.currentTimeMillis() - start;

            return Health.up()
                    .withDetail("latency", latency + "ms")
                    .withDetail("message", "OpenAI está despierto y ligeramente irritado")
                    .build();

        } catch (Exception e) {
            return Health.down()
                    .withDetail("error", e.getMessage())
                    .withDetail("message", "OpenAI no responde. Está llorando en una esquina.")
                    .build();
        }
    }
}
