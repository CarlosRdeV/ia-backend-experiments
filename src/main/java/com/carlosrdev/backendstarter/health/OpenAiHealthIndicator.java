package com.carlosrdev.backendstarter.health;

import com.carlosrdev.backendstarter.metrics.OpenAiLatencyMetrics;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class OpenAiHealthIndicator implements HealthIndicator {

    private final WebClient webClient;
    private final OpenAiLatencyMetrics openAiLatencyMetrics;

    @Value("${openai.api-key}")
    private String apiKey;

    private long lastCheckTime = 0;
    private Health cachedHealth = Health.unknown().withDetail("message", "Sin datos aún").build();

    private static final long CACHE_DURATION_MS = 2 * 60 * 1000; // 5 minutos

    public OpenAiHealthIndicator(WebClient.Builder builder, OpenAiLatencyMetrics openAiLatencyMetrics) {
        this.webClient = builder.baseUrl("https://api.openai.com").build();
        this.openAiLatencyMetrics = openAiLatencyMetrics;
    }

    @Override
    public Health health() {
        long now = System.currentTimeMillis();

        if (now - lastCheckTime < CACHE_DURATION_MS) {
            return cachedHealth; // 🧊 Reusar el último resultado
        }

        try {
            long start = System.currentTimeMillis();
            webClient.get()
                    .uri("/v1/models")
                    .header("Authorization", "Bearer " + apiKey)
                    .retrieve()
                    .toBodilessEntity()
                    .block();

            long latency = System.currentTimeMillis() - start;
            openAiLatencyMetrics.updateLatency(latency);

            cachedHealth = Health.up()
                    .withDetail("latency", latency + "ms")
                    .withDetail("message", "OpenAI responde como una diva puntual")
                    .build();

        } catch (Exception e) {
            cachedHealth = Health.down()
                    .withDetail("error", e.getMessage())
                    .withDetail("message", "OpenAI se escondió bajo la mesa")
                    .build();
        }

        lastCheckTime = now;
        return cachedHealth;
    }
}
