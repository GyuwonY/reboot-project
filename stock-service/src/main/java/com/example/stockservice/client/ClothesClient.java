package com.example.stockservice.client;

import com.example.stockservice.dto.GetStockByOptionIdResDto;
import com.example.stockservice.dto.UpdateStockRequestDto;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "clothes")
public interface ClothesClient {
    @CircuitBreaker(name = "default")
    @Retry(name = "default")
    @PutMapping("/internal/clothes/option/stock")
    void updateStock(
            @RequestBody List<UpdateStockRequestDto> optionList
    );

    @CircuitBreaker(name = "default")
    @Retry(name = "default")
    @GetMapping("/internal/clothes/option/{id}/stock")
    GetStockByOptionIdResDto getStockByOptionId(
            @PathVariable("id") String id
    );
}