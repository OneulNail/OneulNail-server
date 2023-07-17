package com.example.oneulnail.global.config.security.oauth2.service;

import com.example.oneulnail.global.config.security.oauth2.dto.OAuth2SignUpReqDto;
import com.example.oneulnail.global.config.security.oauth2.dto.OAuth2SignUpResDto;
import com.example.oneulnail.global.config.security.oauth2.mapper.OAuth2SignUpMapper;
import com.example.oneulnail.domain.user.entity.User;
import com.example.oneulnail.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
@RequiredArgsConstructor
public class OAuth2SignUpService {

    private final AuthService authService;
    private final UserRepository userRepository;
    private final OAuth2SignUpMapper mapper;

    public OAuth2SignUpResDto signUp(HttpServletRequest request, OAuth2SignUpReqDto oAuth2SignUpReqDto) {
        String email = authService.extractEmailFromJwt(request);
        User foundUser = authService.findUserByEmail(email);

        updateUserWithOAuth2SignUpInfo(foundUser, oAuth2SignUpReqDto);
        User savedUser = userRepository.save(foundUser);

        return mapper.entityToDto(savedUser);
    }

    private void updateUserWithOAuth2SignUpInfo(User user, OAuth2SignUpReqDto oAuth2SignUpReqDto) {
        user.oAuth2SignUp(oAuth2SignUpReqDto.getPhoneNum(), oAuth2SignUpReqDto.getName());
    }
}

