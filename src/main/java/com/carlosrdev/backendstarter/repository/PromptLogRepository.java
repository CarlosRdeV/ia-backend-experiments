package com.carlosrdev.backendstarter.repository;

import com.carlosrdev.backendstarter.model.PromptLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PromptLogRepository extends JpaRepository<PromptLog, Long> {
}

