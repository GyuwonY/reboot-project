package com.example.clothesservice.service;

import com.example.clothesservice.dto.CartListResponseDto;
import com.example.clothesservice.dto.CartOptionRequestDto;
import com.example.clothesservice.dto.SaveCartRequestDto;
import com.example.clothesservice.dto.UpdateCartOptionRequestDto;
import com.example.clothesservice.entity.CartEntity;
import com.example.clothesservice.entity.ClothesOptionEntity;
import com.example.clothesservice.repository.CartRepository;
import com.example.clothesservice.repository.ClothesOptionRepository;
import com.example.clothesservice.repository.ClothesRepository;
import com.example.common.entity.enums.clothes.CartStatusEnum;
import com.example.common.entity.enums.clothes.ClothesOptionStatusEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
