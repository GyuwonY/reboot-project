package com.example.stockservice.consumer;

import com.example.stockservice.dto.EventPublishDto;
import com.example.stockservice.service.StockService;
import org.redisson.api.RBucket;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumer {
    private final RedissonClient redissonClient;

    public KafkaConsumer(
            RedissonClient redissonClient
    ){
        this.redissonClient = redissonClient;
    }

    @KafkaListener(topics = "Option-TTL", groupId = "option-ttl-group")
    public void listen(EventPublishDto dto) {
        RLock lock = redissonClient.getLock(StockService.STOCK + dto.getOptionId());

        try {
            lock.lock();
            RBucket<Integer> bucket = redissonClient.getBucket(StockService.STOCK + dto.getOptionId());
            bucket.set(bucket.get() + dto.getCount());
        } finally {
            lock.unlock();
        }
    }
}