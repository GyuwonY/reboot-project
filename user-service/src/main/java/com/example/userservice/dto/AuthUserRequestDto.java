package com.example.userservice.dto;

import com.example.common.entity.enums.user.DeviceTypeEnum;
import com.example.userservice.utils.EncryptionUtil;
import com.example.userservice.utils.PasswordEncoder;
import lombok.Getter;

@Getter
public class AuthUserRequestDto {
    private String email;
    private String password;
    private DeviceTypeEnum deviceType;

    public void encryptInfo() {
        this.email = EncryptionUtil.encrypt(email);
        this.password = PasswordEncoder.hash(password);
    }
}
