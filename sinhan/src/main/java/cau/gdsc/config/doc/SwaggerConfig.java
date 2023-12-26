package cau.gdsc.config.doc;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.service.SecurityScheme;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Collections;
import java.util.List;

@Configuration // 스프링 설정 파일
public class SwaggerConfig {
    @Bean
    public Docket api(){
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("cau.gdsc.controller")) // 현재 RequestMapping으로 할당된 모든 URL 리스트를 추출
                .paths(PathSelectors.any()) // PathSelectors.any() 는 모든 경로를 의미
                .build()
                .securityContexts(Collections.singletonList(securityContext()))
                .securitySchemes(Collections.singletonList(apiKey()));
    }

    private SecurityContext securityContext() {
        return SecurityContext
                .builder()
                .securityReferences(defaultAuth()) // Swagger에서 사용할 인증 정보
                .operationSelector(o -> true)
                .build();
    }

    private List<SecurityReference> defaultAuth() {
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[]{
                new AuthorizationScope("global", "accessEverything") // AuthorizationScope는 Swagger에서 권한을 설정하는 부분
                // 여기서는 global로 설정하여 모든 리소스에 대한 접근 권한을 가지도록 했다.
        };
        return List.of(new SecurityReference("JWT 토큰", authorizationScopes));
    }

    // JWT를 통해 인증을 하는 경우, Swagger에서도 인증을 할 수 있도록 설정
    private ApiKey apiKey() {
        return new ApiKey("JWT 토큰", "Authorization", "header");
    }
}
