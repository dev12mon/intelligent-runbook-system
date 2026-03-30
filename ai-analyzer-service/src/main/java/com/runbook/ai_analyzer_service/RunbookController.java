package com.runbook.ai_analyzer_service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/runbooks")
public class RunbookController {

    @Autowired
    private RunbookRepository runbookRepository;

    // Fetch all runbooks
    @GetMapping
    public List<RunbookEntity> getAllRunbooks() {
        return runbookRepository.findAll();
    }

    // Java 8 Stream Example: Get only payment-service issues
    @GetMapping("/payment-issues")
    public List<RunbookEntity> getPaymentRunbooks() {
        return runbookRepository.findAll().stream()
                .filter(runbook -> "payment-service".equals(runbook.getServiceName()))
                .sorted(Comparator.comparing(RunbookEntity::getCreatedAt).reversed())
                .collect(Collectors.toList());
    }

    // Java 8 Optional Example: Get by ID
    @GetMapping("/{id}")
    public ResponseEntity<RunbookEntity> getRunbookById(@PathVariable Long id) {
        return runbookRepository.findById(id)
                .map(runbook -> ResponseEntity.ok().body(runbook))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}