package com.example.clothesservice.dto;

import com.example.common.entity.enums.clothes.ClothesOptionTypeEnum;
import com.example.common.entity.enums.clothes.ClothesTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Setter;

@Setter
@Builder
@AllArgsConstructor
public class CartListResponseDto {
    String clothesTitle;
    String optionTitle;
    ClothesTypeEnum clothesType;
    ClothesOptionTypeEnum optionType;
    int price;
    int count;
}
