package com.example.reboot_project.entity;

import com.example.reboot_project.entity.enums.DeviceTypeEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class DeviceEntity extends BaseEntity {
    @Column(nullable = false)
    String userId;

    @Column(nullable = false)
    String AccessToken;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    DeviceTypeEnum type;
}
