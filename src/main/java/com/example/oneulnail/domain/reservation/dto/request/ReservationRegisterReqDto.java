package com.example.oneulnail.domain.reservation.dto.request;

import lombok.Builder;
import lombok.Getter;

import java.util.Date;

@Getter
@Builder
public class ReservationRegisterReqDto {
    private Long shopId;
    private Date date;
}
