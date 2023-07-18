package com.example.oneulnail.domain.reservation.repository;


import com.example.oneulnail.domain.reservation.entity.Reservation;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    @Query("SELECT r from Reservation r WHERE r.shop.id = :shopId")
    Slice<Reservation> findReservationsByShopIdSlice(@Param("shopId") Long shopId, Pageable pageable);
}
