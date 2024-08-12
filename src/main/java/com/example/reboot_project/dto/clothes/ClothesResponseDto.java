package com.example.reboot_project.dto.clothes;

import com.example.reboot_project.entity.clothes.enums.ClothesTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Setter;

import java.util.List;

@Setter
@Builder
@AllArgsConstructor
public class ClothesResponseDto {
    String title;
    ClothesTypeEnum type;
    int price;
    int stock;
    List<ClothesOptionResponseDto> clothesOptionList;
}
