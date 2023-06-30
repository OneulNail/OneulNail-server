package com.example.oneulnail.common.exception;

import com.example.oneulnail.common.constants.BaseResponseStatus;
import lombok.Getter;

import static com.example.oneulnail.common.constants.BaseResponseStatus._UNAUTHORIZED;

@Getter
public class UnauthorizedException extends BaseException {
    private String message;

    public UnauthorizedException(String message) {
        super(_UNAUTHORIZED);
        this.message = message;
    }

    public UnauthorizedException(BaseResponseStatus errorCode, String message) {
        super(errorCode);
        this.message = message;
    }

    public UnauthorizedException(BaseResponseStatus errorCode) {
        super(errorCode);
    }

}
