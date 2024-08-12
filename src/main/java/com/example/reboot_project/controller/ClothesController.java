package com.example.reboot_project.controller;

import com.example.reboot_project.dto.clothes.ClothesListResponseDto;
import com.example.reboot_project.dto.clothes.ClothesResponseDto;
import com.example.reboot_project.service.ClothesService;
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
