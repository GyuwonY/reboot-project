package com.example.clothesservice.dto;

import com.example.common.entity.enums.clothes.ClothesTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class ClothesListResponseDto {
    String title;
    ClothesTypeEnum type;
    int price;
    int stock;
}
