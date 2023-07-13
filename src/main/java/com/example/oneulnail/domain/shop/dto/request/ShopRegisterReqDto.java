package com.example.oneulnail.domain.shop.dto.request;

import lombok.Getter;

@Getter
public class ShopRegisterReqDto {

    private String name;

    private String phoneNumber;

    private String location;

    private String operatingHours;

    private String imgUrl;

    private int basePrice;

    private String shopInfo;
}
