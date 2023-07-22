package com.example.oneulnail.domain.user.service;


import com.example.oneulnail.domain.user.dto.request.SignUpReqDto;
import com.example.oneulnail.domain.user.dto.response.SignInResDto;
import com.example.oneulnail.domain.user.dto.response.SignUpResDto;
import com.example.oneulnail.domain.user.entity.User;
import com.example.oneulnail.domain.user.mapper.SignMapper;
import com.example.oneulnail.domain.user.repository.UserRepository;
import com.example.oneulnail.global.config.security.JwtTokenProvider;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class SignService {
    private final Logger LOGGER = LoggerFactory.getLogger(SignService.class);

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final SignMapper signMapper;

    @Transactional
    public SignUpResDto signUp(SignUpReqDto signUpReqDto) {
        User newUser = buildUser(signUpReqDto);

       User savedUser = userRepository.save(newUser);

        return signMapper.signUpEntityToDto();
    }

    @Transactional(readOnly = true)
    public SignInResDto signIn(String phone_num, String password) throws RuntimeException {
        User user = userRepository.getByPhoneNum(phone_num);

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException();
        }
        return signMapper.signInEntityToDto(user);
    }

    private User buildUser(SignUpReqDto signUpReqDto){
        return User.builder()
                .phoneNum(signUpReqDto.getPhone_num())
                .password(passwordEncoder.encode(signUpReqDto.getPassword()))
                .name(signUpReqDto.getName())
                .role(signUpReqDto.getRole())
                .build();
    }

}