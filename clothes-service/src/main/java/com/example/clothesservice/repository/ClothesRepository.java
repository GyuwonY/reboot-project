package com.example.clothesservice.repository;

import com.example.clothesservice.entity.ClothesEntity;
import com.example.common.entity.enums.clothes.ClothesOptionStatusEnum;
import com.example.common.entity.enums.clothes.ClothesStatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ClothesRepository extends JpaRepository<ClothesEntity, String> {
    List<ClothesEntity> findAllByStatusAndStartAtLessThanEqual(
            ClothesStatusEnum status,
            LocalDateTime now
    );

    @Query("""
            select c from ClothesEntity c left join fetch ClothesOptionEntity co
            where c.id = :id and c.status = :status and co.status = :optionStatus
            """)
    Optional<ClothesEntity> findByIdAndStatus(
            String id,
            ClothesStatusEnum status,
            ClothesOptionStatusEnum optionStatus
    );
}
