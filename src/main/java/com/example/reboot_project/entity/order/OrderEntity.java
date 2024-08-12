package com.example.reboot_project.entity.order;

import com.example.reboot_project.entity.BaseEntity;
import com.example.reboot_project.entity.order.enums.OrderStatusEnum;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class OrderEntity extends BaseEntity {
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    OrderStatusEnum status;

    @Column(nullable = false)
    String userId;

    @Column(nullable = false)
    int amount;

    @OneToOne
    @JoinColumn(name = "paymentId")
    PaymentEntity payment;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "orderId")
    List<OrderOptionEntity> orderOptionList;

    public void updateStatus(OrderStatusEnum status) {
        this.status = status;
    }
}
