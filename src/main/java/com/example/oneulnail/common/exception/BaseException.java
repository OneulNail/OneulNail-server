package com.example.oneulnail.common.exception;

import com.example.oneulnail.common.constants.BaseResponseStatus;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.Map;

@Getter
public class BaseException extends RuntimeException {

    HttpStatus httpStatus;
    BaseResponseStatus status;
    String responseMessage;
    Object result;
    Map<String, String> data;


    public BaseException(BaseResponseStatus status) {
        super();
        this.status = status;
        this.responseMessage = status.getMessage();
        this.httpStatus=status.getHttpStatus();
    }

    public BaseException(BaseResponseStatus status, Map<String, String> data) {
        super();
        this.status = status;
        this.responseMessage = status.getMessage();
        this.httpStatus=status.getHttpStatus();
        this.data = data;
    }
}

