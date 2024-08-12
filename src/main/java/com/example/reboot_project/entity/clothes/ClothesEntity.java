package com.example.reboot_project.entity.clothes;

import com.example.reboot_project.entity.BaseEntity;
import com.example.reboot_project.entity.clothes.enums.ClothesStatusEnum;
import com.example.reboot_project.entity.clothes.enums.ClothesTypeEnum;
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
