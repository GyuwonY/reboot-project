package com.example.clothesservice.dto;

import com.example.common.entity.enums.clothes.ClothesOptionTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Setter;

@Setter
@Builder
@AllArgsConstructor
public class ClothesOptionResponseDto {
    String title;
    ClothesOptionTypeEnum type;
    int price;
    int stock;
}
