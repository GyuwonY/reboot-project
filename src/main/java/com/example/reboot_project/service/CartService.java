package com.example.reboot_project.service;

import com.example.reboot_project.dto.clothes.*;
import com.example.reboot_project.entity.clothes.CartEntity;
import com.example.reboot_project.entity.clothes.ClothesOptionEntity;
import com.example.reboot_project.entity.clothes.enums.ClothesOptionStatusEnum;
import com.example.reboot_project.entity.clothes.enums.CartStatusEnum;
import com.example.reboot_project.repository.clothes.CartRepository;
import com.example.reboot_project.repository.clothes.ClothesOptionRepository;
import com.example.reboot_project.repository.clothes.ClothesRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CartService {
    private final CartRepository cartRepository;
    private final ClothesRepository clothesRepository;
    private final ClothesOptionRepository clothesOptionRepository;

    public CartService(
            CartRepository cartRepository,
            ClothesRepository clothesRepository,
            ClothesOptionRepository clothesOptionRepository
    ) {
        this.cartRepository = cartRepository;
        this.clothesRepository = clothesRepository;
        this.clothesOptionRepository = clothesOptionRepository;
    }

    public void saveCart(
            String userId,
            SaveCartRequestDto cartDto
    ) {
        List<ClothesOptionEntity> optionList = clothesOptionRepository.findAllByIdInAndStatus(
                cartDto.getCartOptionList().stream().map(CartOptionRequestDto::getClothesOptionId).toList(),
                ClothesOptionStatusEnum.ACTIVE
        );

        Map<String, CartOptionRequestDto> optionMap = cartDto.getCartOptionList().stream()
                .collect(
                        Collectors.toMap(
                                CartOptionRequestDto::getClothesOptionId,
                                option -> option
                        )
                );

        cartRepository.saveAll(
                optionList.stream().map(option -> {
                    return CartEntity.builder()
                            .userId(userId)
                            .count(optionMap.get(option.getId()).getOptionCount())
                            .status(CartStatusEnum.ACTIVE)
                            .clothesOption(option)
                            .build();
                }).toList()
        );
    }

    public List<CartListResponseDto> getCartList(
            String userId,
            int page,
            int limit
    ) {
        Page<CartEntity> cartPage = cartRepository.findAllByUserId(
                userId,
                CartStatusEnum.ACTIVE,
                PageRequest.of(page, limit, Sort.by("createdAt").descending())
        );

        return cartPage.get().map(cart -> {
            ClothesOptionEntity option = cart.getClothesOption();
            return CartListResponseDto.builder()
                    .clothesTitle(option.getClothes().getTitle())
                    .optionTitle(option.getTitle())
                    .clothesType(option.getClothes().getType())
                    .optionType(option.getType())
                    .price(option.getPrice())
                    .count(cart.getCount())
                    .build();
        }).toList();
    }

    @Transactional
    public void updateCartOption(
            String userId,
            UpdateCartOptionRequestDto updateCartOptionRequestDto
    ) {
        CartEntity cart = cartRepository.findByIdAndUserIdAndStatus(
                updateCartOptionRequestDto.getCartId(),
                userId,
                CartStatusEnum.ACTIVE
        ).orElseThrow();

        cart.updateOption(updateCartOptionRequestDto.getStatus(), updateCartOptionRequestDto.getCount());
    }
}
