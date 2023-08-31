package com.example.oneulnail.domain.user.service;


import com.example.oneulnail.domain.user.dto.request.SignUpReqDto;
import com.example.oneulnail.domain.user.dto.response.SignInResDto;
import com.example.oneulnail.domain.user.dto.response.SignUpResDto;
import com.example.oneulnail.domain.user.entity.RefreshToken;
import com.example.oneulnail.domain.user.entity.Status;
import com.example.oneulnail.domain.user.entity.User;
import com.example.oneulnail.domain.user.mapper.SignMapper;
import com.example.oneulnail.domain.user.repository.RedisRepository;
import com.example.oneulnail.domain.user.repository.UserRepository;

import com.example.oneulnail.global.config.security.jwt.service.JwtService;
import com.example.oneulnail.global.exception.BadRequestException;
import com.example.oneulnail.global.exception.UnauthorizedException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;


import java.util.Optional;

import static com.example.oneulnail.global.constants.BaseResponseStatus.*;

@Service
@RequiredArgsConstructor
public class SignService {
    private final Logger LOGGER = LoggerFactory.getLogger(SignService.class);

    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final RedisRepository redisRepository;
    private final PasswordEncoder passwordEncoder;
    private final SignMapper signMapper;

    @Transactional
    public SignUpResDto signUp(SignUpReqDto signUpReqDto) {
        if(userRepository.findByEmail(signUpReqDto.getEmail()).isPresent()){
            throw new BadRequestException(USERS_EXISTS_EMAIL);
        }
        User newUser = buildUser(signUpReqDto);

       User savedUser = userRepository.save(newUser);

        return signMapper.signUpEntityToDto();
    }

    @Transactional(readOnly = true)
    public SignInResDto signIn(String email, String password) throws RuntimeException {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new BadRequestException(USER_NOT_FOUND));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new BadRequestException(FAILED_TO_PASSWORD);
        }

        SignInResDto token = jwtService.createToken(email); //atk,rtk 생성
        redisRepository.save(RefreshToken.builder() //로그인 시, RefreshToken 업데이트
                .id(email)
                .refreshToken(token.getRefreshToken())
                .build());

        return token;
    }

    private User buildUser(SignUpReqDto signUpReqDto){
        return User.builder()
                .email(signUpReqDto.getEmail())
                .phoneNum(signUpReqDto.getPhoneNum())
                .password(passwordEncoder.encode(signUpReqDto.getPassword()))
                .name(signUpReqDto.getName())
                .role(signUpReqDto.getRole())
                .status(Status.ENABLED)
                .build();
    }

    public SignInResDto reissue(HttpServletRequest httpServletRequest) {
        String refreshToken = jwtService.resolveRefreshToken(httpServletRequest);

        return Optional.ofNullable(refreshToken)
                .map(redisRepository::findByRefreshToken) // Redis에서 RefreshToken 찾기
                .map(foundRefreshToken -> jwtService.createAccessToken(foundRefreshToken.getId())) // Redis 에 저장된 RefreshToken 정보를 기반으로 AccessToken 생성
                .map(accessToken -> signMapper.signInAccessEntityToDto(accessToken)) // 매핑
                .orElseThrow(() -> new UnauthorizedException(ForbiddenException));
    }
}