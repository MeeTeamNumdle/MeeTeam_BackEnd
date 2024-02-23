package synk.meeteam.global.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.security.SecuritySchemes;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @io.swagger.v3.oas.annotations.info.Info(title = "MEETEAM",
                description = "MEETEAM SERVER API Documentation",
                version = "v1"),
        security = {
                @SecurityRequirement(name = "Authorization"),
                @SecurityRequirement(name = "Authorization-refresh")
        },
        servers = {
                @Server(url = "http://localhost:8080", description = "local server"),
                @Server(url = "https://api.meeteam.com", description = "dev server")
        })
@SecuritySchemes({
        @SecurityScheme(name = "Authorization",
                type = SecuritySchemeType.APIKEY,
                description = "access token",
                in = SecuritySchemeIn.HEADER,
                paramName = "Authorization"),
        @SecurityScheme(name = "Authorization-refresh",
                type = SecuritySchemeType.APIKEY,
                description = "refresh token",
                in = SecuritySchemeIn.HEADER,
                paramName = "Authorization-refresh"),
})
@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .components(new Components())
                .info(apiInfo());
    }

    private Info apiInfo() {
        return new Info()
                .title("Springdoc 테스트")
                .description("Springdoc을 사용한 Swagger UI 테스트")
                .version("1.0.0");
    }
}
