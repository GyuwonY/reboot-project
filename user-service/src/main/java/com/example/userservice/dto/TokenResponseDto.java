package com.example.userservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Setter;

@Setter
@Builder
@AllArgsConstructor
public class TokenResponseDto {
    private String token;
}
