package com.example.oneulnail.domain.user.mapper;

import com.example.oneulnail.domain.user.dto.response.SignInResDto;
import com.example.oneulnail.domain.user.dto.response.SignUpResDto;
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
                .accessToken("Bearer " + accessToken)
                .refreshToken("Bearer " + refreshToken)
                .build();
    }

    public SignInResDto signInAccessEntityToDto(String accessToken){
        return SignInResDto.builder()
                .msg("AccessToken 갱신완료")
                .accessToken("Bearer " + accessToken)
                .refreshToken(null)
                .build();
    }

}
