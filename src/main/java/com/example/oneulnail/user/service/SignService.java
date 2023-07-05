package com.example.oneulnail.user.service;


import com.example.oneulnail.common.config.security.JwtTokenProvider;
import com.example.oneulnail.common.entity.BaseResponse;
import com.example.oneulnail.user.dto.sign.SignInResDto;
import com.example.oneulnail.user.dto.sign.SignUpResDto;

// 예제 13.24
import com.example.oneulnail.user.entity.User;
import com.example.oneulnail.user.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class SignService {
    private final Logger LOGGER = LoggerFactory.getLogger(SignService.class);

    private UserRepository userRepository;
    private JwtTokenProvider jwtTokenProvider;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public SignService(UserRepository userRepository, JwtTokenProvider jwtTokenProvider,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.passwordEncoder = passwordEncoder;
    }

    public SignUpResDto signUp(String id, String password, String name, String role) {
        User user;
        if (role.equalsIgnoreCase("admin")) {
            user = User.builder()
                    .uid(id)
                    .name(name)
                    .password(passwordEncoder.encode(password))
                    .roles(Collections.singletonList("ROLE_ADMIN"))
                    .build();
        } else {
            user = User.builder()
                    .uid(id)
                    .name(name)
                    .password(passwordEncoder.encode(password))
                    .roles(Collections.singletonList("ROLE_USER"))
                    .build();
        }

        User savedUser = userRepository.save(user);
        SignUpResDto signUpResDto = new SignInResDto();

        if (!savedUser.getName().isEmpty()) {
            LOGGER.info("[getSignUpResult] 정상 처리 완료");
            setSuccessResult(signUpResDto);
        } else {
            LOGGER.info("[getSignUpResult] 실패 처리 완료");
            setFailResult(signUpResDto);
        }
        return signUpResDto;
    }

    public SignInResDto signIn(String id, String password) throws RuntimeException {
        User user = userRepository.getByUid(id);

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException();
        }
        LOGGER.info("[getSignInResult] 패스워드 일치");

        SignInResDto signInResDto = SignInResDto.builder()
                .token(jwtTokenProvider.createToken(String.valueOf(user.getUid()),
                        user.getRoles()))
                .build();

        setSuccessResult(signInResDto);

        return signInResDto;
    }

    // 결과 모델에 api 요청 성공
    private void setSuccessResult(SignUpResDto result) {
        result.setSuccess(true);
        result.setCode("1");
//        result.setMsg(BaseResponse.SUCCESS.getMsg());
        BaseResponse.onSuccess("성공");
    }

    // 결과 모델에 api 요청 실패
    private void setFailResult(SignUpResDto result) {
        result.setSuccess(false);
        result.setCode("0");
//        result.setMsg(CommonResponse.FAIL.getMsg());
//        BaseResponse.onFailure('4','t',result);
        BaseResponse.onSuccess("실패");
    }
}