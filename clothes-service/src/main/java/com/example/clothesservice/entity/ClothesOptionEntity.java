package com.example.clothesservice.entity;

import com.example.common.entity.enums.clothes.ClothesOptionStatusEnum;
import com.example.common.entity.enums.clothes.ClothesOptionTypeEnum;
import com.example.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class ClothesOptionEntity extends BaseEntity {
    @Column(nullable = false)
    String title;

    @Column(nullable = false)
    int price;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    ClothesOptionStatusEnum status;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    ClothesOptionTypeEnum type;

    @Column(nullable = false)
    int stock;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="clothesId")
    ClothesEntity clothes;

    public void minusStock(int count) {
        stock -= count;
    }

    public void plusStock(int count) {
        stock += count;
    }
}
