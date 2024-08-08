package com.example.reboot_project.dto.user;

import com.example.reboot_project.entity.enums.DeviceTypeEnum;
import com.example.reboot_project.utils.EncryptionUtil;
import com.example.reboot_project.utils.PasswordEncoder;
import lombok.Getter;

@Getter
public class SaveUserRequestDto {
    private String email;
    private String password;
    private String name;
    private String address;
    private String detailAddress;
    private DeviceTypeEnum deviceType;

    public void encryptInfo() {
        this.email = EncryptionUtil.encrypt(email);
        this.name = EncryptionUtil.encrypt(name);
        this.address = EncryptionUtil.encrypt(address);
        this.detailAddress = EncryptionUtil.encrypt(detailAddress);
        this.password = PasswordEncoder.hash(password);
    }
}
