package com.example.oneulnail.domain.oauth2.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OAuth2SignUpResDto {
    private String name;
    private String phoneNum;
}
