package com.example.oneulnail.domain.user.dto.request;

import com.example.oneulnail.global.config.security.oauth2.entity.Role;
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