package com.example.oneulnail.domain.user.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SignInReqDto {
    private String email;
    private String password;
}