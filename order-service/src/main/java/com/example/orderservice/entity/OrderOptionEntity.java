package com.example.orderservice.entity;

import com.example.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class OrderOptionEntity extends BaseEntity {
    @Column(nullable = false)
    int count;

    @Column(nullable = false)
    String orderId;

    String clothesOptionId;
}
