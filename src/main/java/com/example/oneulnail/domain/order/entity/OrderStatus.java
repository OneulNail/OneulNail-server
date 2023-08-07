package com.example.oneulnail.domain.order.entity;

import lombok.Getter;

@Getter
public enum OrderStatus {
    ORDER_COMPLETED("주문 완료"),
    ORDER_CANCELED("주문 취소"),
    SHIPPED("배송 중"),
    ARRIVED("상품 도착");
    private final String displayName;

    OrderStatus(String displayName) {
        this.displayName = displayName;
    }
}
