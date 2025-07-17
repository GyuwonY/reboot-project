package com.example.orderservice.client;

import com.example.orderservice.dto.ClothesOptionListByIdsResponseDto;
import com.example.orderservice.dto.UpdateStockRequestDto;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "clothes")
public interface ClothesClient {
    @CircuitBreaker(name = "default")
    @Retry(name = "default")
    @GetMapping("/internal/clothes/option/list")
    List<ClothesOptionListByIdsResponseDto> getClothesOptionListByIds(
            @RequestBody List<String> idList
    );
}
