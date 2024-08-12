package com.example.reboot_project.entity.user;

import com.example.reboot_project.entity.BaseEntity;
import com.example.reboot_project.entity.user.enums.UserStatusEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class UserEntity extends BaseEntity {
    @Column(nullable = false)
    String email;

    @Column(nullable = false)
    String password;

    @Column(nullable = false)
    String name;

    @Column(nullable = false)
    String address;

    @Column(nullable = false)
    String detailAddress;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    UserStatusEnum status;

    public void update(
            String password,
            String name,
            String address,
            String detailAddress,
            UserStatusEnum status
    ) {
        this.password = password;
        this.name = name;
        this.address = address;
        this.detailAddress = detailAddress;
        this.status = status;
    }
}