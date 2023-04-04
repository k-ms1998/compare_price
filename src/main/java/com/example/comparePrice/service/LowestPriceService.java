package com.example.comparePrice.service;

import com.example.comparePrice.vo.Keyword;
import com.example.comparePrice.vo.Product;
import com.example.comparePrice.vo.ProductGroup;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public interface LowestPriceService {

    Set getZSetValue(String key);

    Long addNewProduct(Product product);

    Long addNewProductGroup(ProductGroup productGroup);

    Keyword getLowestPriceProductByKeyword(String keyword);

    Long bindProductGroupIdToKeyword(String productGroupId, Double score, String keyword);
}
