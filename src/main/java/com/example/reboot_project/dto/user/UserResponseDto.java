package com.example.reboot_project.dto.user;

import com.example.reboot_project.entity.enums.UserStatusEnum;
import com.example.reboot_project.utils.EncryptionUtil;
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
