package com.example.userservice.dto;

import com.example.common.entity.enums.user.UserStatusEnum;
import com.example.userservice.utils.EncryptionUtil;
import lombok.Builder;
import lombok.Setter;

@Setter
@Builder
public class UserResponseDto {
    private String email;
    private String name;
    private String address;
    private String detailAddress;
    private UserStatusEnum status;

    public void decryptInfo() {
        this.email = EncryptionUtil.decrypt(email);
        this.name = EncryptionUtil.decrypt(name);
        this.address = EncryptionUtil.decrypt(address);
        this.detailAddress = EncryptionUtil.decrypt(detailAddress);
    }
}
