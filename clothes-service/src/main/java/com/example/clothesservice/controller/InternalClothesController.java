package com.example.clothesservice.controller;

import com.example.clothesservice.dto.ClothesOptionListByIdsResponseDto;
import com.example.clothesservice.dto.GetStockByOptionIdResDto;
import com.example.clothesservice.dto.UpdateStockRequestDto;
import com.example.clothesservice.service.ClothesService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/internal/clothes")
public class InternalClothesController {

    private final ClothesService clothesService;

    public InternalClothesController(
            ClothesService clothesService
    ) {
        this.clothesService = clothesService;
    }

    @GetMapping("/option/list")
    public List<ClothesOptionListByIdsResponseDto> getClothesOptionListByIds(
            @RequestBody List<String> idList
    ) {
        return clothesService.getClothesListByIds(idList);
    }

    @PutMapping("/option/stock")
    public void updateStock(
            @RequestBody List<UpdateStockRequestDto> optionList
    ) {
        clothesService.updateStock(optionList);
    }

    @GetMapping("/option/{id}/stock")
    public GetStockByOptionIdResDto getStockByOptionId(
            @PathVariable("id") String id
    ) {
        return clothesService.getStockByOptionId(id);
    }
}
