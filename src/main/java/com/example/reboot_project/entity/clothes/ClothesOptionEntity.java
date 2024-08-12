package com.example.reboot_project.entity.clothes;

import com.example.reboot_project.entity.BaseEntity;
import com.example.reboot_project.entity.clothes.enums.ClothesOptionStatusEnum;
import com.example.reboot_project.entity.clothes.enums.ClothesOptionTypeEnum;
import jakarta.persistence.*;
import lombok.*;

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
