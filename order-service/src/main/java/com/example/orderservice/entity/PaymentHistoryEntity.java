package com.example.orderservice.entity;

import com.example.common.entity.BaseEntity;
import com.example.common.entity.enums.order.PaymentStatusEnum;
import com.example.common.entity.enums.order.PaymentTypeEnum;
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
public class PaymentHistoryEntity extends BaseEntity {
    @Column(nullable = false)
    String paymentId;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    PaymentTypeEnum type;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    PaymentStatusEnum status;

    @Column(nullable = false)
    int amount;
}
