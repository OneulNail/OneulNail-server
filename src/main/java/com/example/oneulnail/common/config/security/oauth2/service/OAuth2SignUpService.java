package com.example.oneulnail.common.config.security.oauth2.service;

import com.example.oneulnail.common.config.security.jwt.service.JwtService;
import com.example.oneulnail.common.config.security.oauth2.dto.OAuth2SignUpReqDto;
import com.example.oneulnail.common.config.security.oauth2.dto.OAuth2SignUpResDto;
import com.example.oneulnail.common.config.security.oauth2.dto.mapper.OAuth2SignUpMapper;
import com.example.oneulnail.user.entity.User;
import com.example.oneulnail.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
@RequiredArgsConstructor
public class OAuth2SignUpService {

    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final OAuth2SignUpMapper mapper;

    public OAuth2SignUpResDto signUp(HttpServletRequest request, OAuth2SignUpReqDto oAuth2SignUpReqDto) {
        String accessToken = jwtService.extractAccessToken(request).orElseThrow();
        String email = jwtService.extractEmail(accessToken).orElseThrow();

        User foundUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));
        foundUser.oAuth2SignUp(oAuth2SignUpReqDto.getPhoneNum(), oAuth2SignUpReqDto.getName());
        User savedUser = userRepository.save(foundUser);

        return mapper.entityToDto(savedUser);
    }
}
