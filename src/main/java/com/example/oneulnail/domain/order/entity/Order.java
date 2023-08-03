package com.example.oneulnail.domain.order.entity;

import com.example.oneulnail.domain.user.entity.User;
import com.example.oneulnail.global.entity.BaseEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "orders")
public class Order extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="paymentMethod")
    private String paymentMethod;

    @Column(name="totalPrice")
    private int totalPrice;

    @Column(name = "status")
    private String status;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public Order(String paymentMethod, int totalPrice, String status){
        this.paymentMethod = paymentMethod;
        this.totalPrice = totalPrice;
        this.status = status;
    }
}
