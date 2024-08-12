package com.example.reboot_project.controller;

import com.example.reboot_project.dto.clothes.SaveCartRequestDto;
import com.example.reboot_project.dto.clothes.CartListResponseDto;
import com.example.reboot_project.dto.clothes.UpdateCartOptionRequestDto;
import com.example.reboot_project.security.CustomUserDetails;
import com.example.reboot_project.service.CartService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestBody SaveCartRequestDto cartDto
    ) {
        cartService.saveCart(userDetails.getUser().getId(), cartDto);
    }

    @GetMapping("/list")
    public List<CartListResponseDto> getCartList(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "limit", defaultValue = "10") int limit
    ) {
        return cartService.getCartList(userDetails.getUser().getId(), page, limit);
    }

    @PutMapping()
    public void updateCartOption(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestBody UpdateCartOptionRequestDto updateCartOptionRequestDto
    ) {
        cartService.updateCartOption(userDetails.getUser().getId(), updateCartOptionRequestDto);
    }
}
