package com.example.oneulnail.domain.product.entity;

import lombok.Getter;

@Getter
public enum ProductCategory {
    NAIL_CARE("네일케어"),
    NAIL_POLISH("매니큐어"),
    REMOVE_GEL("젤 제거"),
    ETC("기타");

    private final String category;

    ProductCategory(String category) {
        this.category = category;
    }
}
