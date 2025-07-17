package com.example.orderservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class SaveOrderRequestDto {
    String userId;
    List<OrderOptionRequestDto> orderOptionList;
}
