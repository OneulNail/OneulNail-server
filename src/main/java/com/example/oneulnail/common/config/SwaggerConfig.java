package com.example.oneulnail.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.*;

@Configuration
@EnableSwagger2
public class SwaggerConfig {


    private List<SwaggerResourcesProvider> resourcesProviders;

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.OAS_30)
                .useDefaultResponseMessages(true)// Swagger 에서 제공해주는 기본 응답 코드 (200, 401, 403, 404) 등의 노출 여부
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.example.oneulneil"))
                .paths(PathSelectors.any())
                .build();
    }

    private Set<String> getConsumeContentTypes() {
        Set<String> consumes = new HashSet<>();
        consumes.add("application/json;charset=UTF-8");
        consumes.add("application/x-www-form-urlencoded");
        return consumes;
    }

    private Set<String> getProduceContentTypes() {
        Set<String> produces;
        produces = new HashSet<>();
        produces.add("application/json;charset=UTF-8");
        produces.add("application/x-www-form-urlencoded");
        return produces;
    }



    private ApiInfo apiInfo() {
        String description = "OneulNeil SWAGGER API 입니다.";
        return new ApiInfoBuilder()
                .title("OneulNeil API 명세서")
                .description(description)
                .version("1.0")
                .build();
    }



    private SecurityContext securityContext() {
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                .build();
    }


    private List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Arrays.asList(new SecurityReference("X-AUTH-TOKEN", authorizationScopes));
    }


    private ApiKey apiKey() {
        //return new ApiKey("Authorization", "Authorization", "header");
        return new ApiKey("X-AUTH-TOKEN", "Bearer", "header");
    }

}