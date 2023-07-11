package com.example.oneulnail.common.config.security.oauth2.controller;

import com.example.oneulnail.common.config.security.oauth2.dto.OAuth2SignUpReqDto;
import com.example.oneulnail.common.config.security.oauth2.dto.OAuth2SignUpResDto;
import com.example.oneulnail.common.config.security.oauth2.service.OAuth2SignUpService;
import com.example.oneulnail.common.entity.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequiredArgsConstructor
public class OAuth2Controller {

    private final OAuth2SignUpService oAuth2SignUpService;

    @PostMapping("/oauth2/sign-up")
    public BaseResponse<OAuth2SignUpResDto> oAuth2SignUp(HttpServletRequest request, @ModelAttribute OAuth2SignUpReqDto oAuth2SignUpReqDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            System.out.println("bindingResult 유효성 검사 오류가 있는 경우 처리");
            // 유효성 검사 오류가 있는 경우 처리
        }
        OAuth2SignUpResDto oAuth2SignUpResDto = oAuth2SignUpService.signUp(request, oAuth2SignUpReqDto);
        return BaseResponse.onSuccess(oAuth2SignUpResDto);
    }

    @GetMapping("/oauth2/sign-up")
    public String signUpForm(Model model) {
        model.addAttribute("oAuth2SignUpReqDto", new OAuth2SignUpReqDto());
        return "signup";
    }
}
