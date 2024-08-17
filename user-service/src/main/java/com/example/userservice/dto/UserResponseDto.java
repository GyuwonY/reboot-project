package com.example.userservice.dto;

import com.example.common.entity.enums.user.UserStatusEnum;
import com.example.userservice.utils.EncryptionUtil;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserResponseDto {
    private String email;
    private String name;
    private String address;
    private String detailAddress;
    private UserStatusEnum status;

    public void decryptInfo(EncryptionUtil encryptionUtil) {
        this.name = encryptionUtil.decrypt(name);
        this.address = encryptionUtil.decrypt(address);
        this.detailAddress = encryptionUtil.decrypt(detailAddress);
    }
}
