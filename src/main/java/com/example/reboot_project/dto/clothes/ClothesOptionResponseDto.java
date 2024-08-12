package com.example.reboot_project.dto.clothes;

import com.example.reboot_project.entity.clothes.enums.ClothesOptionTypeEnum;
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
