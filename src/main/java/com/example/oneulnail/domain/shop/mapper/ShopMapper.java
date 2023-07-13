package com.example.oneulnail.domain.shop.mapper;

import com.example.oneulnail.domain.shop.dto.response.ShopRegisterResDto;
import com.example.oneulnail.domain.shop.entity.Shop;
import org.springframework.stereotype.Component;

@Component
public class ShopMapper {
    public ShopRegisterResDto ShopRegisterEntityToDto(Shop shop) {
        return ShopRegisterResDto.builder()
                .msg("성공")
                .build();
    }
}
