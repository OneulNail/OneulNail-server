package com.example.oneulnail.domain.reservation.entity;

import com.example.oneulnail.domain.shop.entity.Shop;
import com.example.oneulnail.global.entity.BaseEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "reservation")
public class Reservation extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime date;

    @ManyToOne
    @JoinColumn(name = "shop_id")
    private Shop shop;

    @Builder
    public Reservation(LocalDateTime date,Shop shop){
        this.date= date;
        this.shop=shop;
    }

}
