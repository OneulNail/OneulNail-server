package com.example.oneulnail.user.dto.sign;

import com.example.oneulnail.common.config.security.oauth2.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SignUpReqDto {

    private String phone_num;

    private String password;

    private String name;

    private Role role;

}