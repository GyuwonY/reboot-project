package com.example.orderservice.dto;


import com.example.common.entity.enums.order.PaymentTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class SaveOrderRequestDto {
    String userId;
    PaymentTypeEnum paymentType;
    int clothesCount;
    List<OrderOptionRequestDto> orderOptionList;
}
