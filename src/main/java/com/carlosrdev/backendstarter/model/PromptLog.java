package com.carlosrdev.backendstarter.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PromptLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String prompt;

    @Column(length = 5000)
    private String response;

    private boolean success;

    private LocalDateTime timestamp;

    @Column(name = "trace_id")
    private String traceId;

    @Column(name = "model")
    private String model;

    @Column(name = "latency_ms")
    private Long latencyMs;

    @Column(name = "status_code")
    private Integer statusCode;

    @Column(name = "source_info")
    private String sourceInfo;

}

