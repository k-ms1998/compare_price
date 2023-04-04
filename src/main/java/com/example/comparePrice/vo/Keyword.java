package com.example.comparePrice.vo;

import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Keyword {

    private String keyword;
    private List<ProductGroup> productGroupList;


    public static Keyword of(String keyword, List<ProductGroup> productGroupList) {
        return new Keyword(keyword, productGroupList);
    }
}
