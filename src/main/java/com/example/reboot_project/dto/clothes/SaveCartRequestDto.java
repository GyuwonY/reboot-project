package com.example.reboot_project.dto.clothes;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class SaveCartRequestDto {
    List<CartOptionRequestDto> cartOptionList;
}
