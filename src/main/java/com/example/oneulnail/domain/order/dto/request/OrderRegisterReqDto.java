package com.example.oneulnail.domain.order.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OrderRegisterReqDto {

    private String paymentMethod;
    private int totalPrice;
}
