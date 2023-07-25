package com.example.oneulnail.domain.product.mapper;

import com.example.oneulnail.domain.product.dto.response.ProductInfoResDto;
import com.example.oneulnail.domain.product.dto.response.ProductRegisterResDto;
import com.example.oneulnail.domain.product.entity.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {
    public ProductRegisterResDto productEntityToDto(Product registerProduct) {
        return ProductRegisterResDto.builder()
                .msg("성공")
                .build();
    }

    public ProductInfoResDto productInfoEntityToDto(Product product){
        return ProductInfoResDto.builder()
                .name(product.getName())
                .imgUrl(product.getImgUrl())
                .price(product.getPrice())
                .build();
    }
}
