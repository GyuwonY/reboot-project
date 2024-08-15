package com.example.userservice.redis.repository;

import com.example.userservice.redis.entity.AccessToken;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccessTokenRepository extends CrudRepository<AccessToken, String> {
    void deleteAllByDeviceIdIn(List<String> deviceIdList);
}
