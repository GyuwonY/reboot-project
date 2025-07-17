package com.example.orderservice.client;

import com.example.orderservice.dto.SaveWaitingPayReqDto;
import com.example.orderservice.dto.UpdateStockRequestDto;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "stock")
public interface StockClient {
    @CircuitBreaker(name = "default")
    @Retry(name = "default")
    @PostMapping()
    void saveWaitingPay(
            @RequestBody SaveWaitingPayReqDto saveWaitingPayReqDto
    );

    @CircuitBreaker(name = "default")
    @Retry(name = "default")
    @DeleteMapping()
    void deleteWaitingPay(
            @RequestBody SaveWaitingPayReqDto saveWaitingPayReqDto
    );

    @CircuitBreaker(name = "default")
    @Retry(name = "default")
    @PutMapping()
    void updateStock(
            @RequestBody List<UpdateStockRequestDto> updateStockRequestDtoList
    );
}
