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
        System.out.println("\n🚨 NEW LOG DETECTED 🚨");
        System.out.println("Service: " + logEvent.getServiceName());
        System.out.println("Error: " + logEvent.getErrorMessage());
        
        // Generate Runbook
        String runbook = aiRunbookGenerator.generateRunbook(logEvent);
        
        System.out.println("\n🤖 --- AI RUNBOOK GENERATED --- 🤖");
        System.out.println(runbook);
        System.out.println("-----------------------------------\n");

        // Save to Database
        RunbookEntity entity = new RunbookEntity();
        entity.setServiceName(logEvent.getServiceName());
        entity.setErrorMessage(logEvent.getErrorMessage());
        entity.setRunbookContent(runbook);
        
        runbookRepository.save(entity);
        System.out.println("✅ SUCCESS: Runbook securely saved to PostgreSQL Database!");
    }
}