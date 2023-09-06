package com.example.oneulnail.domain.user.controller;

import com.example.oneulnail.domain.user.dto.request.SignInReqDto;
import com.example.oneulnail.domain.user.dto.request.SignUpReqDto;
import com.example.oneulnail.domain.user.dto.response.SignInResDto;
import com.example.oneulnail.domain.user.dto.response.SignMessageResDto;
import com.example.oneulnail.domain.user.dto.response.SignUpResDto;
import com.example.oneulnail.domain.user.service.SignService;


import java.util.Random;

import com.example.oneulnail.global.response.ResultCode;
import com.example.oneulnail.global.response.ResultResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.service.DefaultMessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


@Tag(name = "자체 로그인, 회원가입")
@RestController
@RequestMapping("/user")
public class SignController {

    final DefaultMessageService messageService;
    private final Logger LOGGER = LoggerFactory.getLogger(SignController.class);
    private final SignService signService;

    @Autowired
    public SignController(SignService signService,
                          @Value("${COOLSMS_API_KEY}") String apiKey,
                          @Value("${COOLSMS_API_SECRET}") String apiSecret ) {
        this.messageService = NurigoApp.INSTANCE.initialize(
                apiKey, apiSecret, "https://api.coolsms.co.kr");
        this.signService = signService;
    }

    @Operation(summary = "자체 로그인")
    @PostMapping(value = "/sign-in")
    public ResponseEntity<ResultResponse> signIn(@RequestBody SignInReqDto signInReqDto) {
        SignInResDto signInResDto = signService.signIn(signInReqDto.getEmail(), signInReqDto.getPassword());
        return ResponseEntity.ok(ResultResponse.of(ResultCode.USER_LOGIN_SUCCESS, signInResDto));
    }

    @Operation(summary = "자체 회원가입")
    @PostMapping(value = "/sign-up")
    public ResponseEntity<ResultResponse> signUp(@RequestBody SignUpReqDto signUpReqDto) {
        SignUpResDto signUpResDto = signService.signUp(signUpReqDto);

        LOGGER.info("회원가입을 완료. 전화번호 : {}", signUpReqDto.getPhoneNum());
        return ResponseEntity.ok(ResultResponse.of(ResultCode.USER_CREATE_SUCCESS, signUpResDto));
    }

    // 인증문자 (단일) 메시지 발송
    @PostMapping("/authentication-message")
    public ResponseEntity<ResultResponse> sendMessage(@RequestBody String phone_num) {

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

        return ResponseEntity.ok(ResultResponse.of(ResultCode.USER_AUTHENTICATION_MESSAGE_SEND_SUCCESS, signMessageResDto));
    }

    @PostMapping("/reissue")
    public ResponseEntity<ResultResponse> reissue(HttpServletRequest httpServletRequest){
        SignInResDto reissue = signService.reissue(httpServletRequest);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.USER_REISSUE_SUCCESS, reissue));
    }
}