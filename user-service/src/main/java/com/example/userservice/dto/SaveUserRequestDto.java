package com.example.userservice.dto;

import com.example.common.entity.enums.user.DeviceTypeEnum;
import com.example.userservice.utils.EncryptionUtil;
import lombok.Getter;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
public class SaveUserRequestDto {
    private String email;
    private String password;
    private String name;
    private String address;
    private String detailAddress;
    private DeviceTypeEnum deviceType;

    public void encryptInfo(EncryptionUtil encryptionUtil, PasswordEncoder passwordEncoder) {
        this.name = encryptionUtil.encrypt(name);
        this.address = encryptionUtil.encrypt(address);
        this.detailAddress = encryptionUtil.encrypt(detailAddress);
        this.password = passwordEncoder.encode(password);
    }
}
