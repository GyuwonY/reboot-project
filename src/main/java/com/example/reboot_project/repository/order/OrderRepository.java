package com.example.reboot_project.repository.order;

import com.example.reboot_project.entity.order.OrderEntity;
import com.example.reboot_project.entity.order.PaymentEntity;
import com.example.reboot_project.entity.order.enums.OrderStatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, String> {
    @Query("""
            select o from OrderEntity o 
            left join fetch o.payment p 
            left join fetch o.orderOptionList oo 
            left join fetch oo.clothesOption co 
            left join fetch co.clothes c 
            where o.userId = :userId and o.status != :status
            """)
    List<OrderEntity> findAllByUserIdAndStatusNot(String userId, OrderStatusEnum status);

    Optional<OrderEntity> findByIdAndUserIdAndStatusNot(String userId, String orderId, OrderStatusEnum status);

    @Query("""
            select o from OrderEntity o
            left join fetch o.orderOptionList oo 
            left join fetch oo.clothesOption co 
            where o.status = :status and o.updatedAt >= :yesterday and o.updatedAt < :today 
            """)
    List<OrderEntity> findAllByStatusAndUpdatedAt(OrderStatusEnum status, LocalDate day, LocalDate yesterday);
}
