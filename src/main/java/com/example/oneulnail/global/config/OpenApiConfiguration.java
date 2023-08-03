package com.example.oneulnail.global.config;


import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@SecurityScheme(
        type = SecuritySchemeType.APIKEY, in = SecuritySchemeIn.HEADER,
        name = "Authorization", description = "Auth Token")
@Configuration
public class OpenApiConfiguration {

    private static final String AUTH_TOKEN_HEADER = "Authorization";

    @Bean
    public GroupedOpenApi api() {
        return GroupedOpenApi.builder()
                .group("api")
                .addOpenApiCustomiser(openApi -> {
                    openApi.info(new Info()
                            .title("오늘네일 API")
                            .description("오늘네일 API입니다.")
                            .version("1.0")
                    );
                    SecurityRequirement securityRequirement = new SecurityRequirement();
                    securityRequirement.addList(AUTH_TOKEN_HEADER);
                    openApi.addSecurityItem(securityRequirement);
                })
                .pathsToMatch("/**")
                .build();
    }
}
