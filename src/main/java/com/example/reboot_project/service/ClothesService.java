package com.example.reboot_project.service;

import com.example.reboot_project.dto.clothes.ClothesListResponseDto;
import com.example.reboot_project.dto.clothes.ClothesOptionResponseDto;
import com.example.reboot_project.dto.clothes.ClothesResponseDto;
import com.example.reboot_project.entity.clothes.ClothesEntity;
import com.example.reboot_project.entity.clothes.enums.ClothesOptionStatusEnum;
import com.example.reboot_project.entity.clothes.enums.ClothesStatusEnum;
import com.example.reboot_project.repository.clothes.ClothesOptionRepository;
import com.example.reboot_project.repository.clothes.ClothesRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClothesService {
    private final ClothesRepository clothesRepository;
    private final ClothesOptionRepository clothesOptionRepository;

    public ClothesService(
            ClothesRepository clothesRepository,
            ClothesOptionRepository clothesOptionRepository
    ) {
        this.clothesRepository = clothesRepository;
        this.clothesOptionRepository = clothesOptionRepository;
    }


    public List<ClothesListResponseDto> getClothesList() {
        List<ClothesEntity> clothesList = clothesRepository.findAllByStatus(ClothesStatusEnum.ACTIVE);

        return clothesList.stream().map(clothes -> {
            return ClothesListResponseDto.builder()
                    .title(clothes.getTitle())
                    .type(clothes.getType())
                    .price(clothes.getPrice())
                    .build();
        }).collect(Collectors.toList());
    }

    public ClothesResponseDto getClothesById(
            String clothesId
    ) {
        ClothesEntity clothes = clothesRepository.findByIdAndStatus(
                clothesId,
                ClothesStatusEnum.ACTIVE,
                ClothesOptionStatusEnum.ACTIVE
        ).orElseThrow();

        return ClothesResponseDto.builder()
                .title(clothes.getTitle())
                .type(clothes.getType())
                .price(clothes.getPrice())
                .clothesOptionList(
                        clothes.getClothesOptionList().stream().map(clothesOption -> {
                            return ClothesOptionResponseDto.builder()
                                    .title(clothesOption.getTitle())
                                    .type(clothesOption.getType())
                                    .price(clothesOption.getPrice())
                                    .stock(clothesOption.getStock())
                                    .build();
                        }).toList()
                ).build();
    }
}
