package com.example.orderservice.dto;

import com.example.common.entity.enums.order.OrderStatusEnum;
import com.example.common.entity.enums.order.PaymentTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Setter;

import java.util.List;

@Setter
@Builder
@AllArgsConstructor
public class OrderListResponseDto {
    String orderId;
    PaymentTypeEnum paymentType;
    OrderStatusEnum orderStatus;
    List<OrderClothesOptionResponseDto> clothesOptionList;
    int amount;
}
