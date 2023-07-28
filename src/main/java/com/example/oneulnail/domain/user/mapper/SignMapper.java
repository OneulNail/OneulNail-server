package com.example.oneulnail.domain.user.mapper;

import com.example.oneulnail.domain.user.dto.request.SignUpReqDto;
import com.example.oneulnail.domain.user.dto.response.SignInResDto;
import com.example.oneulnail.domain.user.dto.response.SignMessageResDto;
import com.example.oneulnail.domain.user.dto.response.SignUpResDto;
import com.example.oneulnail.domain.user.entity.User;
import com.example.oneulnail.global.config.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SignMapper {

    public SignUpResDto signUpEntityToDto(){
        return SignUpResDto.builder()
                .msg("회원가입에 성공했습니다.")
                .build();
    }

    public SignInResDto signInEntityToDto(String accessToken,String refreshToken){
        return SignInResDto.builder()
                .msg("로그인에 성공했습니다.")
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public SignInResDto signInAccessEntityToDto(String accessToken){
        return SignInResDto.builder()
                .msg("AccessToken 갱신완료")
                .accessToken(accessToken)
                .refreshToken(null)
                .build();
    }

}
