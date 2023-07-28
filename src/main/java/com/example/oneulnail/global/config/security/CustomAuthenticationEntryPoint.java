package com.example.oneulnail.global.config.security;

import com.example.oneulnail.global.constants.BaseResponseStatus;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.minidev.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

//인증 실패시 결과처리

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException ex) throws IOException {
        // 유효한 자격증명을 제공하지 않고 접근하려 할때 401
        String exception = (String) request.getAttribute("exception");

        BaseResponseStatus errorCode;


        /**
         * 토큰이 없는 경우 예외처리
         */
        if (exception == null) {
            errorCode = BaseResponseStatus.UNAUTHORIZED_EXCEPTION;
            setResponse(response, errorCode);
            return;
        }

        /**
         * 토큰이 만료된 경우 예외처리
         */
        if (exception.equals("NotExistUser")) {
            errorCode = BaseResponseStatus.NOT_EXIST_USER;
            setResponse(response, errorCode);
            return;
        } else if (exception.equals("ExpiredJwtException")) {
            errorCode = BaseResponseStatus.EXPIRED_JWT_EXCEPTION;
            setResponse(response, errorCode);
            return;
        } else if (exception.equals("MalformedJwtException")) {
            errorCode = BaseResponseStatus.INVALID_TOKEN_EXCEPTION;
            setResponse(response, errorCode);
            return;
        } else if (exception.equals("HijackException")) {
            errorCode = BaseResponseStatus.HIJACK_JWT_TOKEN_EXCEPTION;
            setResponse(response, errorCode);
            return;
        }
    }
    private void setResponse(HttpServletResponse response, BaseResponseStatus errorCode) throws IOException {
        JSONObject json = new JSONObject();
        response.setContentType("application/json;charset=UTF-8");
        response.setCharacterEncoding("utf-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        json.put("code", errorCode.getCode());
        json.put("message", errorCode.getMessage());
        json.put("isSuccess",false);
        response.getWriter().print(json);
    }
}


