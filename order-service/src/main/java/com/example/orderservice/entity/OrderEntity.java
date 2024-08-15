package com.example.orderservice.entity;

import com.example.common.entity.BaseEntity;
import com.example.common.entity.enums.order.OrderStatusEnum;
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
