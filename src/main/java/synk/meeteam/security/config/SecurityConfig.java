package synk.meeteam.security.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer.FrameOptionsConfig;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import synk.meeteam.security.filter.JwtAuthenticationFilter;
import synk.meeteam.security.filter.JwtExceptionFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private static final String[] AUTH_WHITELIST = {
            "/", "/error", "/webjars/**",

            // Swagger
            "/swagger-resources/**",
            "/swagger-ui/**",
            "/v3/api-docs/**",
            "/webjars/**",
            "swagger-ui/github-markdown-css.",

            // Authentication
            "/auth/**", "/login/**", "/authTest", "/user/search/check-duplicate", "/university", "/department",

            // client
            "/", "/css/**", "/images/**", "/js/**", "/favicon.ico", "/h2-console/**", "/actuator/health",

            "/skill/**", "/tag/**", "/role/**",
            "/user/encrypt/**"
    };

    private static final String[] SEMI_AUTH_WHITELIST = {
            // 꼭 GET만 가능해야 하는 리스트
            "/recruitment/postings/{id}",
            "/recruitment/postings/search",
            "/user/profile/**"
    };

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final JwtExceptionFilter jwtExceptionFilter;


    //cors 에러를 대응하기 위한 메소드
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("https://meeteam.co.kr", "http://localhost:5173", "http://localhost:8080")
                        .allowedOriginPatterns("https://meeteam.co.kr", "http://localhost:5173",
                                "http://localhost:8080")
                        .allowedMethods(
                                HttpMethod.GET.name(),
                                HttpMethod.POST.name(),
                                HttpMethod.PUT.name(),
                                HttpMethod.PATCH.name(),
                                HttpMethod.DELETE.name()
                        );
            }
        };
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> {
                    session.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                })
                .headers((headerConfig) ->
                        headerConfig.frameOptions(FrameOptionsConfig::disable)
                )
        //  .userDetailsService(memberAuthService)
        ;

        //== URL별 권한 관리 옵션 ==//
        http.authorizeHttpRequests(auth -> {
                    auth.requestMatchers(AUTH_WHITELIST).permitAll();
                    auth.requestMatchers(HttpMethod.GET, SEMI_AUTH_WHITELIST).permitAll();
                    auth.anyRequest().authenticated();
                })
                // 원래 스프링 시큐리티 필터 순서가 LogoutFilter 이후에 로그인 필터 동작
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(jwtExceptionFilter, JwtAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }


}
