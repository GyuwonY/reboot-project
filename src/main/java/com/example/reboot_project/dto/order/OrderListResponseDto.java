package com.example.reboot_project.dto.order;

import com.example.reboot_project.entity.order.enums.OrderStatusEnum;
import com.example.reboot_project.entity.order.enums.PaymentTypeEnum;
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
