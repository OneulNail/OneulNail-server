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

    final DefaultMessageService messageService;
    private final Logger LOGGER = LoggerFactory.getLogger(SignController.class);
    private final SignService signService;
    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public SignController(SignService signService, JwtTokenProvider jwtTokenProvider,
                          @Value("${COOLSMS_API_KEY}") String apiKey,
                          @Value("${COOLSMS_API_SECRET}") String apiSecret ) {
        this.messageService = NurigoApp.INSTANCE.initialize(
                apiKey, apiSecret, "https://api.coolsms.co.kr");
        this.signService = signService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping(value = "/sign-in")
    public BaseResponse<SignInResDto> signIn(
            @ApiParam(value = "User-Login", required = true) @RequestBody SignInReqDto signInReqDto)
            throws RuntimeException {
        SignInResDto signInResDto = signService.signIn(signInReqDto.getPhone_num(), signInReqDto.getPassword());
        if(signInReqDto.getPhone_num()==null) throw new BaseException(USERS_EMPTY_USER_PHONE_NUMBER);

        if(signInReqDto.getPassword()==null)throw new BaseException(USERS_EMPTY_USER_PASSWORD);
        return BaseResponse.onSuccess(signInResDto);
    }

    @PostMapping(value = "/sign-up")
    public BaseResponse<SignUpResDto> signUp(
            @ApiParam(value = "User-Join", required = true) @RequestBody SignUpReqDto signUpReqDto) {
        SignUpResDto signUpResDto = signService.signUp(signUpReqDto.getPhone_num(), signUpReqDto.getPassword(),signUpReqDto.getName(), signUpReqDto.getRole());

        LOGGER.info("회원가입을 완료. 전화번호 : {}", signUpReqDto.getPhone_num());
        return BaseResponse.onSuccess(signUpResDto);
    }

    // 인증문자 (단일) 메시지 발송
    @PostMapping("/authentication-message")
    public BaseResponse<SignMessageResDto> sendMessage(@RequestBody String phone_num) {

        SignMessageResDto signMessageResDto = new SignMessageResDto();
        Random rand  = new Random();
        String numStr = "";
        for(int i=0; i<4; i++) {
            String ran = Integer.toString(rand.nextInt(10));
            numStr+=ran;
        }

        Message message = new Message();
        // 발신번호 및 수신번호는 반드시 01012345678 형태로 입력되어야 합니다.
        message.setFrom("01040282129");
        message.setTo(phone_num);
        message.setText("OneulNail 인증번호: " + numStr);

        this.messageService.sendOne(new SingleMessageSendingRequest(message));
        LOGGER.info("인증번호:"+numStr);
        signMessageResDto.setAuthenticationNumber(numStr);

        return BaseResponse.onSuccess(signMessageResDto);
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