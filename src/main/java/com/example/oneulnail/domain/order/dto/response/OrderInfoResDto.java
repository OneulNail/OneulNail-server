package com.example.oneulnail.domain.order.dto.response;

import com.example.oneulnail.domain.order.entity.OrderStatus;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OrderInfoResDto {
    private Long orderId;
    private String paymentMethod;
    private int totalPrice;
    private OrderStatus status;
}
