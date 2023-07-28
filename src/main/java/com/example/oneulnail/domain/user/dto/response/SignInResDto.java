package com.example.oneulnail.domain.user.dto.response;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SignInResDto {

    private String msg;
    private String accessToken;
    private String refreshToken;
}
