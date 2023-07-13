package com.example.oneulnail.domain.user.dto.sign;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class SignInResDto extends SignUpResDto{

    private String token;

    @Builder
    public SignInResDto(String msg, String token) {
        super(msg);
        this.token = token;
    }
}
