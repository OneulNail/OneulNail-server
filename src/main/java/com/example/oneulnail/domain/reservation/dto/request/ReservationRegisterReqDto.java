package com.example.oneulnail.domain.reservation.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class ReservationRegisterReqDto {
    private Long shopId;
    private LocalDateTime date;
}
