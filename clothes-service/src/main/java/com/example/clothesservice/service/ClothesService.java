package com.example.clothesservice.service;

import com.example.clothesservice.dto.*;
import com.example.clothesservice.entity.ClothesEntity;
import com.example.clothesservice.entity.ClothesOptionEntity;
import com.example.common.entity.enums.clothes.ClothesOptionStatusEnum;
import com.example.common.entity.enums.clothes.ClothesStatusEnum;
import com.example.clothesservice.repository.ClothesOptionRepository;
import com.example.clothesservice.repository.ClothesRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ClothesService {
    private final ClothesRepository clothesRepository;
    private final ClothesOptionRepository clothesOptionRepository;
    private RedisTemplate<String, Object> redisTemplate;

    public ClothesService(
            ClothesRepository clothesRepository,
            ClothesOptionRepository clothesOptionRepository,
            RedisTemplate<String, Object> redisTemplate
    ) {
        this.clothesRepository = clothesRepository;
        this.clothesOptionRepository = clothesOptionRepository;
        this.redisTemplate = redisTemplate;
    }

    public List<ClothesListResponseDto> getClothesList() {
        List<ClothesEntity> clothesList = clothesRepository.findAllByStatusAndStartAtLessThanEqual(
                ClothesStatusEnum.ACTIVE,
                LocalDateTime.now()
        );

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

    public List<ClothesOptionListByIdsResponseDto> getClothesListByIds(
            List<String> idList
    ) {
        List<ClothesOptionEntity> clothesOptionList = clothesOptionRepository.findAllByIdList(
                idList
        );

        return clothesOptionList.stream().map(option -> {
            return ClothesOptionListByIdsResponseDto.builder()
                    .clothesOptionId(option.getId())
                    .optionTitle(option.getTitle())
                    .clothesId(option.getClothes().getId())
                    .clothesTitle(option.getClothes().getTitle())
                    .type(option.getType())
                    .price(option.getPrice())
                    .stock(option.getStock())
                    .build();
        }).toList();
    }

    @Transactional
    public void updateStock(List<UpdateStockRequestDto> optionList) {
        Map<String, ClothesOptionEntity> clothesOptionMap = clothesOptionRepository.findAllByIdInAndStatus(
                optionList.stream().map(UpdateStockRequestDto::getId).toList(),
                ClothesOptionStatusEnum.ACTIVE
        ).stream().collect(Collectors.toMap(
                ClothesOptionEntity::getId,
                option -> option
        ));

        for (UpdateStockRequestDto optionDto : optionList) {
            ClothesOptionEntity optionEntity = clothesOptionMap.get(optionDto.getId());
            if (optionDto.getCount() < 0) {
                if (-optionDto.getCount() <= optionEntity.getStock()){
                    optionEntity.plusStock(optionDto.getCount());
                } else {
                    throw new RuntimeException();
                }
            } else {
                optionEntity.plusStock(optionDto.getCount());
            }

        }
    }

    public GetStockByOptionIdResDto getStockByOptionId(String id) {
        ClothesOptionEntity option = clothesOptionRepository.findById(id).orElseThrow();

        return GetStockByOptionIdResDto.builder()
                .stock(option.getStock())
                .id(option.getId())
                .build();
    }
}
