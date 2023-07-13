package com.example.oneulnail.global.exception;


import com.example.oneulnail.global.constants.BaseResponseStatus;
import lombok.Getter;

import static com.example.oneulnail.global.constants.BaseResponseStatus._BAD_REQUEST;

@Getter
public class BadRequestException extends BaseException {
    private String message;

    public BadRequestException(String message) {
        super(_BAD_REQUEST);
        this.message = message;
    }

    public BadRequestException(BaseResponseStatus errorCode, String message) {
        super(errorCode);
        this.message = message;
    }

    public BadRequestException(BaseResponseStatus errorCode) {
        super(errorCode);
    }
}