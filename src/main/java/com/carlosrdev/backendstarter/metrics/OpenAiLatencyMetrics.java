package com.carlosrdev.backendstarter.metrics;

import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class OpenAiLatencyMetrics {

    private final MeterRegistry registry;

    // Esta variable almacenará el último valor de latencia en segundos
    private volatile double openAiLatencySeconds = 0.0;

    public OpenAiLatencyMetrics(MeterRegistry registry) {
        this.registry = registry;
    }

    @PostConstruct
    public void init() {
        Gauge.builder("openai_latency_seconds", this, OpenAiLatencyMetrics::getOpenAiLatencySeconds)
                .description("Latency to reach OpenAI in seconds")
                .register(registry);
    }

    public double getOpenAiLatencySeconds() {
        return openAiLatencySeconds;
    }

    // Método que puedes llamar desde donde midas latencia (por ejemplo, desde el health check)
    public void updateLatency(long millis) {
        this.openAiLatencySeconds = millis / 1000.0;
    }
}
