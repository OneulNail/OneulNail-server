package com.example.oneulnail.domain.oauth2.service;

import com.example.oneulnail.domain.oauth2.dto.OAuth2SignUpReqDto;
import com.example.oneulnail.domain.oauth2.dto.OAuth2SignUpResDto;
import com.example.oneulnail.domain.oauth2.mapper.OAuth2SignUpMapper;
import com.example.oneulnail.domain.user.entity.User;
import com.example.oneulnail.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class OAuth2SignUpService {

    private final UserRepository userRepository;
    private final OAuth2SignUpMapper mapper;

    @Transactional
    public OAuth2SignUpResDto signUp(User foundUser, OAuth2SignUpReqDto oAuth2SignUpReqDto) {
        updateUserWithOAuth2SignUpInfo(foundUser, oAuth2SignUpReqDto);
        User savedUser = userRepository.save(foundUser);

        return mapper.entityToDto(savedUser);
    }

    private void updateUserWithOAuth2SignUpInfo(User user, OAuth2SignUpReqDto oAuth2SignUpReqDto) {
        user.oAuth2SignUp(oAuth2SignUpReqDto.getPhoneNum(), oAuth2SignUpReqDto.getName());
    }
}

