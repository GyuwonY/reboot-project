package com.example.reboot_project.repository.clothes;

import com.example.reboot_project.entity.clothes.ClothesEntity;
import com.example.reboot_project.entity.clothes.enums.ClothesOptionStatusEnum;
import com.example.reboot_project.entity.clothes.enums.ClothesStatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClothesRepository extends JpaRepository<ClothesEntity, String> {
    List<ClothesEntity> findAllByStatus(ClothesStatusEnum status);

    @Query("""
            select c from ClothesEntity c left join fetch ProductOptionEntity po
            where c.id = :id and c.status = :status and po.status = :optionStatus
            """)
    Optional<ClothesEntity> findByIdAndStatus(
            String id,
            ClothesStatusEnum status,
            ClothesOptionStatusEnum optionStatus
    );
}
