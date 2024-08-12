package com.example.reboot_project.repository.clothes;

import com.example.reboot_project.entity.clothes.ClothesOptionEntity;
import com.example.reboot_project.entity.clothes.enums.ClothesOptionStatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClothesOptionRepository extends JpaRepository<ClothesOptionEntity, String> {
    List<ClothesOptionEntity> findAllByProductIdAndStatus(String productId, ClothesOptionStatusEnum status);
    List<ClothesOptionEntity> findAllByIdInAndStatus(List<String> idList, ClothesOptionStatusEnum status);
}
