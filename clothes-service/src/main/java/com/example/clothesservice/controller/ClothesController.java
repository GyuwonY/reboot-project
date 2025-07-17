package com.example.clothesservice.controller;

import com.example.clothesservice.dto.ClothesListResponseDto;
import com.example.clothesservice.dto.ClothesResponseDto;
import com.example.clothesservice.service.ClothesService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/clothes")
public class ClothesController {
    private final ClothesService clothesService;

    public ClothesController(
            ClothesService clothesService
    ) {
        this.clothesService = clothesService;
    }

    @GetMapping("/list")
    public List<ClothesListResponseDto> getClothesList() {
        return clothesService.getClothesList();
    }

    @GetMapping("/{id}")
    public ClothesResponseDto getClothesById(
            @PathVariable("id") String clothesId
    ) {
        return clothesService.getClothesById(clothesId);
    }
}
