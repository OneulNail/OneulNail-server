package com.example.oneulnail.user.controller;

import com.example.oneulnail.common.config.security.JwtTokenProvider;
import com.example.oneulnail.common.config.security.oauth2.Role;
import com.example.oneulnail.common.entity.BaseResponse;
import com.example.oneulnail.common.exception.BaseException;
import com.example.oneulnail.user.dto.sign.SignInResDto;
import com.example.oneulnail.user.dto.sign.SignUpResDto;
import com.example.oneulnail.user.service.SignService;
import io.swagger.annotations.ApiParam;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.example.oneulnail.common.constants.BaseResponseStatus.*;

@RestController
@RequestMapping("/user")
public class SignController {

    private final Logger LOGGER = LoggerFactory.getLogger(SignController.class);
    private final SignService signService;
    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public SignController(SignService signService, JwtTokenProvider jwtTokenProvider ) {
        this.signService = signService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping(value = "/sign-in")
    public BaseResponse<SignInResDto> signIn(
            @ApiParam(value = "PhoneNum", required = true) @RequestParam String phone_num,
            @ApiParam(value = "Password", required = true) @RequestParam String password)
            throws RuntimeException {
        SignInResDto signInResDto = signService.signIn(phone_num, password);
        if(phone_num==null) throw new BaseException(USERS_EMPTY_USER_PHONE_NUMBER);

        if(password==null)throw new BaseException(USERS_EMPTY_USER_PASSWORD);
        return BaseResponse.onSuccess(signInResDto);
    }

    @PostMapping(value = "/sign-up")
    public BaseResponse<SignUpResDto> signUp(
            @ApiParam(value = "전화번호", required = true) @RequestParam String phone_num,
            @ApiParam(value = "비밀번호", required = true) @RequestParam String password,
            @ApiParam(value = "이름", required = true) @RequestParam String name,
            @ApiParam(value = "권한", required = true) @RequestParam Role role) {
        SignUpResDto signUpResDto = signService.signUp(id, password, name, role);

        LOGGER.info("회원가입을 완료. 전화번호 : {}", phone_num);
        return BaseResponse.onSuccess(signUpResDto);
    }

    @GetMapping(value = "/exception")
    public void exceptionTest() throws RuntimeException {
        throw new RuntimeException("접근이 금지되었습니다.");
    }

    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<Map<String, String>> ExceptionHandler(RuntimeException e) {
        HttpHeaders responseHeaders = new HttpHeaders();
        //responseHeaders.add(HttpHeaders.CONTENT_TYPE, "application/json");
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

        LOGGER.error("ExceptionHandler 호출, {}, {}", e.getCause(), e.getMessage());

        Map<String, String> map = new HashMap<>();
        map.put("error type", httpStatus.getReasonPhrase());
        map.put("code", "400");
        map.put("message", "에러 발생");

        return new ResponseEntity<>(map, responseHeaders, httpStatus);
    }

}