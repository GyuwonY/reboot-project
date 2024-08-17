package com.example.userservice.dto;

import com.example.common.entity.enums.user.DeviceTypeEnum;
import com.example.userservice.utils.EncryptionUtil;
import lombok.Getter;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
public class AuthUserRequestDto {
    private String email;
    private String password;
    private DeviceTypeEnum deviceType;
}
