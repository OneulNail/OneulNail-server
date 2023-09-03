package com.example.oneulnail.domain.user.exception;

import com.example.oneulnail.global.exception.BusinessException;
import com.example.oneulnail.global.response.ErrorCode;

public class ExpiredJwtException extends BusinessException {
    public ExpiredJwtException() {
        super(ErrorCode.EXPIRED_JWT_EXCEPTION);
    }
}
