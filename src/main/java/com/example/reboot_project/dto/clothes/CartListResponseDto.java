package com.example.reboot_project.dto.clothes;

import com.example.reboot_project.entity.clothes.enums.ClothesOptionTypeEnum;
import com.example.reboot_project.entity.clothes.enums.ClothesTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Setter;

import java.util.List;

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
