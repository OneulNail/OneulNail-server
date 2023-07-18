package com.example.oneulnail.domain.post.entity;

import com.example.oneulnail.domain.shop.entity.Shop;
import com.example.oneulnail.global.entity.BaseEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "post")
public class Post extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "like_count")
    private int likeCount;

    @Column(name = "img_url")
    private String imgUrl;

    @Column(name = "price")
    private int price;

    @Column(name = "content")
    private String content;

    @Column(name = "category")
    private String category;

    @ManyToOne
    @JoinColumn(name = "shop_id")
    private Shop shop;

    @Builder
    public Post(String name, int likeCount, String imgUrl, int price, String content, String category, Shop shop) {
        this.name = name;
        this.likeCount = likeCount;
        this.imgUrl = imgUrl;
        this.price = price;
        this.content = content;
        this.category = category;
        this.shop = shop;
    }

    public void addLikeCount() {
        this.likeCount = likeCount + 1;
    }
}
