package com.example.rediseventpublisher.redis;

import com.example.rediseventpublisher.dto.EventPublishDto;
import com.example.rediseventpublisher.kafka.TTLEventProducer;
import org.redisson.api.listener.MessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TTLEventListener implements MessageListener<String> {
    private static final String ORDER = "ORDER:";
    private final TTLEventProducer ttlEventProducer;

    @Autowired
    public TTLEventListener(
            TTLEventProducer ttlEventProducer
    ) {
        this.ttlEventProducer = ttlEventProducer;
    }

    @Override
    public void onMessage(CharSequence channel, String msg) {
        if (msg.startsWith(ORDER)) {
            String[] stringIds = msg.split(":");
            String optionId = stringIds[2];
            int count = Integer.parseInt(stringIds[3]);

            ttlEventProducer.produceOptionTTL(
                    EventPublishDto.builder()
                            .optionId(optionId)
                            .count(count)
                            .build()
            );
        }
    }
}