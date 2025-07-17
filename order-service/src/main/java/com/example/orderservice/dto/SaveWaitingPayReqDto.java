package com.example.orderservice.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class SaveWaitingPayReqDto {
    String orderId;
    List<UpdateStockRequestDto> updateStockRequestDtoList;
}
