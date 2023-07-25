package com.example.oneulnail.domain.product.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProductInfoResDto {
    private String name;
    private String imgUrl;
    private int price;
}
