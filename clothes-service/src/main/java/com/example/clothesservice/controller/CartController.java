package com.example.clothesservice.controller;

import com.example.clothesservice.dto.CartListResponseDto;
import com.example.clothesservice.dto.SaveCartRequestDto;
import com.example.clothesservice.dto.UpdateCartOptionRequestDto;
import com.example.clothesservice.service.CartService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController {
    private final CartService cartService;

    public CartController(
            CartService cartService
    ) {
        this.cartService = cartService;
    }

    @PostMapping()
    public void saveCart(
            @RequestHeader("X-User-Id") String userId,
            @RequestBody SaveCartRequestDto cartDto
    ) {
        cartService.saveCart(userId, cartDto);
    }

    @GetMapping("/list")
    public List<CartListResponseDto> getCartList(
            @RequestHeader("X-User-Id") String userId,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "limit", defaultValue = "10") int limit
    ) {
        return cartService.getCartList(userId, page, limit);
    }

    @PutMapping()
    public void updateCartOption(
            @RequestHeader("X-User-Id") String userId,
            @RequestBody UpdateCartOptionRequestDto updateCartOptionRequestDto
    ) {
        cartService.updateCartOption(userId, updateCartOptionRequestDto);
    }
}
