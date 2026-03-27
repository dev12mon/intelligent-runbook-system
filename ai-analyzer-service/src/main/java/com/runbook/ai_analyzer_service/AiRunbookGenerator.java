package com.runbook.ai_analyzer_service;

import org.springframework.stereotype.Service;

@Service
public class AiRunbookGenerator {

    public String generateRunbook(LogEvent logEvent) {
        
        // Simulate the AI taking 2 seconds to "think"
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Generate a dynamic runbook based on the error level
        if ("CRITICAL".equalsIgnoreCase(logEvent.getLevel())) {
            return String.format("""
                [SRE RUNBOOK: %s]
                1. 🔴 [INVESTIGATE] Verify the database connection pool metrics in your monitoring dashboard.
                2. 🟡 [MITIGATE] If connection pool is exhausted, temporarily increase 'max-active' connections in config.
                3. 🔍 [ROOT CAUSE] Check slow query logs on the database server to identify queries blocking the connections.
                4. 🚨 [ESCALATE] If database CPU is > 90%%, page the DBA on-call immediately.
                """, logEvent.getServiceName());
        } else {
            return "1. Monitor the service for further occurrences.\n2. Create a Jira ticket for the dev team to review the logs during business hours.";
        }
    }
}