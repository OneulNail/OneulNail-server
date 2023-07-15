package com.example.oneulnail.domain.shop.mapper;

import com.example.oneulnail.domain.shop.dto.response.ShopListResDto;
import com.example.oneulnail.domain.shop.dto.response.ShopFindOneResDto;
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

    public ShopFindOneResDto shopFindOneEntityToDto(Shop shop) {
        return ShopFindOneResDto.builder()
                .id(shop.getId())
                .name(shop.getName())
                .phoneNumber(shop.getPhoneNumber())
                .location(shop.getLocation())
                .operatingHours(shop.getOperatingHours())
                .likesCount(shop.getLikesCount())
                .imgUrl(shop.getImgUrl())
                .basePrice(shop.getBasePrice())
                .shopInfo(shop.getShopInfo())
                .build();
    }

    public ShopListResDto entityToShopListResDto(Shop shop) {
        return ShopListResDto.builder()
                .id(shop.getId())
                .name(shop.getName())
                .phoneNumber(shop.getPhoneNumber())
                .location(shop.getLocation())
                .operatingHours(shop.getOperatingHours())
                .likesCount(shop.getLikesCount())
                .imgUrl(shop.getImgUrl())
                .basePrice(shop.getBasePrice())
                .shopInfo(shop.getShopInfo())
                .build();
    }
}
