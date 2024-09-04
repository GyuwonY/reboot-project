package com.example.rediseventpublisher.redis;

import org.redisson.api.RTopic;
import org.redisson.api.RedissonClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RedissonConfig {
    private RedissonClient redissonClient;
    private TTLEventListener ttlEventListener;

    public RedissonConfig(
            RedissonClient redissonClient,
            TTLEventListener ttlEventListener
    ) {
        this.redissonClient = redissonClient;
        this.ttlEventListener = ttlEventListener;
    }

    @Bean
    public void subscribe() {
        RTopic topic = redissonClient.getTopic("__keyevent@0__:expired");
        topic.addListener(String.class, ttlEventListener);
    }
}