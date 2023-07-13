package com.example.oneulnail.domain.shop.entity;

import com.example.oneulnail.global.entity.BaseEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "shop")
public class Shop extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "phone_number", length = 11)
    private String phoneNumber;

    @Column(name = "location")  // 지역
    private String location;

    @Column(name = "operating_hours")  // 영업시간
    private String operatingHours;

    @Column(name = "likes_count")  // 즐겨찾기 개수
    private int likesCount;

    @Column(name = "img_url")
    private String imgUrl;

    @Column(name = "base_price")
    private int basePrice;

    @Column(name = "shop_info")
    private String shopInfo;

    @Builder
    public Shop(String name, String phoneNumber, String location, String operatingHours, int likesCount,
                String imgUrl, int basePrice, String shopInfo) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.location = location;
        this.operatingHours = operatingHours;
        this.likesCount = likesCount;
        this.imgUrl = imgUrl;
        this.basePrice = basePrice;
        this.shopInfo = shopInfo;
    }
}
