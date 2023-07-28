package com.example.oneulnail.domain.user.controller;

import com.example.oneulnail.domain.user.dto.request.SignInReqDto;
import com.example.oneulnail.domain.user.dto.request.SignUpReqDto;
import com.example.oneulnail.domain.user.dto.response.SignInResDto;
import com.example.oneulnail.domain.user.dto.response.SignMessageResDto;
import com.example.oneulnail.domain.user.dto.response.SignUpResDto;
import com.example.oneulnail.global.config.security.JwtTokenProvider;
import com.example.oneulnail.global.entity.BaseResponse;
import com.example.oneulnail.global.exception.BadRequestException;
import com.example.oneulnail.domain.user.service.SignService;
import io.swagger.annotations.ApiParam;


import java.io.IOException;
import java.util.Random;

import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.service.DefaultMessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import static com.example.oneulnail.global.constants.BaseResponseStatus.*;

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
    public BaseResponse<SignInResDto> signIn(@ApiParam(value = "User-Login", required = true) @RequestBody SignInReqDto signInReqDto) throws IOException {
        if (signInReqDto.getPhone_num() == null) {
            throw new BadRequestException(USERS_EMPTY_USER_PHONE_NUMBER);
        }
        if (signInReqDto.getPassword() == null) {
            throw new BadRequestException(USERS_EMPTY_USER_PASSWORD);
        }

        SignInResDto signInResDto = signService.signIn(signInReqDto.getPhone_num(), signInReqDto.getPassword());
        return BaseResponse.onSuccess(signInResDto);
    }

    @PostMapping(value = "/sign-up")
    public BaseResponse<SignUpResDto> signUp(
            @ApiParam(value = "User-Join", required = true) @RequestBody SignUpReqDto signUpReqDto) throws IOException{

        if(signUpReqDto.getPhoneNum()==null) throw new BadRequestException(USERS_EMPTY_USER_PHONE_NUMBER);
        if(signUpReqDto.getPassword()==null)throw new BadRequestException(USERS_EMPTY_USER_PASSWORD);
        if(signUpReqDto.getName()==null) throw new BadRequestException(POST_USERS_EMPTY_NAME);
          SignUpResDto signUpResDto = signService.signUp(signUpReqDto);

        LOGGER.info("회원가입을 완료. 전화번호 : {}", signUpReqDto.getPhoneNum());
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

    @PostMapping("/reissue")
    public BaseResponse<SignInResDto> reissue(HttpServletRequest httpServletRequest){
        SignInResDto reissue = signService.reissue(httpServletRequest);
        return BaseResponse.onSuccess(reissue);
    }

}