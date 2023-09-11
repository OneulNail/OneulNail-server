package com.example.oneulnail.domain.reservation.exception;

import com.example.oneulnail.global.exception.BusinessException;
import com.example.oneulnail.global.response.ErrorCode;

public class ReservationOverlapException extends BusinessException{
    public ReservationOverlapException() {
        super(ErrorCode.RESERVATION_OVERLAP);
    }
}
