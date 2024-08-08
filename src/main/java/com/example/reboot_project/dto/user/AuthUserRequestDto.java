package com.example.reboot_project.dto.user;

import com.example.reboot_project.entity.enums.DeviceTypeEnum;
import com.example.reboot_project.utils.EncryptionUtil;
import com.example.reboot_project.utils.PasswordEncoder;
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
