package com.example.reboot_project.service;

import com.example.reboot_project.dto.clothes.CartOptionRequestDto;
import com.example.reboot_project.dto.order.OrderClothesOptionResponseDto;
import com.example.reboot_project.dto.order.OrderListResponseDto;
import com.example.reboot_project.dto.order.SaveOrderRequestDto;
import com.example.reboot_project.entity.clothes.ClothesOptionEntity;
import com.example.reboot_project.entity.clothes.enums.ClothesOptionStatusEnum;
import com.example.reboot_project.entity.order.*;
import com.example.reboot_project.entity.order.enums.OrderStatusEnum;
import com.example.reboot_project.entity.order.enums.PaymentStatusEnum;
import com.example.reboot_project.repository.clothes.ClothesOptionRepository;
import com.example.reboot_project.repository.order.*;
import jakarta.transaction.Transactional;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final ClothesOptionRepository clothesOptionRepository;
    private final PaymentRepository paymentRepository;
    private final OrderOptionRepository orderOptionRepository;
    private final OrderHistoryRepository orderHistoryRepository;
    private final PaymentHistoryRepository paymentHistoryRepository;

    @Autowired
    public OrderService(
            OrderRepository orderRepository,
            ClothesOptionRepository clothesOptionRepository,
            PaymentRepository paymentRepository,
            OrderOptionRepository orderOptionRepository,
            OrderHistoryRepository orderHistoryRepository,
            PaymentHistoryRepository paymentHistoryRepository
    ) {
        this.orderRepository = orderRepository;
        this.clothesOptionRepository = clothesOptionRepository;
        this.paymentRepository = paymentRepository;
        this.orderOptionRepository = orderOptionRepository;
        this.orderHistoryRepository = orderHistoryRepository;
        this.paymentHistoryRepository = paymentHistoryRepository;
    }

    @Transactional
    public void saveOrder(
            String userId,
            SaveOrderRequestDto saveOrderRequestDto
    ) {
        Map<String, CartOptionRequestDto> optionRequestMap = saveOrderRequestDto.getCartOptionList()
                .stream()
                .collect(Collectors.toMap(
                        CartOptionRequestDto::getClothesOptionId,
                        optionRequest -> optionRequest
                ));

        List<ClothesOptionEntity> clothesOptionList = clothesOptionRepository.findAllByIdInAndStatus(
                new ArrayList<>(optionRequestMap.keySet()),
                ClothesOptionStatusEnum.ACTIVE
        );

        PaymentEntity payment = PaymentEntity.builder()
                .amount(0)
                .type(saveOrderRequestDto.getPaymentType())
                .status(PaymentStatusEnum.PAID)
                .build();

        OrderEntity order = OrderEntity.builder()
                .amount(payment.getAmount())
                .status(OrderStatusEnum.PAID)
                .payment(payment)
                .userId(userId)
                .build();

        for(ClothesOptionEntity clothesOption : clothesOptionList) {
            int orderCnt = optionRequestMap.get(clothesOption.getId()).getOptionCount();
            clothesOption.minusStock(orderCnt);
            int paymentPrice = clothesOption.getPrice() * orderCnt;
            payment.addAmount(paymentPrice);

            order.getOrderOptionList().add(
                    OrderOptionEntity.builder()
                            .count(orderCnt)
                            .orderId(order.getId())
                            .clothesOption(clothesOption)
                            .build()
            );
        }

        PaymentHistoryEntity paymentHistory = PaymentHistoryEntity.builder()
                .paymentId(payment.getId())
                .type(payment.getType())
                .status(payment.getStatus())
                .amount(payment.getAmount())
                .build();

        OrderHistoryEntity orderHistory = OrderHistoryEntity.builder()
                .paymentId(order.getId())
                .status(order.getStatus())
                .paymentId(payment.getId())
                .userId(userId)
                .build();

        paymentRepository.save(payment);
        orderRepository.save(order);
        orderOptionRepository.saveAll(order.getOrderOptionList());
        paymentHistoryRepository.save(paymentHistory);
        orderHistoryRepository.save(orderHistory);
    }

    public List<OrderListResponseDto> getOrderList(String userId) {
        List<OrderEntity> orderList =  orderRepository.findAllByUserIdAndStatusNot(
                userId,
                OrderStatusEnum.DELETED
        );

        return orderList.stream().map(order -> {
            return OrderListResponseDto.builder()
                    .orderId(order.getId())
                    .paymentType(order.getPayment().getType())
                    .orderStatus(order.getStatus())
                    .amount(order.getAmount())
                    .clothesOptionList(
                            order.getOrderOptionList().stream().map(option -> {
                                return OrderClothesOptionResponseDto.builder()
                                        .clothesId(option.getClothesOption().getClothes().getId())
                                        .clothesTitle(option.getClothesOption().getClothes().getTitle())
                                        .clothesOptionId(option.getClothesOption().getId())
                                        .optionTitle(option.getClothesOption().getTitle())
                                        .count(option.getCount())
                                        .price(option.getClothesOption().getPrice())
                                        .build();
                            }).toList()
                    ).build();
        }).toList();
    }

    @Transactional
    public void cancelOrder(
            String userId,
            String orderId
    ) {
        OrderEntity order = orderRepository.findByIdAndUserIdAndStatusNot(
                userId,
                orderId,
                OrderStatusEnum.DELETED
        ).orElseThrow();

        if(order.getStatus().equals(OrderStatusEnum.PAID)) {
            order.updateStatus(OrderStatusEnum.CANCELED);
            for(OrderOptionEntity option : order.getOrderOptionList()) {
                option.getClothesOption().plusStock(option.getCount());
            }
        } else if(order.getStatus().equals(OrderStatusEnum.COMPLETED_DELIVERY) &&
                order.getUpdatedAt().plusDays(1).isBefore(LocalDateTime.now())) {
            order.updateStatus(OrderStatusEnum.RETURNING);
        } else {
            throw new IllegalArgumentException("코드가 일치하지 않습니다.");
        }
    }
}
