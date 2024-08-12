package com.example.reboot_project.entity.order;

import com.example.reboot_project.entity.BaseEntity;
import com.example.reboot_project.entity.order.enums.PaymentStatusEnum;
import com.example.reboot_project.entity.order.enums.PaymentTypeEnum;
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
public class PaymentEntity extends BaseEntity {
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    PaymentTypeEnum type;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    PaymentStatusEnum status;

    @Column(nullable = false)
    int amount;

    public void addAmount(int amount) {
        this.amount += amount;
    }
}
