package com.example.oneulnail.domain.order.repository;

import com.example.oneulnail.domain.order.entity.Order;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query("select o from Order o")
    Slice<Order> findAllSlice(Pageable pageable);

    @Query("UPDATE Order o SET o.status = '주문 취소' WHERE o.Id= :orderId")
    List<Order> save(@Param("orderId") Long orderId);

//    @Query("SELECT o FROM Order o WHERE ")
    @Query("SELECT o from Order o WHERE o.status = :status")
    Slice<Order> findByStatusSlice(@Param("status") String status, Pageable pageable);


}
