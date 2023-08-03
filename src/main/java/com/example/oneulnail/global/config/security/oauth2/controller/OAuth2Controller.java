package com.example.oneulnail.global.config.security.oauth2.controller;

import com.example.oneulnail.domain.user.entity.User;
import com.example.oneulnail.global.annotation.LoginUser;
import com.example.oneulnail.global.config.security.oauth2.dto.OAuth2SignUpReqDto;
import com.example.oneulnail.global.config.security.oauth2.dto.OAuth2SignUpResDto;
import com.example.oneulnail.global.config.security.oauth2.service.OAuth2SignUpService;
import com.example.oneulnail.global.entity.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Tag(name = "소셜로그인")
@RestController
@RequiredArgsConstructor
public class OAuth2Controller {

    private final OAuth2SignUpService oAuth2SignUpService;

    @Operation(summary = "소셜로그인 회원가입")
    @PostMapping("/oauth2/sign-up")
    public BaseResponse<OAuth2SignUpResDto> oAuth2SignUp(
            @Parameter(hidden = true) @LoginUser User user,
            @RequestBody OAuth2SignUpReqDto oAuth2SignUpReqDto) {
        OAuth2SignUpResDto oAuth2SignUpResDto = oAuth2SignUpService.signUp(user, oAuth2SignUpReqDto);
        return BaseResponse.onSuccess(oAuth2SignUpResDto);
    }

    @Operation(summary = "소셜로그인 회원가입 페이지로 리다이렉션")
    @GetMapping("/oauth2/sign-up")
    public String signUpForm(Model model) {
        model.addAttribute("oAuth2SignUpReqDto", new OAuth2SignUpReqDto());
        return "signup";
    }
}
