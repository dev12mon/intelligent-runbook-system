package com.runbook.ai_analyzer_service;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "incident_runbooks")
public class RunbookEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String serviceName;
    private String errorMessage;

    @Column(columnDefinition = "TEXT")
    private String runbookContent;

    private LocalDateTime createdAt;

    public RunbookEntity() {
        this.createdAt = LocalDateTime.now();
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getServiceName() { return serviceName; }
    public void setServiceName(String serviceName) { this.serviceName = serviceName; }
    public String getErrorMessage() { return errorMessage; }
    public void setErrorMessage(String errorMessage) { this.errorMessage = errorMessage; }
    public String getRunbookContent() { return runbookContent; }
    public void setRunbookContent(String runbookContent) { this.runbookContent = runbookContent; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}