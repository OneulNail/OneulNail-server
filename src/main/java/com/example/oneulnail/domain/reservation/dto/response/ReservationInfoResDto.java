package com.example.oneulnail.domain.reservation.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Builder
public class ReservationInfoResDto {
    private Long reservationId;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
}
