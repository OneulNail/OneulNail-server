package com.example.oneulnail.domain.reservation.repository;


import com.example.oneulnail.domain.reservation.entity.Reservation;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    @Query("SELECT r from Reservation r WHERE r.shop.id = :shopId")
    Slice<Reservation> findReservationsByShopIdSlice(@Param("shopId") Long shopId, Pageable pageable);

    @Query("SELECT r from Reservation r")
    Slice<Reservation> findAllSlice(Pageable pageable);

    Slice<Reservation> findByShopIdAndDate(Long shopId, LocalDate date);

    @Query("SELECT r FROM Reservation r WHERE r.shop.id = :shopId AND r.date = :date " +
            "AND ((r.startTime >= :startTime AND r.startTime < :endTime) " +
            "OR (r.endTime > :startTime AND r.endTime <= :endTime))")
    List<Reservation> findOverlappingReservations(@Param("shopId") Long shopId,
                                                  @Param("date") LocalDate date,
                                                  @Param("startTime") LocalTime startTime,
                                                  @Param("endTime") LocalTime endTime);
}
