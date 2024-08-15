package com.example.orderservice.scheduler;

import com.example.common.entity.enums.order.OrderStatusEnum;
import com.example.orderservice.client.ClothesClient;
import com.example.orderservice.dto.UpdateStockRequestDto;
import com.example.orderservice.entity.OrderEntity;
import com.example.orderservice.repository.OrderRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderStatusScheduler {
    private final OrderRepository orderRepository;
    private final ClothesClient clothesClient;

    public OrderStatusScheduler(
            OrderRepository orderRepository,
            ClothesClient clothesClient
    ) {
        this.orderRepository = orderRepository;
        this.clothesClient = clothesClient;
    }

    @Scheduled(cron = "0 0 0 * * *")
    @Transactional
    public void changeOrderStatusToDelivery(){
        LocalDate yesterday = LocalDate.now().minusDays(1);
        List<OrderEntity> orderList = orderRepository.findAllByStatusAndUpdatedAt(
                OrderStatusEnum.PAID,
                LocalDate.now(), yesterday
        );

        for(OrderEntity order : orderList) {
            order.updateStatus(OrderStatusEnum.IN_PROGRESS_DELIVERY);
        }
    }

    @Scheduled(cron = "0 0 0 * * *")
    @Transactional
    public void changeOrderStatusToCompleteDelivery(){
        LocalDate yesterday = LocalDate.now().minusDays(1);
        List<OrderEntity> orderList = orderRepository.findAllByStatusAndUpdatedAt(
                OrderStatusEnum.IN_PROGRESS_DELIVERY,
                LocalDate.now(), yesterday
        );

        for(OrderEntity order : orderList) {
            order.updateStatus(OrderStatusEnum.COMPLETED_DELIVERY);
        }
    }

    @Scheduled(cron = "0 0 0 * * *")
    @Transactional
    public void changeOrderStatusToCompleteReturn(){
        LocalDate yesterday = LocalDate.now().minusDays(1);
        List<OrderEntity> orderList = orderRepository.findAllByStatusAndUpdatedAt(
                OrderStatusEnum.RETURNING,
                LocalDate.now(), yesterday
        );

        for(OrderEntity order : orderList) {
            order.updateStatus(OrderStatusEnum.COMPLETED_RETURN);
        }

        List<UpdateStockRequestDto> updateStockRequestDtoList = new ArrayList<>();
        for(OrderEntity order : orderList) {
            updateStockRequestDtoList.addAll(
                    order.getOrderOptionList().stream().map(option -> {
                        return UpdateStockRequestDto.builder()
                                .id(option.getClothesOptionId())
                                .count(option.getCount())
                                .build();
                    }).toList()
            );
        }

        clothesClient.updateStock(updateStockRequestDtoList);
    }
}
