package com.carlosrdev.backendstarter.service.ia;

import com.carlosrdev.backendstarter.model.PromptLog;
import com.carlosrdev.backendstarter.repository.PromptLogRepository;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class OpenAiService {

    private final OpenAiRequestBuilder requestBuilder;
    private final OpenAiResponseParser responseParser;

    @Value("${openai.api-key}")
    private String apiKey;

    @Value("${openai.model}")
    private String model;

    @Value("${openai.url}")
    private String apiUrl;

    // Cola para controlar las peticiones
    private final BlockingQueue<String> promptQueue = new LinkedBlockingQueue<>();
    private ExecutorService executorService;
    private WebClient webClient;
    private final PromptLogRepository promptLogRepository;


    @PostConstruct
    public void init() {
        log.info("🔐 API Key: {}", apiKey != null ? "cargada" : "NO cargada");
        log.info("🧠 Modelo: {}", model);
        log.info("🌐 URL: {}", apiUrl);

        this.webClient = WebClient.builder()
                .baseUrl(apiUrl)
                .defaultHeader("Authorization", "Bearer " + apiKey)
                .build();

        // Iniciar un hilo consumidor
        executorService = Executors.newSingleThreadExecutor();
        executorService.submit(this::processQueue);
    }

    @PreDestroy
    public void shutdown() {
        executorService.shutdown();
        log.info("🛑 Servicio OpenAI apagado correctamente.");
    }

    // Método expuesto para usar desde el controller
    public void enqueuePrompt(String prompt) {
        try {
            promptQueue.put(prompt);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.error("❌ Error al encolar prompt: {}", e.getMessage());
        }
    }

    private void processQueue() {
        while (true) {
            try {
                String prompt = promptQueue.take(); // bloquea hasta que haya algo
                log.info("🧾 Procesando prompt: {}", prompt);

                String result = callOpenAi(prompt);
                log.info("✅ Respuesta OpenAI: {}", result);

                promptLogRepository.save(PromptLog.builder()
                        .prompt(prompt)
                        .response(result)
                        .success(!result.startsWith("❌") && !result.startsWith("🤖"))
                        .timestamp(LocalDateTime.now())
                        .build());


                // Delay entre peticiones (puedes ajustar o quitar)
                Thread.sleep(5000);

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                log.warn("🔌 Proceso de cola interrumpido.");
                break;
            }
        }
    }

    private String callOpenAi(String prompt) {
        Map<String, Object> request = requestBuilder.buildChatRequest(prompt, model);

        try {
            Map response = webClient.post()
                    .bodyValue(request)
                    .retrieve()
                    .onStatus(
                            status -> status.value() == 429,
                            clientResponse -> {
                                log.warn("🚦 Límite alcanzado: 429 Too Many Requests");
                                return clientResponse.createException();
                            }
                    )
                    .bodyToMono(Map.class)
                    .retryWhen(
                            Retry.backoff(5, Duration.ofSeconds(10))
                                    .maxBackoff(Duration.ofSeconds(30))
                                    .filter(throwable -> throwable instanceof WebClientResponseException.TooManyRequests)
                                    .onRetryExhaustedThrow((spec, signal) ->
                                            new RuntimeException("❌ Exceso de intentos: OpenAI sigue diciendo 'NO'."))
                    )
                    .block();

            return responseParser.extractContentFromResponse(response);

        } catch (WebClientResponseException.TooManyRequests e) {
            log.error("💥 Demasiadas solicitudes: {}", e.getMessage());
            return "🤖 OpenAI está ocupado. Intenta más tarde.";
        } catch (WebClientResponseException e) {
            log.error("⚠️ Error HTTP al llamar a OpenAI: {}", e.getMessage());
            return "❌ Error al contactar a OpenAI: " + e.getStatusCode();
        } catch (Exception e) {
            log.error("🔥 Error inesperado: {}", e.getMessage(), e);
            return "❌ Algo salió mal. Detalles: " + e.getMessage();
        }
    }
}
