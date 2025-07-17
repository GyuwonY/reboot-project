package com.example.stockservice.controller;

import com.example.stockservice.dto.GetStockResponseDto;
import com.example.stockservice.dto.UpdateStockRequestDto;
import com.example.stockservice.service.StockService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/stock")
public class StockController {

    private final StockService stockService;

    public StockController(
            StockService stockService
    ) {
        this.stockService = stockService;
    }

    @GetMapping("/{id}")
    public GetStockResponseDto getStockById(
            @PathVariable("id") String clothesOptionId
    ) {
        return stockService.getStockById(clothesOptionId);
    }
}
