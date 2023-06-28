package shop.cazait.global.config.openApi;


import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.security.SecurityScheme.Type;
import io.swagger.v3.oas.models.servers.Server;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springdoc.core.GroupedOpenApi;
import org.springdoc.core.customizers.OpenApiCustomiser;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springdoc.core.filters.OpenApiMethodFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import shop.cazait.global.config.encrypt.NoAuth;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI openAPI() {

        Server devServer = new Server();
        devServer.setDescription("dev");
        devServer.setUrl("https://cazait.shop");

        Server localServer = new Server();
        localServer.setDescription("local");
        localServer.setUrl("http://localhost:8082");

        return new OpenAPI()
                .info(getInfo())
                .servers(Arrays.asList(devServer, localServer));

    }

    private Info getInfo() {
        return new Info()
                .title("CaZaIt REST API")
                .description("CaZaIt API DOCS");
    }

    //토큰 인증이 필요한 API 모음
    @Bean
    public GroupedOpenApi SecurityGroupOpenApi() {
        return GroupedOpenApi
                .builder()
                .group("토큰 인증 필요한 API")
                .addOpenApiCustomiser(buildSecurityOpenApi())
                .addOpenApiMethodFilter(authFilter)
                .pathsToExclude("/api/auths/refresh")
                .build();
    }

    //토큰 인증이 필요 없는 API 모음
    @Bean
    public GroupedOpenApi NonSecurityGroupOpenApi() {
        return GroupedOpenApi
                .builder()
                .group("토큰 인증 불필요한 API")
                .addOpenApiMethodFilter(noAuthFilter)
                .pathsToExclude("/api/auths/refresh")
                .build();
    }

    //토큰 재발급 API만 따로 분류
    @Bean
    public GroupedOpenApi refreshTokenAPI() {
        return GroupedOpenApi
                .builder()
                .group("토큰 재발급")
                .addOpenApiCustomiser(buildSecurityOpenApi())
                .pathsToMatch("/api/auths/refresh")
                .build();
    }

    public OpenApiCustomiser buildSecurityOpenApi() {
        SecurityScheme securityScheme = new SecurityScheme()
                .name("Authorization")
                .type(SecurityScheme.Type.HTTP)
                .in(SecurityScheme.In.HEADER)
                .bearerFormat("JWT")
                .scheme("bearer");

        return OpenApi -> OpenApi
                .addSecurityItem(new SecurityRequirement().addList("Authorization"))
                .getComponents().addSecuritySchemes("Authorization", securityScheme);
    }

    //NoAuth가 존재하는 API들을 필터링합니다 (토큰 인증 필요 x)
    OpenApiMethodFilter noAuthFilter = ((method)-> {
        if (method.getAnnotation(NoAuth.class) != null)
            return true;
        return false;
        }
    );

    //NoAuth가 존재하지 않는 API들을 필터링합니다 (토큰 인증 필요)
    OpenApiMethodFilter authFilter = ((method)-> {
        if (method.getAnnotation(NoAuth.class) == null)
            return true;
        return false;
        }
    );

}




