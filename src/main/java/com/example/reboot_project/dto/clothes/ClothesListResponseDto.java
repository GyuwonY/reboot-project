package com.example.reboot_project.dto.clothes;

import com.example.reboot_project.entity.clothes.enums.ClothesTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Setter;

@Setter
@Builder
@AllArgsConstructor
public class ClothesListResponseDto {
    String title;
    ClothesTypeEnum type;
    int price;
    int stock;
}
