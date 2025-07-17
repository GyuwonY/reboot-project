package com.example.rediseventpublisher.kafka;

import com.example.rediseventpublisher.dto.EventPublishDto;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class TTLEventProducer {
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public TTLEventProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void produceOptionTTL(EventPublishDto eventPublishDto) {
        kafkaTemplate.send("Option-TTL", eventPublishDto);
    }
}