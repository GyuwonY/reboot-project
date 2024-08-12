package com.example.reboot_project.entity.clothes;

import com.example.reboot_project.entity.BaseEntity;
import com.example.reboot_project.entity.clothes.enums.CartStatusEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class CartEntity extends BaseEntity {
    @Column(nullable = false)
    String userId;

    @Column(nullable = false)
    CartStatusEnum status;

    @Column(nullable = false)
    int count;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "clothesOptionId", nullable = false)
    ClothesOptionEntity clothesOption;

    public void updateStatus(CartStatusEnum status) {
        this.status = status;
    }

    public void updateOption(
            CartStatusEnum status,
            int count
    ) {
        this.status = status;
        this.count = count;
    }
}
