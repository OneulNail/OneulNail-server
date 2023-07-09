package com.example.oneulnail.user.service;


import com.example.oneulnail.common.config.security.JwtTokenProvider;
import com.example.oneulnail.common.config.security.oauth2.Role;
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

    public SignUpResDto signUp(String phone_num, String password, String name, Role role) {
        User user;
        if (role.getKey().equals("ROLE_ADMIN")) {
            user = User.builder()
                    .phoneNum(phone_num)
                    .name(name)
                    .password(passwordEncoder.encode(password))
                    .role(role)
                    .build();
        } else {
            user = User.builder()
                    .phoneNum(phone_num)
                    .name(name)
                    .password(passwordEncoder.encode(password))
                    .role(role)
                    .build();
        }

        User savedUser = userRepository.save(user);
        SignUpResDto signUpResDto = new SignUpResDto();

        if (!savedUser.getName().isEmpty()) {
            signUpResDto.setMsg("회원가입이 완료되었습니다.");
        } else {
            signUpResDto.setMsg("회원가입 실패");
        }
        return signUpResDto;
    }

    public SignInResDto signIn(String phone_num, String password) throws RuntimeException {
        User user = userRepository.getByPhoneNum(phone_num);

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException();
        }

        SignInResDto signInResDto = SignInResDto.builder()
                .token(jwtTokenProvider.createToken(String.valueOf(user.getPhoneNum()),
                        user.getRole().toString()))
                .build();

        signInResDto.setMsg("로그인에 성공하였습니다.");

        return signInResDto;
    }

}