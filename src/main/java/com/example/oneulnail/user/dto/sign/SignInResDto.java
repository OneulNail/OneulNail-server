package com.example.oneulnail.user.dto.sign;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class SignInResDto extends SignUpResDto{

    private String token;

    @Builder
    public SignInResDto(boolean success, String code, String msg, String token) {
        super(success, code, msg);
        this.token = token;
    }
}
