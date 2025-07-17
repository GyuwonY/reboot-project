package com.example.stockservice.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class EventPublishDto {
    String optionId;
    int count;
}
