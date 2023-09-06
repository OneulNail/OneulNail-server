package com.example.oneulnail.domain.shop.exception;

import com.example.oneulnail.global.exception.BusinessException;
import com.example.oneulnail.global.response.ErrorCode;

public class NotFoundShopEntityException extends BusinessException {
    public NotFoundShopEntityException() {
        super(ErrorCode.SHOP_NOT_FOUND);
    }
}
