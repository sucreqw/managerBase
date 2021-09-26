package com.common.manager.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@EnableSwagger2
@Configuration
@Profile({"dev", "test"})
public class Swagger2Config {

    @Bean
    public Docket api() {
        AuthorizationScope authorizationScope = new AuthorizationScope("jwt_token", "X-Auth");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[]{authorizationScope};
        return new Docket(DocumentationType.SWAGGER_2)
                .securitySchemes(Collections.singletonList(new ApiKey("jwt_token", "X-Auth", "header")))
                .securityContexts(Collections.singletonList(SecurityContext.builder().securityReferences(Collections.singletonList(new SecurityReference("jwt_token", authorizationScopes))).forPaths(PathSelectors.any()).build()))
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build();
    }
}
