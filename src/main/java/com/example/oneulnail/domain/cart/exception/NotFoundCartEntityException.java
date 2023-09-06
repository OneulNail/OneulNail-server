package com.example.oneulnail.domain.cart.exception;

import com.example.oneulnail.global.exception.BusinessException;
import com.example.oneulnail.global.response.ErrorCode;

public class NotFoundCartEntityException extends BusinessException {
    public NotFoundCartEntityException() {
        super(ErrorCode.CART_NOT_FOUND);
    }
}
