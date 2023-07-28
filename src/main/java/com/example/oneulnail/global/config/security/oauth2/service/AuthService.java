package com.example.oneulnail.global.config.security.oauth2.service;

import com.example.oneulnail.domain.user.entity.User;
import com.example.oneulnail.domain.user.repository.UserRepository;
import com.example.oneulnail.global.config.security.jwt.service.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final HttpServletRequest request;

    @Transactional(readOnly = true)
    public User getLoginUser() {
        log.info("어노테이션 실행");
        String accessToken = jwtService.extractAccessToken(request).orElseThrow();
        String email = jwtService.extractEmail(accessToken).orElseThrow();

        return userRepository.findByEmail(email).orElseThrow();
    }
}

