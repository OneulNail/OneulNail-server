package com.example.oneulnail.global.exception;

import com.example.oneulnail.global.constants.BaseResponseStatus;
import lombok.Getter;

@Getter
public class InternalServerException extends BaseException {
    private String message;

    public InternalServerException(String message) {
        super(BaseResponseStatus._INTERNAL_SERVER_ERROR);
        this.message = message;
    }

    public InternalServerException(BaseResponseStatus errorCode, String message) {
        super(errorCode);
        this.message = message;
    }

    public InternalServerException(BaseResponseStatus errorCode) {
        super(errorCode);
    }
}