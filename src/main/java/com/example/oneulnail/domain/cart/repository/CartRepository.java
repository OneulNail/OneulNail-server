package com.example.oneulnail.domain.cart.repository;

import com.example.oneulnail.domain.cart.entity.Cart;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CartRepository extends JpaRepository<Cart,Long> {

    @Query("SELECT c FROM Cart c JOIN c.user u WHERE u.id = :userId")
    Slice<Cart> findCartByUserIdSlice(@Param("userId") Long userId, Pageable pageable);

    @Query("SELECT c FROM Cart c JOIN c.user u WHERE u.id = :userId and c.id = :cartId")
    Cart findCartByUserId(@Param("userId") Long userId,@Param("cartId") Long cartId);

}
