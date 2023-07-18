package com.example.oneulnail.domain.reservation.repository;


import com.example.oneulnail.domain.reservation.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
}
