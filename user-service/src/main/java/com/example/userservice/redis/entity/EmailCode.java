package com.example.userservice.redis.entity;

import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.redis.core.RedisHash;

@Getter
@Builder
@RedisHash(value = "emailcode", timeToLive = 300)
public class EmailCode {
    @Id
    String id;

    String code;
}
