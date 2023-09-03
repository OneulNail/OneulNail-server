package com.example.oneulnail.domain.user.exception;

import com.example.oneulnail.global.exception.BusinessException;
import com.example.oneulnail.global.response.ErrorCode;

public class EmailExistsException extends BusinessException {
    public EmailExistsException() {
        super(ErrorCode.USERS_EXISTS_EMAIL);
    }
}
