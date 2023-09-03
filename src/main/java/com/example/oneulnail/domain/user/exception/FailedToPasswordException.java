package com.example.oneulnail.domain.user.exception;

import com.example.oneulnail.global.exception.BusinessException;
import com.example.oneulnail.global.response.ErrorCode;

public class FailedToPasswordException extends BusinessException {
    public FailedToPasswordException() {
        super(ErrorCode.FAILED_TO_PASSWORD);
    }
}
