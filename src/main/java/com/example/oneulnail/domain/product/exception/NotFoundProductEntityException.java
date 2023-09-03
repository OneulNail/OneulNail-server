package com.example.oneulnail.domain.product.exception;

import com.example.oneulnail.global.exception.BusinessException;
import com.example.oneulnail.global.response.ErrorCode;

public class NotFoundProductEntityException extends BusinessException {
    public NotFoundProductEntityException() {
        super(ErrorCode.PRODUCT_NOT_FOUND);
    }
}
