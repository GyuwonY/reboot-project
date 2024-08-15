package com.example.orderservice.entity;

import com.example.common.entity.BaseEntity;
import com.example.common.entity.enums.order.OrderStatusEnum;
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
