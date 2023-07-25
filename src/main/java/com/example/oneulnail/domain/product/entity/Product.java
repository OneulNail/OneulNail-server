package com.example.oneulnail.domain.product.entity;

import com.example.oneulnail.domain.user.entity.User;
import com.example.oneulnail.global.entity.BaseEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "product")
public class Product extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String imgUrl;

    private int price;

    @Builder
    public Product(String name, String imgUrl,int price){
        this.name = name;
        this.imgUrl = imgUrl;
        this.price = price;
    }
}
