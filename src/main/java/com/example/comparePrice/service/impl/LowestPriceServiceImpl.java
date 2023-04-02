package com.example.comparePrice.service.impl;

import com.example.comparePrice.service.LowestPriceService;
import com.example.comparePrice.vo.Product;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class LowestPriceServiceImpl implements LowestPriceService {

    private final RedisTemplate productPriceRedisTemplate;

    @Override
    public Set getZSetValue(String key) {
        ZSetOperations operations = productPriceRedisTemplate.opsForZSet();
        Set set = operations.rangeWithScores(key, 0, 9); // ZRange
        Long size = operations.size(key);   // ZCard

        log.info("Size : {}, Value: {}", size, set.toString());

        return set;
    }

}
