package com.example.orderservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
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
