package com.example.userservice.redis.repository;

import com.example.userservice.redis.entity.EmailCode;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmailCodeRepository extends CrudRepository<EmailCode, String> {
}
