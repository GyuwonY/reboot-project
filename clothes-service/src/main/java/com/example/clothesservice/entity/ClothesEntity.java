package com.example.clothesservice.entity;

import com.example.common.entity.enums.clothes.ClothesStatusEnum;
import com.example.common.entity.enums.clothes.ClothesTypeEnum;
import com.example.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class ClothesEntity extends BaseEntity {
    @Column(nullable = false)
    String title;

    @Column(nullable = false)
    int price;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    ClothesStatusEnum status;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    ClothesTypeEnum type;

    @OneToMany(
            fetch = FetchType.LAZY,
            mappedBy = "clothes"
    )
    List<ClothesOptionEntity> clothesOptionList;
}
