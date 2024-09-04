package com.example.orderservice.controller;

import com.example.orderservice.dto.OrderListResponseDto;
import com.example.orderservice.dto.PayOrderRequestDto;
import com.example.orderservice.dto.SaveOrderRequestDto;
import com.example.orderservice.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {
    private final OrderService orderService;

    @Autowired
    public OrderController(
            OrderService orderService
    ) {
        this.orderService = orderService;
    }

    @PostMapping()
    public void saveOrder(
            @RequestBody SaveOrderRequestDto saveOrderRequestDto
    ) {
        orderService.saveOrder(saveOrderRequestDto);
    }

    @PostMapping("/{orderId}")
    public void payOrder(
            @RequestBody PayOrderRequestDto payOrderRequestDto
    ) {
        orderService.payOrder(payOrderRequestDto);
    }

    @GetMapping("/list")
    public List<OrderListResponseDto> getOrderList(
            @RequestParam("userId") String userId
    ) {
        return orderService.getOrderList(userId);
    }

    @PutMapping("/{orderId}")
    public void cancelOrder(
            @RequestParam("userId") String userId,
            @PathVariable("orderId") String orderId
    ) {
        orderService.cancelOrder(userId, orderId);
    }

}
