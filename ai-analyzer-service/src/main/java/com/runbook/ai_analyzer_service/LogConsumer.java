package com.runbook.ai_analyzer_service;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LogConsumer {

    @Autowired
    private AiRunbookGenerator aiRunbookGenerator;

    @Autowired
    private RunbookRepository runbookRepository; // Database connection injected!
@RabbitListener(queues = "log.ingestion.queue")
public void consumeLog(LogEvent logEvent) {
    System.out.println("Received log, handing off to background thread...");

    // Run the heavy AI generation in a completely separate thread
    CompletableFuture.supplyAsync(() -> aiRunbookGenerator.generateRunbook(logEvent))
            .thenAccept(runbook -> {
                // This block executes whenever the AI finally finishes
                RunbookEntity entity = new RunbookEntity();
                entity.setServiceName(logEvent.getServiceName());
                entity.setRunbookContent(runbook);
                runbookRepository.save(entity);
                System.out.println("✅ Background Task: Saved to DB!");
            })
            .exceptionally(ex -> {
                System.err.println("Failed to generate AI runbook: " + ex.getMessage());
                return null;
            });
}
}