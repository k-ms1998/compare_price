package com.example.comparePrice.vo;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    private String productGroupId;
    private String productId;
    private int price;

    public static Product of(String productGroupId, String productId, int price) {
        return new Product(productGroupId, productId, price);
    }

}
