package com.example.clothesservice.dto;

import com.example.common.entity.enums.clothes.CartStatusEnum;
import lombok.Getter;

@Getter
public class UpdateCartOptionRequestDto {
    String cartId;
    CartStatusEnum status;
    int count;
}
