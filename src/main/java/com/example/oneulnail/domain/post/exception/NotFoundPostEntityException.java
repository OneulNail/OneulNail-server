package com.example.oneulnail.domain.post.exception;

import com.example.oneulnail.domain.order.exception.NotFoundOrderEntityException;
import com.example.oneulnail.global.exception.BusinessException;
import com.example.oneulnail.global.response.ErrorCode;

public class NotFoundPostEntityException extends BusinessException {
    public NotFoundPostEntityException() {
        super(ErrorCode.POST_NOT_FOUND);
    }
}
