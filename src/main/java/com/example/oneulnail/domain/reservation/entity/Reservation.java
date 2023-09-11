package com.example.oneulnail.domain.reservation.entity;

import com.example.oneulnail.domain.shop.entity.Shop;
import com.example.oneulnail.domain.user.entity.User;
import com.example.oneulnail.global.entity.BaseEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.tomcat.jni.Local;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "reservation")
public class Reservation extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private LocalTime startTime;

    private LocalTime endTime;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "shop_id")
    private Shop shop;

    @Builder
    public Reservation(LocalDate date, LocalTime startTime, LocalTime endTime, User user, Shop shop){
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.user = user;
        this.shop = shop;
    }
}
