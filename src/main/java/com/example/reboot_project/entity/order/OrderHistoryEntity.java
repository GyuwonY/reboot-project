package com.example.reboot_project.entity.order;

import com.example.reboot_project.entity.BaseEntity;
import com.example.reboot_project.entity.order.enums.OrderStatusEnum;
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
public class OrderHistoryEntity extends BaseEntity {
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    OrderStatusEnum status;

    @Column(nullable = false)
    String orderId;

    @Column(nullable = false)
    String paymentId;

    @Column(nullable = false)
    String userId;
}
