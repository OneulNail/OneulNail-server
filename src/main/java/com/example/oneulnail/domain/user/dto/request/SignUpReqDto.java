package com.example.oneulnail.domain.user.dto.request;

import com.example.oneulnail.global.config.security.oauth2.entity.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SignUpReqDto {

    private String email;

    private String phoneNum;

    private String password;

    private String name;

    private Role role;

}