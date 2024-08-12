package com.example.reboot_project.controller;

import com.example.reboot_project.dto.order.OrderListResponseDto;
import com.example.reboot_project.dto.order.SaveOrderRequestDto;
import com.example.reboot_project.security.CustomUserDetails;
import com.example.reboot_project.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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

    @PostMapping
    public void saveOrder(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestBody SaveOrderRequestDto saveOrderRequestDto
    ) {
        orderService.saveOrder(userDetails.getUser().getId(), saveOrderRequestDto);
    }

    @GetMapping("/list")
    public List<OrderListResponseDto> getOrderList(
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        return orderService.getOrderList(userDetails.getUser().getId());
    }

    @PutMapping("/{orderId}")
    public void cancelOrder(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable("orderId") String orderId
    ) {
        orderService.cancelOrder(userDetails.getUser().getId(), orderId);
    }

}
