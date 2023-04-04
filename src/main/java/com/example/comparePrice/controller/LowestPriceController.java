package com.example.comparePrice.controller;

import com.example.comparePrice.service.LowestPriceService;
import com.example.comparePrice.vo.Keyword;
import com.example.comparePrice.vo.Product;
import com.example.comparePrice.vo.ProductGroup;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@Slf4j
@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class LowestPriceController {

    private final LowestPriceService lowestPriceService;

    @GetMapping("/getZSetValue")
    public Set getZSetValue(String key) {
        return lowestPriceService.getZSetValue(key);
    }

    @PostMapping("/add/product") // 새로운 제품 추가하기
    public Long addNewProduct(@RequestBody Product product) {
        return lowestPriceService.addNewProduct(product);
    }

    @PostMapping("/add/productGroup") // 해당 ProductGroup에 새로운 제품들 추가하기
    public Long addNewProductGroup(@RequestBody ProductGroup productGroup) {
        return lowestPriceService.addNewProductGroup(productGroup);
    }

    @PostMapping("/bindIdToKeyword") // ProductGroupId를 Keyword 랑 bind하기
    public Long bindProductGroupIdToKeyword(String productGroupId, Double score, String keyword) {
        return lowestPriceService.bindProductGroupIdToKeyword(productGroupId, score, keyword);
    }

    @PostMapping("/lowest/product") // Keyword 로 검색해서 가장 낮은 가격의 제품 가져오기
    public Keyword getLowestPriceProductByKeyword(String keyword) {
        return lowestPriceService.getLowestPriceProductByKeyword(keyword);
    }
}
