package com.example.orderservice.repository;

import com.example.orderservice.entity.OrderEntity;
import com.example.common.entity.enums.order.OrderStatusEnum;
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
            where o.userId = :userId and o.status != :status
            """)
    List<OrderEntity> findAllByUserIdAndStatusNot(String userId, OrderStatusEnum status);

    Optional<OrderEntity> findByIdAndUserIdAndStatusNot(String userId, String orderId, OrderStatusEnum status);

    @Query("""
            select o from OrderEntity o
            left join fetch o.orderOptionList oo
            where o.status = :status and o.updatedAt >= :yesterday and o.updatedAt < :today
            """)
    List<OrderEntity> findAllByStatusAndUpdatedAt(OrderStatusEnum status, LocalDate today, LocalDate yesterday);

    @Query("""
            select o from OrderEntity o 
            left join fetch o.orderOptionList oo
            where o.id = :id and o.status != :status
            """)
    Optional<OrderEntity> findByIdAndStatusNot(String id, OrderStatusEnum status);
}
