package com.example.oneulnail.domain.order.repository;

import com.example.oneulnail.domain.order.entity.Order;
import com.example.oneulnail.domain.order.entity.OrderStatus;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query("select o from Order o JOIN o.user u WHERE u.id = :userId")
    Slice<Order> findAllSlice(@Param("userId") Long userId,Pageable pageable);

    @Query("SELECT o from Order o JOIN o.user u WHERE u.id = :userId AND o.status = :status")
    Slice<Order> findByStatusSlice(@Param("userId") Long userId ,@Param("status") OrderStatus status, Pageable pageable);

}
