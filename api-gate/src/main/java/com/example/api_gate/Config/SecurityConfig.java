package com.example.api_gate.Config;

import Security.JwtAuthenticationFilter;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.HttpStatusServerEntryPoint;
import org.springframework.security.web.server.authorization.HttpStatusServerAccessDeniedHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        return http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .formLogin(ServerHttpSecurity.FormLoginSpec::disable)
                .httpBasic(ServerHttpSecurity.HttpBasicSpec::disable)
                .authorizeExchange(exchanges -> exchanges
                // Allow preflight OPTIONS requests
                .pathMatchers(HttpMethod.OPTIONS).permitAll()
                .pathMatchers(
                        "/swagger-ui/**",
                        "/v3/api-docs/**",
                        "/api/v1/usermanagement/member/auth/**",
                        "/api/v1/usermanagement/admin/auth/**",
                        "/"
                ).permitAll()
                .pathMatchers("/api/v1/usermanagement/admin/**",
                                "/api/v1/reportmanagement/**")
                .hasAnyAuthority("ROLE_ADMINISTRATOR")
                .anyExchange().authenticated()
                )
                .exceptionHandling(handling -> handling
                .authenticationEntryPoint(httpStatusServerEntryPoint())
                .accessDeniedHandler(httpStatusServerAccessDeniedHandler())
                )
                .addFilterAt(jwtAuthenticationFilter(), SecurityWebFiltersOrder.AUTHENTICATION)
                .build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        // Allow your Vercel domain and any ngrok subdomain.
        // Adjust the patterns to match your actual deployed domains.
        config.setAllowedOriginPatterns(List.of(
                "https://adminso-plusplus.vercel.app",
                "https://adminso-plusplus-*.vercel.app",
                "https://*.serveo.net",
                "http://localhost:*",
                "https://*.ngrok-free.app"
        ));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS","PATCH"));
        config.setAllowedHeaders(List.of("*"));
        config.setExposedHeaders(List.of("Authorization"));
        // Allow credentials if needed (set true if using cookies/credentials)
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter();
    }

    @Bean
    public HttpStatusServerEntryPoint httpStatusServerEntryPoint() {
        return new HttpStatusServerEntryPoint(HttpStatus.UNAUTHORIZED);
    }

    @Bean
    public HttpStatusServerAccessDeniedHandler httpStatusServerAccessDeniedHandler() {
        return new HttpStatusServerAccessDeniedHandler(HttpStatus.FORBIDDEN);
    }
}
