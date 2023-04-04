package com.example.comparePrice.service.impl;

import com.example.comparePrice.service.LowestPriceService;
import com.example.comparePrice.vo.Keyword;
import com.example.comparePrice.vo.Product;
import com.example.comparePrice.vo.ProductGroup;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

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

    @Override
    public Long addNewProduct(Product product) {
        String key = product.getProductGroupId();
        String value = product.getProductId();
        int score = product.getPrice();

        ZSetOperations operations = productPriceRedisTemplate.opsForZSet();
        operations.add(key, value, score);

        return operations.rank(key, value);
    }

    @Override
    public Long addNewProductGroup(ProductGroup productGroup) {
        ZSetOperations operations = productPriceRedisTemplate.opsForZSet();

        String productGroupId = productGroup.getProductGroupId();
        productGroup.getProductList().stream().forEach(p -> operations.add(productGroupId, p.getProductId(), p.getPrice()));

        return operations.zCard(productGroupId);
    }

    @Override
    public Long bindProductGroupIdToKeyword(String productGroupId, Double score, String keyword) {
        ZSetOperations operations = productPriceRedisTemplate.opsForZSet();

        operations.add(keyword, productGroupId, score);

        return operations.zCard(keyword);
    }

    @Override
    public Keyword getLowestPriceProductByKeyword(String keyword) {
        ZSetOperations operations = productPriceRedisTemplate.opsForZSet();
        ObjectMapper objectMapper = new ObjectMapper();

        /*
        Keyword로 ProductGroupId 10개 가져오기
        튜플: (Keyword, Product Group Id, Score) -> 이때 score 는 keyword 랑 ProductGroupId 의 관련도 점수 -> ZSet은 score 오름차순으로 정렬되기 때문에, reverseRage로 해서 내림차순으로 10개 가져오기
         */
        List<String> productGroupIds = operations.reverseRange(keyword, 0, 9).stream().toList();

        List<ProductGroup> productGroups = new ArrayList<>();
        for (String productGroupId : productGroupIds) { // 각 productGroupId 확인해서, 각 productGroupId에 속한 제품들 가져오기
            /*
            튜플: (Product Group Id, Product Id, Price) 10개 가져오기
            -> 여기서 Score는 Price 이며, ZSet은 오름차순이므로, 가격이 낮은 순서대로 가져옴
             */
            Set set = operations.rangeWithScores(productGroupId, 0, 9);
            Iterator iterator = set.iterator();
            List<Product> productList = new ArrayList<>();
            while (iterator.hasNext()) { // 각 튜플 확인하기
                Object next = iterator.next();
                Map<String, Object> mapped = objectMapper.convertValue(next, Map.class); // 각 투플을 변환 -> 변환후 {"key" : String, "value" : String, "score" : Double}
                productList.add(Product.of(productGroupId, mapped.get("value").toString(), Double.valueOf(mapped.get("score").toString()).intValue())); // 각 튜플을 리스트에 추가하기
            }

            productGroups.add(ProductGroup.of(productGroupId, productList));
        }


        return Keyword.of(keyword, productGroups);

    }


}
