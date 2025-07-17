package com.example.clothesservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Setter;

@Setter
@AllArgsConstructor
@Builder
public class GetStockByOptionIdResDto {
    String id;
    int stock;
}
