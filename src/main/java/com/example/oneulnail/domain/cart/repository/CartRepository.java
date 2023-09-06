package com.example.oneulnail.domain.cart.repository;

import com.example.oneulnail.domain.cart.entity.Cart;
import com.example.oneulnail.domain.order.dto.request.OrderRegisterReqDto;
import com.example.oneulnail.domain.order.entity.Order;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart,Long> {

    @Query("SELECT c FROM Cart c JOIN c.user u WHERE u.id = :userId")
    Slice<Cart> findCartByUserIdSlice(@Param("userId") Long userId, Pageable pageable);

    @Query("SELECT c FROM Cart c JOIN c.user u WHERE u.id = :userId AND c.order IS NULL")
    List<Cart> findCartByUserIdNotOrder(@Param("userId") Long userId);

    @Query("SELECT c FROM Cart c JOIN c.user u WHERE u.id = :userId and c.id = :cartId")
    Optional<Cart> findCartByUserId(@Param("userId") Long userId, @Param("cartId") Long cartId);

    @Query("SELECT c FROM Cart c JOIN FETCH c.user u JOIN FETCH c.order o WHERE u.id = :userId AND o.id = :orderId")
    List<Cart> findCartsByUserIdAndOrderId(@Param("userId") Long userId, @Param("orderId") Long orderId);
}
