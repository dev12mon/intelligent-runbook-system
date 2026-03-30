package com.runbook.ai_analyzer_service;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class LogConsumer {

    @Autowired
    private AiRunbookGenerator aiRunbookGenerator;

    @Autowired
    private RunbookRepository runbookRepository;

    @RabbitListener(queues = "log.ingestion.queue")
    public void consumeLog(LogEvent logEvent) {
        System.out.println("\n🚨 NEW LOG DETECTED: Handing off to background thread... 🚨");

        // Java 8 CompletableFuture for Asynchronous Processing
        CompletableFuture.supplyAsync(() -> aiRunbookGenerator.generateRunbook(logEvent))
                .thenAccept(runbook -> {
                    System.out.println("\n🤖 --- AI RUNBOOK GENERATED --- 🤖");
                    System.out.println(runbook);
                    
                    RunbookEntity entity = new RunbookEntity();
                    entity.setServiceName(logEvent.getServiceName());
                    entity.setErrorMessage(logEvent.getErrorMessage());
                    entity.setRunbookContent(runbook);
                    
                    runbookRepository.save(entity);
                    System.out.println("✅ SUCCESS: Runbook securely saved to PostgreSQL Database!");
                })
                .exceptionally(ex -> {
                    System.err.println("Failed to generate AI runbook: " + ex.getMessage());
                    return null;
                });
    }
}