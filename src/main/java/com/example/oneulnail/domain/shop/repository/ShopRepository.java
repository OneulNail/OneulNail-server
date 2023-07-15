package com.example.oneulnail.domain.shop.repository;

import com.example.oneulnail.domain.shop.entity.Shop;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ShopRepository extends JpaRepository<Shop, Long> {
    @Query("SELECT s FROM Shop s")
    Slice<Shop> findAllSlice(Pageable pageable);
}
