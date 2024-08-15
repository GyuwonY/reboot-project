package com.example.orderservice.service;

import com.example.common.entity.enums.order.OrderStatusEnum;
import com.example.common.entity.enums.order.PaymentStatusEnum;
import com.example.orderservice.client.ClothesClient;
import com.example.orderservice.dto.*;
import com.example.orderservice.entity.*;
import com.example.orderservice.repository.*;
import jakarta.transaction.Transactional;
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
    private final PaymentRepository paymentRepository;
    private final OrderOptionRepository orderOptionRepository;
    private final OrderHistoryRepository orderHistoryRepository;
    private final PaymentHistoryRepository paymentHistoryRepository;
    private final ClothesClient clothesClient;

    @Autowired
    public OrderService(
            OrderRepository orderRepository,
            PaymentRepository paymentRepository,
            OrderOptionRepository orderOptionRepository,
            OrderHistoryRepository orderHistoryRepository,
            PaymentHistoryRepository paymentHistoryRepository,
            ClothesClient clothesClient
    ) {
        this.orderRepository = orderRepository;
        this.paymentRepository = paymentRepository;
        this.orderOptionRepository = orderOptionRepository;
        this.orderHistoryRepository = orderHistoryRepository;
        this.paymentHistoryRepository = paymentHistoryRepository;
        this.clothesClient = clothesClient;
    }

    @Transactional
    public void saveOrder(
            SaveOrderRequestDto saveOrderRequestDto
    ) {
        Map<String, OrderOptionRequestDto> optionRequestMap = saveOrderRequestDto.getOrderOptionList()
                .stream()
                .collect(Collectors.toMap(
                        OrderOptionRequestDto::getClothesOptionId,
                        optionRequest -> optionRequest
                ));


        List<ClothesOptionListByIdsResponseDto> clothesOptionList = clothesClient.getClothesOptionListByIds(
                new ArrayList<>(optionRequestMap.keySet())
        );

        clothesClient.updateStock(
                saveOrderRequestDto.getOrderOptionList()
                .stream().map(order -> {
                    return UpdateStockRequestDto.builder()
                            .id(order.getClothesOptionId())
                            .count(-order.getOptionCount())
                            .build();
                }).toList()
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
                .userId(saveOrderRequestDto.getUserId())
                .build();

        for(ClothesOptionListByIdsResponseDto clothesOption : clothesOptionList) {
            int orderCnt = optionRequestMap.get(clothesOption.getClothesOptionId()).getOptionCount();
            int paymentPrice = clothesOption.getPrice() * orderCnt;
            payment.addAmount(paymentPrice);

            order.getOrderOptionList().add(
                    OrderOptionEntity.builder()
                            .count(orderCnt)
                            .orderId(order.getId())
                            .clothesOptionId(clothesOption.getClothesOptionId())
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
                .userId(saveOrderRequestDto.getUserId())
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

        List<String> optionIdList = new ArrayList<>();
        for(OrderEntity order : orderList) {
            optionIdList.addAll(order.getOrderOptionList().stream().map(OrderOptionEntity::getId).toList());
        }

        Map<String, ClothesOptionListByIdsResponseDto> clothesOptionMap = clothesClient.getClothesOptionListByIds(
                optionIdList
        ).stream().collect(Collectors.toMap(
                ClothesOptionListByIdsResponseDto::getClothesOptionId,
                option -> option
        ));

        return orderList.stream().map(order -> {
            return OrderListResponseDto.builder()
                    .orderId(order.getId())
                    .paymentType(order.getPayment().getType())
                    .orderStatus(order.getStatus())
                    .amount(order.getAmount())
                    .clothesOptionList(
                            order.getOrderOptionList().stream().map(option -> {
                                ClothesOptionListByIdsResponseDto clothesOption =
                                        clothesOptionMap.get(option.getClothesOptionId());

                                return OrderClothesOptionResponseDto.builder()
                                        .clothesId(clothesOption.getClothesId())
                                        .clothesTitle(clothesOption.getClothesTitle())
                                        .clothesOptionId(clothesOption.getClothesOptionId())
                                        .optionTitle(clothesOption.getOptionTitle())
                                        .count(option.getCount())
                                        .price(clothesOption.getPrice())
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
            List<UpdateStockRequestDto> updateStockRequestDtoList = order.getOrderOptionList()
                    .stream().map(option -> {
                        return UpdateStockRequestDto.builder()
                                .id(option.getClothesOptionId())
                                .count(option.getCount())
                                .build();
                    })
                    .toList();

            clothesClient.updateStock(updateStockRequestDtoList);
        } else if(order.getStatus().equals(OrderStatusEnum.COMPLETED_DELIVERY) &&
                order.getUpdatedAt().plusDays(1).isBefore(LocalDateTime.now())) {
            order.updateStatus(OrderStatusEnum.RETURNING);
        } else {
            throw new IllegalArgumentException("취소 불가");
        }
    }
}
