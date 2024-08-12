package com.example.reboot_project.dto.order;

import com.example.reboot_project.dto.clothes.CartOptionRequestDto;
import com.example.reboot_project.entity.order.enums.PaymentTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class SaveOrderRequestDto {
    PaymentTypeEnum paymentType;
    int clothesCount;
    List<CartOptionRequestDto> cartOptionList;
}
