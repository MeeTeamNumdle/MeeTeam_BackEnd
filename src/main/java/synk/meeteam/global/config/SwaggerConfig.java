package synk.meeteam.global.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.security.SecuritySchemes;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.tags.Tag;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @io.swagger.v3.oas.annotations.info.Info(title = "MEETEAM",
                description = "MEETEAM SERVER API Documentation",
                version = "v1"),
        security = {
                @SecurityRequirement(name = "Authorization"),
                @SecurityRequirement(name = "Authorization-refresh")
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
    private String SERVER_DOMAIN;

    public SwaggerConfig(@Value("${domain.server-domain}") String serverDomain) {
        SERVER_DOMAIN = serverDomain;
    }

    @Bean
    public OpenAPI openAPI() {
        io.swagger.v3.oas.models.servers.Server devServer = new io.swagger.v3.oas.models.servers.Server();
        devServer.setDescription("dev server");
        devServer.setUrl(SERVER_DOMAIN);

        io.swagger.v3.oas.models.servers.Server localServer = new io.swagger.v3.oas.models.servers.Server();
        localServer.setDescription("local server");
        localServer.setUrl("http://localhost:5173");

        OpenAPI info = new OpenAPI()
                .components(new Components())
                .info(apiInfo());

        info.setServers(Arrays.asList(devServer, localServer));

        return info;
    }

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("Your API").version("1.0.0"))
                .tags(List.of(
                                new Tag().name("recruitment").description("구인 관련 API"),
                                new Tag().name("comment").description("댓글 관련 API"),
                                new Tag().name("applicant").description("신청자 관련 API")
                        )
                );
    }

    private Info apiInfo() {
        return new Info()
                .title("Springdoc 테스트")
                .description("Springdoc을 사용한 Swagger UI 테스트")
                .version("1.0.0");
    }
}
