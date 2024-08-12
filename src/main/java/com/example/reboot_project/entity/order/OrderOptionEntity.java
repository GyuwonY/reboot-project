package com.example.reboot_project.entity.order;

import com.example.reboot_project.entity.BaseEntity;
import com.example.reboot_project.entity.clothes.ClothesOptionEntity;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "clothesOptionId", nullable = false)
    ClothesOptionEntity clothesOption;
}
