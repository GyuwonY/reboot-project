package com.example.userservice.entity;

import com.example.common.entity.BaseEntity;
import com.example.common.entity.enums.user.DeviceTypeEnum;
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
    String accessToken;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    DeviceTypeEnum type;
}
