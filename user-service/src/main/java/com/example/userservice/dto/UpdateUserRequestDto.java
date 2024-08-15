package com.example.userservice.dto;

import com.example.common.entity.enums.user.UserStatusEnum;
import com.example.userservice.utils.EncryptionUtil;
import com.example.userservice.utils.PasswordEncoder;
import lombok.Getter;

@Getter
public class UpdateUserRequestDto {
    private String password;
    private String name;
    private String address;
    private String detailAddress;
    private UserStatusEnum status;

    public void encryptInfo() {
        this.name = EncryptionUtil.encrypt(name);
        this.address = EncryptionUtil.encrypt(address);
        this.detailAddress = EncryptionUtil.encrypt(detailAddress);
        this.password = PasswordEncoder.hash(password);
    }
}
