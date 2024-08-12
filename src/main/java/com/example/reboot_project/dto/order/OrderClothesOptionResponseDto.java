package com.example.reboot_project.dto.order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Setter;

@Setter
@Builder
@AllArgsConstructor
public class OrderClothesOptionResponseDto {
    String clothesId;
    String clothesTitle;
    String clothesOptionId;
    String optionTitle;
    int count;
    int price;
}
