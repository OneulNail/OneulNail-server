package com.example.oneulnail.domain.product.repository;

import com.example.oneulnail.domain.product.entity.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductRepository extends JpaRepository<Product,Long> {
    @Query("select p from Product p")
    Slice<Product> findAllSlice(Pageable pageable);
}
