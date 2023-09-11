package com.example.oneulnail.global.response;

import lombok.AllArgsConstructor;
import lombok.Getter;


/** Enum Naming Format : {주체}_{이유} message format: 동사 명사형으로 마무리 */
@Getter
@AllArgsConstructor
public enum ErrorCode {
    // Global
    INTERNAL_SERVER_ERROR(500, "G001", "서버 오류"),
    INPUT_INVALID_VALUE(400, "G002", "잘못된 입력"),

    // Cart
    CART_NOT_FOUND(400, "C001", "장바구니를 찾을 수 없음"),

    // LikeStyle
    LIKE_STYLE_NOT_FOUND(400, "LS001", "선호 스타일을 찾을 수 없음"),

    // Order
    ORDER_NOT_FOUND(400, "O001", "주문을 찾을 수 없음"),

    // Post
    POST_NOT_FOUND(400, "P001", "게시물을 찾을 수 없음"),

    // Product
    PRODUCT_NOT_FOUND(400, "PR001", "상품을 찾을 수 없음"),

    // Shop
    SHOP_NOT_FOUND(400, "S001", "가게를 찾을 수 없음"),

    // User
    USERS_EXISTS_EMAIL(400,"U001","중복된 이메일입니다."),
    USERS_EXISTS_NAME(400, "U002","중복된 이름입니다."),
    FAILED_TO_PASSWORD(400, "U003","비밀번호가 잘못되었습니다."),
    USER_NOT_FOUND(400, "U004", "유저를 찾을 수 없음"),

    // Auth
    EXPIRED_JWT_EXCEPTION(401,"AUTH001", "기존 토큰이 만료되었습니다. 토큰을 재발급해주세요."),

    // Reservation
    RESERVATION_OVERLAP(400,"R001","이미 존재하는 예약시간 입니다.");

    private final int status;
    private final String code;
    private final String message;
}
