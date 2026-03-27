package com.runbook.ai_analyzer_service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RunbookRepository extends JpaRepository<RunbookEntity, Long> {
}