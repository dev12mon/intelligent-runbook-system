package com.runbook.ai_analyzer_service;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LogConsumer {

    @Autowired
    private AiRunbookGenerator aiRunbookGenerator;

    @RabbitListener(queues = "log.ingestion.queue")
    public void consumeLog(LogEvent logEvent) {
        System.out.println("\n🚨 NEW LOG DETECTED 🚨");
        System.out.println("Service: " + logEvent.getServiceName());
        System.out.println("Error: " + logEvent.getErrorMessage());
        System.out.println("Generating AI Runbook... Please wait (this takes 2-4 seconds)...");
        
        // Call the Gemini API
        String runbook = aiRunbookGenerator.generateRunbook(logEvent);
        
        System.out.println("\n🤖 --- AI RUNBOOK GENERATED --- 🤖");
        System.out.println(runbook);
        System.out.println("-----------------------------------\n");
    }
}