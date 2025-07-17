package com.example.stockservice.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class SaveWaitingPayReqDto {
    String orderId;
    List<UpdateStockRequestDto> updateStockRequestDtoList;
}
