package com.example.oneulnail.domain.user.service;


import com.example.oneulnail.domain.user.dto.request.SignUpReqDto;
import com.example.oneulnail.domain.user.dto.response.SignInResDto;
import com.example.oneulnail.domain.user.dto.response.SignUpResDto;
import com.example.oneulnail.domain.user.entity.RefreshToken;
import com.example.oneulnail.domain.user.entity.User;
import com.example.oneulnail.domain.user.mapper.SignMapper;
import com.example.oneulnail.domain.user.repository.RedisRepository;
import com.example.oneulnail.domain.user.repository.UserRepository;
import com.example.oneulnail.global.config.security.JwtTokenProvider;

import com.example.oneulnail.global.entity.BaseResponse;
import com.example.oneulnail.global.exception.BadRequestException;
import com.example.oneulnail.global.exception.UnauthorizedException;
import lombok.RequiredArgsConstructor;
import org.hibernate.jpa.boot.internal.Helper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;


import static com.example.oneulnail.global.constants.BaseResponseStatus.*;

@Service
@RequiredArgsConstructor
public class SignService {
    private final Logger LOGGER = LoggerFactory.getLogger(SignService.class);

    private final UserRepository userRepository;
    private final RedisRepository redisRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final SignMapper signMapper;

    @Transactional
    public SignUpResDto signUp(SignUpReqDto signUpReqDto) {
        if(userRepository.findByPhoneNum(signUpReqDto.getPhoneNum()).isPresent()){
            throw new BadRequestException(USERS_EXISTS_PHONE_NUMBER);
        }
        User newUser = buildUser(signUpReqDto);

       User savedUser = userRepository.save(newUser);

        return signMapper.signUpEntityToDto();
    }

    @Transactional(readOnly = true)
    public SignInResDto signIn(String phone_num, String password) throws RuntimeException {
        User user = userRepository.getByPhoneNum(phone_num);

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new BadRequestException(FAILED_TO_PASSWORD);
        }

        SignInResDto token = jwtTokenProvider.createToken(String.valueOf(user.getPhoneNum())); //atk,rtk 생성
        redisRepository.save(RefreshToken.builder() //로그인 시, RefreshToken 업데이트
                .id(phone_num)
                .refreshToken(token.getRefreshToken())
                .build());

        return token;
    }

    private User buildUser(SignUpReqDto signUpReqDto){
        return User.builder()
                .phoneNum(signUpReqDto.getPhoneNum())
                .password(passwordEncoder.encode(signUpReqDto.getPassword()))
                .name(signUpReqDto.getName())
                .role(signUpReqDto.getRole())
                .build();
    }

    public SignInResDto reissue(HttpServletRequest httpServletRequest) {
        String token = jwtTokenProvider.resolveToken(httpServletRequest);
        if (token != null && jwtTokenProvider.validateToken(httpServletRequest,token)) { //RTK 유효성확인
            if(jwtTokenProvider.isRefreshToken(token)) {
                RefreshToken refreshToken = redisRepository.findByRefreshToken(token);
                if (refreshToken != null) {
                    // Redis 에 저장된 RefreshToken 정보를 기반으로 AccessToken 생성
                    SignInResDto newToken = jwtTokenProvider.createAccessToken(refreshToken.getId()); //id: phoneNum
                    return newToken;
                }
            }
        }
        throw new UnauthorizedException(EXPIRED_JWT_EXCEPTION);
    }
}