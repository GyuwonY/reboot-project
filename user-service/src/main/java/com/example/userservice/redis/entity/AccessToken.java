package com.example.userservice.redis.entity;

import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.redis.core.RedisHash;

@Getter
@Builder
@RedisHash(value = "accessToken", timeToLive = 3600)
public class AccessToken {
    @Id
    String id;

    String token;
}
