package com.example.stockservice.service;

import com.example.stockservice.client.ClothesClient;
import com.example.stockservice.dto.GetStockByOptionIdResDto;
import com.example.stockservice.dto.GetStockResponseDto;
import com.example.stockservice.dto.SaveWaitingPayReqDto;
import com.example.stockservice.dto.UpdateStockRequestDto;
import org.redisson.api.RBucket;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;

@Service
public class StockService {
    private final ClothesClient clothesClient;
    private final RedissonClient redissonClient;
    public static final String STOCK = "STOCK:";
    public static final String ORDER = "ORDER:";

    public StockService(
            RedissonClient redissonClient,
            ClothesClient clothesClient
    ) {
        this.redissonClient = redissonClient;
        this.clothesClient = clothesClient;
    }

    public GetStockResponseDto getStockById(String clothesOptionId) {
        RBucket<Integer> bucket = redissonClient.getBucket(STOCK + clothesOptionId);

        if (bucket.get() == null) {
            GetStockByOptionIdResDto res = clothesClient.getStockByOptionId(clothesOptionId);
            bucket.set(res.getStock());

            return GetStockResponseDto.builder()
                    .stock(res.getStock())
                    .build();
        }

        return GetStockResponseDto.builder()
                .stock(bucket.get())
                .build();
    }

    public void updateStock(
            List<UpdateStockRequestDto> updateStockRequestDtoList
    ) {
        List<RLock> locks = updateStockRequestDtoList
                .stream()
                .map(dto -> {
                    return redissonClient.getLock(STOCK + dto.getId());
                }).toList();

        try {
            for (RLock lock : locks) {
                lock.lock();
            }

            for (UpdateStockRequestDto dto : updateStockRequestDtoList) {
                RBucket<Integer> bucket = redissonClient.getBucket(STOCK + dto.getId());
                if (bucket.get() == null || bucket.get() >= dto.getCount()) {
                    throw new RuntimeException();
                }
            }

            for (UpdateStockRequestDto dto : updateStockRequestDtoList) {
                RBucket<Integer> bucket = redissonClient.getBucket(STOCK + dto.getId());
                int stock = bucket.get();
                bucket.set(stock - dto.getCount());
            }
        } finally {
            for (RLock lock : locks) {
                lock.unlock();
            }
        }
    }

    public void saveWaitingPay(SaveWaitingPayReqDto saveWaitingPayReqDto) {

        for(UpdateStockRequestDto count : saveWaitingPayReqDto.getUpdateStockRequestDtoList()){
            RBucket<Integer> bucket = redissonClient.getBucket(
                    ORDER + saveWaitingPayReqDto.getOrderId() + ":" + count.getId() + ":" + count.getCount()
            );

            bucket.set(count.getCount(), Duration.ofMinutes(10));
        }

        updateStock(saveWaitingPayReqDto.getUpdateStockRequestDtoList());
    }

    public void deleteWaitingPay(SaveWaitingPayReqDto saveWaitingPayReqDto) {
        for(UpdateStockRequestDto count : saveWaitingPayReqDto.getUpdateStockRequestDtoList()){
            RBucket<Integer> bucket = redissonClient.getBucket(
                    ORDER + saveWaitingPayReqDto.getOrderId() + ":" + count.getId()
            );

            bucket.delete();
        }
    }
}
