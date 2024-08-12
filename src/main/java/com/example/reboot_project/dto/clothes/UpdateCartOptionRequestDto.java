package com.example.reboot_project.dto.clothes;

import com.example.reboot_project.entity.clothes.enums.CartStatusEnum;
import lombok.Getter;

@Getter
public class UpdateCartOptionRequestDto {
    String cartId;
    CartStatusEnum status;
    int count;
}
