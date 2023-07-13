package com.example.oneulnail.global.exception;

import com.example.oneulnail.global.constants.BaseResponseStatus;
import lombok.Getter;

@Getter
public class NotFoundException extends BaseException  {
    private String message;

    public NotFoundException(String message) {
        super(BaseResponseStatus._BAD_REQUEST);
        this.message = message;
    }

    public NotFoundException(BaseResponseStatus errorCode, String message) {
        super(errorCode);
        this.message = message;
    }

    public NotFoundException(BaseResponseStatus errorCode) {
        super(errorCode);
    }
}