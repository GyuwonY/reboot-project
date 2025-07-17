package com.example.stockservice.controller;

import com.example.stockservice.dto.SaveWaitingPayReqDto;
import com.example.stockservice.dto.UpdateStockRequestDto;
import com.example.stockservice.service.StockService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/internal/stock")
public class InternalStockController {

    private final StockService stockService;

    public InternalStockController(
            StockService stockService
    ) {
        this.stockService = stockService;
    }

    @PutMapping()
    public void updateStock(
            @RequestBody List<UpdateStockRequestDto> updateStockRequestDtoList
    ) {
        stockService.updateStock(updateStockRequestDtoList);
    }

    @PostMapping()
    public void saveWaitingPay(
            @RequestBody SaveWaitingPayReqDto saveWaitingPayReqDto
    ) {
        stockService.saveWaitingPay(saveWaitingPayReqDto);
    }

    @DeleteMapping()
    public void deleteWaitingPay(
            @RequestBody SaveWaitingPayReqDto saveWaitingPayReqDto
    ) {
        stockService.deleteWaitingPay(saveWaitingPayReqDto);
    }
}
