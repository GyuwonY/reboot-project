package com.example.clothesservice.dto;

import com.example.common.entity.enums.clothes.ClothesOptionTypeEnum;
import com.example.common.entity.enums.clothes.ClothesTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Setter;

@Setter
@Builder
@AllArgsConstructor
public class ClothesOptionListByIdsResponseDto {
    String clothesId;
    String clothesTitle;
    String clothesOptionId;
    String optionTitle;
    ClothesOptionTypeEnum type;
    int price;
    int stock;

}
