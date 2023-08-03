package com.example.oneulnail.domain.order.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OrderRegisterReqDto {

    private Long orderId;
    private String paymentMethod;
    private int totalPrice;
    private String status;
}
