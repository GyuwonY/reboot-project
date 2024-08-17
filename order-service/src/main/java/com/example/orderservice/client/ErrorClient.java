package com.example.orderservice.client;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "errorClient", url = "http://user:8080/errorful")
public interface ErrorClient {

    @CircuitBreaker(name = "default")
    @Retry(name = "default")
    @GetMapping("/case1")
    void case1();

    @CircuitBreaker(name = "default")
    @Retry(name = "default")
    @GetMapping("/case1")
    void case2();

    @CircuitBreaker(name = "default")
    @Retry(name = "default")
    @GetMapping("/case1")
    void case3();
}
