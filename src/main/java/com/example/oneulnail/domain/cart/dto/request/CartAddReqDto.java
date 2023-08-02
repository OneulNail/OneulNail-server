package com.example.oneulnail.domain.cart.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CartAddReqDto {
    private Long productId;

    private int quantity;
}
