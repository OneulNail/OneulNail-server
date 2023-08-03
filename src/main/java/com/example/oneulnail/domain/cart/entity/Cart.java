package com.example.oneulnail.domain.cart.entity;

import com.example.oneulnail.domain.order.entity.Order;
import com.example.oneulnail.domain.product.entity.Product;
import com.example.oneulnail.domain.shop.entity.Shop;
import com.example.oneulnail.domain.user.entity.User;
import com.example.oneulnail.global.entity.BaseEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "cart")
public class Cart extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int quantity;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Builder
    public Cart(int quantity, User user, Product product){
        this.quantity = quantity;
        this.user = user;
        this.product = product;
    }
}
