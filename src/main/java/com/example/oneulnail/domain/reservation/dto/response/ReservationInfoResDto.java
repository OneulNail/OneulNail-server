package com.example.oneulnail.domain.reservation.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ReservationInfoResDto {
    private Long reservationId;
    private LocalDateTime date;
}
