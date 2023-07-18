package com.example.oneulnail.global.config.security.oauth2.service;

import com.example.oneulnail.domain.user.entity.User;
import com.example.oneulnail.domain.user.repository.UserRepository;
import com.example.oneulnail.global.config.security.jwt.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtService jwtService;
    private final UserRepository userRepository;

    public String extractEmailFromJwt(HttpServletRequest request) {
        String accessToken = jwtService.extractAccessToken(request).orElseThrow();
        return jwtService.extractEmail(accessToken).orElseThrow();
    }

    @Transactional(readOnly = true)
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));
    }
}

