package com.example.oneulnail.domain.reservation.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@NoArgsConstructor
public class ReservationRegisterReqDto {
    private Long shopId;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
}
