package com.example.oneulnail.domain.user.exception;

import com.example.oneulnail.global.exception.BusinessException;
import com.example.oneulnail.global.response.ErrorCode;

public class NotFoundUserEntityException extends BusinessException {
    public NotFoundUserEntityException() {
        super(ErrorCode.USER_NOT_FOUND);
    }
}
