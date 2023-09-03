package com.example.oneulnail.global.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.FORBIDDEN;

/** Enum Naming Format : {행위}_{목적어}_{성공여부} message format: 동사 명사형으로 마무리 */
@Getter
@AllArgsConstructor
public enum ResultCode {

    // Cart
    CART_CREATE_SUCCESS("C001", "장바구니 생성 성공"),
    GET_ALL_CART_SUCCESS("C002", "장바구니 전체 조회 성공"),
    DELETE_ONE_CART_SUCCESS("C003", "장바구니 단일 삭제 성공"),
    DELETE_ALL_CART_SUCCESS("C004", "장바구니 전체 삭제 성공"),

    // LikePost
    LIKE_POST_CREATE_SUCCESS("LP001", "즐겨찾기 생성 성공"),
    GET_LIKE_POST_SUCCESS("LP002", "즐겨찾기 조회 성공"),

    // LikeStyle
    LIKE_STYLE_CREATE_SUCCESS("LS001", "선호스타일 등록 성공"),
    GET_THREE_LIKE_STYLE_SUCCESS("LS002", "추천 네일 조회 성공"),

    // Order
    ORDER_CREATE_SUCCESS("O001","주문 생성 성공"),
    GET_ALL_ORDER_SUCCESS("O002","내 주문 정보 전체 조회 성공"),
    GET_ALL_ORDER_BY_STATUS_SUCCESS("O003","주문 상태별 조회 성공"),
    ORDER_CANCEL_SUCCESS("O004","주문 취소 성공"),

    // Post
    POST_CREATE_SUCCESS("P001", "게시물 생성 성공"),
    GET_ONE_POST_SUCCESS("P002", "게시물 단일 조회 성공"),
    GET_ALL_POST_SUCCESS("P003", "게시물 전체 조회 성공"),
    GET_ALL_POST_BY_SHOP_SUCCESS("P004", "가게별 게시물 조회 성공"),
    GET_ALL_POST_BY_CATEGORY_SUCCESS("P005", "카테고리별 게시물 조회 성공"),

    // Product
    PRODUCT_CREATE_SUCCESS("PR001", "상품 생성 성공"),
    GET_ONE_PRODUCT_SUCCESS("PR002", "상품 단일 조회 성공"),
    GET_ALL_PRODUCT_SUCCESS("PR003", "상품 전체 조회 성공"),

    // Reservation
    RESERVATION_CREATE_SUCCESS("R001", "예약 생성 성공"),
    GET_ALL_RESERVATION_BY_SHOP_SUCCESS("R002", "가게별 예약 조회 성공"),
    GET_ALL_RESERVATION_SUCCESS("R003", "예약 전체 조회 성공"),

    // Shop
    SHOP_CREATE_SUCCESS("S001", "가게 등록 성공"),
    GET_ONE_SHOP_SUCCESS("S002", "가게 단일 조회 성공"),
    GET_ALL_SHOP_SUCCESS("S003", "가게 전체 조회 성공"),

    // User
    USER_CREATE_SUCCESS("U001", "사용자 생성 성공"),
    USER_LOGIN_SUCCESS("U002", "사용자 로그인 성공"),
    USER_AUTHENTICATION_MESSAGE_SEND_SUCCESS("U003", "사용자 인증 번호 보내기 성공"),
    USER_REISSUE_SUCCESS("U004", "사용자 토큰 재발급 성공"),

    // OAuth
    OAUTH_USER_CREATE_SUCCESS("OU001", "소셜 로그인 사용자 생성 성공");

    private final String code;
    private final String message;
}
