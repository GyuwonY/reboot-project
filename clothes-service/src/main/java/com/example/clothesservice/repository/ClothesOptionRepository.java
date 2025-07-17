package com.example.clothesservice.repository;

import com.example.clothesservice.entity.ClothesOptionEntity;
import com.example.common.entity.enums.clothes.ClothesOptionStatusEnum;
import com.example.common.entity.enums.clothes.ClothesStatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClothesOptionRepository extends JpaRepository<ClothesOptionEntity, String> {
    @Query("""
            select co from ClothesOptionEntity co left join fetch ClothesEntity c
            where co.id in :idList
            """)
    List<ClothesOptionEntity> findAllByIdList(
            List<String> idList
    );

    List<ClothesOptionEntity> findAllByIdInAndStatus(
            List<String> idList,
            ClothesOptionStatusEnum status
    );
}
