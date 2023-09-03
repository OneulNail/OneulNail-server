package com.example.oneulnail.domain.likeStyle.exception;

import com.example.oneulnail.global.exception.BusinessException;
import com.example.oneulnail.global.response.ErrorCode;

public class NotFoundLikeStyleEntityException extends BusinessException {
    public NotFoundLikeStyleEntityException() {
        super(ErrorCode.LIKE_STYLE_NOT_FOUND);
    }
}
