package com.example.oneulnail.domain.order.exception;

import com.example.oneulnail.global.exception.BusinessException;
import com.example.oneulnail.global.response.ErrorCode;

public class NotFoundOrderEntityException extends BusinessException {
    public NotFoundOrderEntityException() {
        super(ErrorCode.ORDER_NOT_FOUND);
    }
}
