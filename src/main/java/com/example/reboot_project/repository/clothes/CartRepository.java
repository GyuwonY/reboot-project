package com.example.reboot_project.repository.clothes;

import com.example.reboot_project.entity.clothes.CartEntity;
import com.example.reboot_project.entity.clothes.enums.CartStatusEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<CartEntity, String> {

    @Query("""
            select c from CartEntity c left join fetch c.clothesOption co left join fetch co.clothes cl
            where c.userId = :userId and c.status = :status
            """)
    Page<CartEntity> findAllByUserId(
            String userId,
            CartStatusEnum status,
            PageRequest pageRequest
    );


    @Query("""
            select c from CartEntity c where c.id = :id and c.userId = :userId and c.status = :status
            """)
    Optional<CartEntity> findByIdAndUserIdAndStatus(String id, String userId, CartStatusEnum status);
}
