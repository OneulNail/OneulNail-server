package com.example.oneulnail.global.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

/**
 * 에러 코드 관리
 */
@Getter
@AllArgsConstructor
public enum BaseResponseStatus {
    /**
     * 잘못된 요청
     */
    _INTERNAL_SERVER_ERROR(INTERNAL_SERVER_ERROR, "COMMON000", "서버 에러, 관리자에게 문의 바랍니다."),
    _BAD_REQUEST(BAD_REQUEST,"COMMON001","잘못된 요청입니다."),
    _UNAUTHORIZED(UNAUTHORIZED,"COMMON002","권한이 잘못되었습니다"),
    _METHOD_NOT_ALLOWED(METHOD_NOT_ALLOWED, "COMMON003", "지원하지 않는 Http Method 입니다."),

    /*
        인증 관련 에러코드
     */
    ForbiddenException(UNAUTHORIZED,"AUTH002", "해당 요청에 대한 권한이 없습니다."),
    UNAUTHORIZED_EXCEPTION (UNAUTHORIZED,"AUTH003", "로그인 후 이용가능합니다. 토큰을 입력해 주세요"),
    EXPIRED_JWT_EXCEPTION(UNAUTHORIZED,"AUTH004", "기존 토큰이 만료되었습니다. 토큰을 재발급해주세요."),
    RELOGIN_EXCEPTION(UNAUTHORIZED,"AUTH005", "모든 토큰이 만료되었습니다. 다시 로그인해주세요."),
    INVALID_TOKEN_EXCEPTION(UNAUTHORIZED,"AUTH006","토큰이 올바르지 않습니다." ),
    HIJACK_JWT_TOKEN_EXCEPTION(UNAUTHORIZED,"AUTH007","탈취된(로그아웃 된) 토큰입니다 다시 로그인 해주세요."),
    INVALID_REFRESH_TOKEN(BAD_REQUEST,"AUTH009","리프레쉬 토큰이 유효하지 않습니다. 다시 로그인 해주세요"),






    /*
     * 1000 : 소셜 관련 예외 처리
     */



    APPLE_BAD_REQUEST(BAD_REQUEST, "OAUTH001", "애플 토큰이 잘못되었습니다."),
    APPLE_SERVER_ERROR(FORBIDDEN, "OAUTH002", "애플 서버와 통신에 실패 하였습니다."),
    FAIL_TO_MAKE_APPLE_PUBLIC_KEY(BAD_REQUEST, "OAUTH003", "새로운 애플 공개키 생성에 실패하였습니다."),







    /**
     * UXXX : USER 관련 에러
     */
    //

    USER_STATUS_UNACTIVATED(BAD_REQUEST,"U000","유저가 비활성화 상태입니다."),
    USERS_EMPTY_USER_EMAIL(BAD_REQUEST, "U001", "유저의 이메일을 입력해주세요."),
    USERS_EMPTY_USER_PASSWORD(BAD_REQUEST, "U002", "유저 비밀번호를 입력해주세요."),
    TOO_SHORT_PASSWORD(BAD_REQUEST, "U003", "비밀번호의 길이를 8자 이상을 설정해주세요."),

    FAILED_TO_SIGN_UP(FORBIDDEN, "U004", "회원가입에 실패하였습니다."),

    USERS_EXISTS_EMAIL(FORBIDDEN,"U005","중복된 이메일입니다."),

    USERS_EXISTS_NAME(FORBIDDEN,"U006","중복된 이름입니다."),

    POST_USERS_EMPTY_NAME(BAD_REQUEST,"U007","이름을 입력해주세요."),

    FAILED_TO_LOGIN(BAD_REQUEST, "U008", "로그인에 실패하였습니다."),
    NOT_EXIST_USER(BAD_REQUEST,"U009" , "해당 유저가 존재하지 않습니다."),
    ROLE_SETTING_FAILED(BAD_REQUEST,"U010","역할 설정이 잘못되었습니다."),
    FAILED_TO_PASSWORD(BAD_REQUEST, "U011","비밀번호가 잘못되었습니다."),
    USER_NOT_FOUND(BAD_REQUEST, "U012", "이메일에 해당하는 유저를 찾을 수 없습니다.");
























    private final HttpStatus httpStatus;
    private final String code;
    private final String message;


}