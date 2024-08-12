package com.example.reboot_project.schedulers;

import com.example.reboot_project.entity.order.OrderEntity;
import com.example.reboot_project.entity.order.OrderOptionEntity;
import com.example.reboot_project.entity.order.enums.OrderStatusEnum;
import com.example.reboot_project.repository.order.OrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class OrderStatusScheduler {
    private final OrderRepository orderRepository;

    public OrderStatusScheduler(
            OrderRepository orderRepository
    ) {
        this.orderRepository = orderRepository;
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
            List<OrderOptionEntity> optionList = order.getOrderOptionList();

            for(OrderOptionEntity option : optionList) {
                option.getClothesOption().plusStock(option.getCount());
            }
        }
    }
}
