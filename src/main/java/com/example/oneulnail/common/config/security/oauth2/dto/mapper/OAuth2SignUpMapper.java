package com.example.oneulnail.common.config.security.oauth2.dto.mapper;

import com.example.oneulnail.common.config.security.oauth2.dto.OAuth2SignUpResDto;
import com.example.oneulnail.user.entity.User;
import org.springframework.stereotype.Component;

@Component
public class OAuth2SignUpMapper {
    public OAuth2SignUpResDto entityToDto(User user) {
        return OAuth2SignUpResDto.builder()
                .name(user.getName())
                .phoneNum(user.getPhoneNum())
                .build();
    }
}
