package com.example.oneulnail.domain.shop.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ShopListResDto {

    private Long id;
    private String name;
    private String phoneNumber;
    private String location;
    private String operatingHours;
    private int likesCount;
    private String imgUrl;
    private int basePrice;
    private String shopInfo;

}
