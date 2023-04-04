package com.example.comparePrice.vo;

import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ProductGroup {

    private String productGroupId;
    private List<Product> productList;

    public static ProductGroup of(String productGroupId, List<Product> productList) {
        return new ProductGroup(productGroupId, productList);
    }

}
