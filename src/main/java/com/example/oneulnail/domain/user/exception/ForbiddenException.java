package com.example.oneulnail.domain.user.exception;

import com.example.oneulnail.global.exception.BusinessException;
import com.example.oneulnail.global.response.ErrorCode;

public class ForbiddenException extends BusinessException {
    public ForbiddenException() {
        super(ErrorCode.EXPIRED_JWT_EXCEPTION);
    }
}
