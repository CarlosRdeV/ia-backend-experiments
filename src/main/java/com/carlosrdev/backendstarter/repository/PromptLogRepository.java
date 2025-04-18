package com.carlosrdev.backendstarter.repository;

import com.carlosrdev.backendstarter.model.PromptLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PromptLogRepository extends JpaRepository<PromptLog, Long> {

    Page<PromptLog> findByTraceId(String traceId, Pageable pageable);

}

