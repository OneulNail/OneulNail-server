package com.example.oneulnail.domain.cart.dto.response;

import com.example.oneulnail.domain.product.entity.Product;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CartInfoResDto {
    private Long cartId;
    private int quantity;
    private Product product;
}
