package com.example.oneulnail.domain.oauth2.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OAuth2SignUpReqDto {
    private String phoneNum;
    private String name;
}
