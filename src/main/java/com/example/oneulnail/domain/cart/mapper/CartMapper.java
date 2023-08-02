package com.example.oneulnail.domain.cart.mapper;

import com.example.oneulnail.domain.cart.dto.response.CartInfoResDto;
import com.example.oneulnail.domain.cart.entity.Cart;
import org.springframework.stereotype.Component;

@Component
public class CartMapper {

    public CartInfoResDto cartEntityToCartInfo(Cart cart){
        return CartInfoResDto.builder()
                .cartId(cart.getId())
                .quantity(cart.getQuantity())
                .product(cart.getProduct())
                .build();
    }
}
