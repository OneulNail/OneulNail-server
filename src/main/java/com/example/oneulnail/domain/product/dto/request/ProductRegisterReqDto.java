package com.example.oneulnail.domain.product.dto.request;

import com.example.oneulnail.domain.product.entity.ProductCategory;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProductRegisterReqDto {
    private String name;
    private String imgUrl;
    private int price;
    private ProductCategory category;
}
