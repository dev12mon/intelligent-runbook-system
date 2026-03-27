package com.runbook.ai_analyzer_service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    // 1. This tells Spring to automatically create the queue if it's missing!
    @Bean
    public Queue logQueue() {
        return new Queue("log.ingestion.queue", true);
    }

    // 2. This converts incoming JSON back into our LogEvent object
    @Bean
    public MessageConverter jsonMessageConverter(ObjectMapper objectMapper) {
        return new Jackson2JsonMessageConverter(objectMapper);
    }
}