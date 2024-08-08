package com.example.reboot_project.dto.mail;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Builder
public class AuthMailCode {
    private String code;
    private final LocalDateTime expireTime = LocalDateTime.now().plusMinutes(5);
}
