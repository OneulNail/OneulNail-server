package com.example.oneulnail.global.exception;

import com.example.oneulnail.global.constants.BaseResponseStatus;
import lombok.Getter;
import static com.example.oneulnail.global.constants.BaseResponseStatus._BAD_REQUEST;

@Getter
public class ForbiddenException extends BaseException {
    private String message;

    public ForbiddenException(String message) {
        super(_BAD_REQUEST);
        this.message = message;
    }

    public ForbiddenException(BaseResponseStatus errorCode, String message) {
        super(errorCode);
        this.message = message;
    }

    public ForbiddenException(BaseResponseStatus errorCode) {
        super(errorCode);
    }

}
