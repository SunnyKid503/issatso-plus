package Security;

import com.example.api_gate.utils.JwtTokenUtil;
import io.jsonwebtoken.ExpiredJwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
@Component
public class JwtAuthenticationFilter implements WebFilter {

    private static final String BEARER_PREFIX = "Bearer ";
    private static final String AUTH_ERROR_HEADER = "X-Authentication-Error";
    private static final Logger logger  =LoggerFactory.getLogger(JwtAuthenticationFilter.class);
    ;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String token = extractToken(request);

        if (token == null) {
            return chain.filter(exchange);
        }

        try {
            if (!jwtTokenUtil.validateToken(token)) {
                return handleError(exchange.getResponse(),
                        "Invalid or expired token",
                        HttpStatus.FORBIDDEN);
            }

            Authentication authentication = createAuthentication(token);
            return chain.filter(exchange)
                    .contextWrite(ReactiveSecurityContextHolder.withAuthentication(authentication));

        } catch (ExpiredJwtException ex) {
            return handleError(exchange.getResponse(),
                    "Token expired: " + ex.getMessage(),
                    HttpStatus.FORBIDDEN);

        } catch (Exception ex) {
            return handleError(exchange.getResponse(),
                    "Authentication error: " + ex.getMessage(),
                    HttpStatus.UNAUTHORIZED);
        }
    }

    private String extractToken(ServerHttpRequest request) {
        String authHeader = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if (authHeader != null && authHeader.startsWith(BEARER_PREFIX)) {
            return authHeader.substring(BEARER_PREFIX.length());
        }
        return null;
    }

    private Authentication createAuthentication(String token) {
        String username = jwtTokenUtil.getUsernameFromToken(token);
        Set<String> roles = jwtTokenUtil.getRolesFromToken(token);
        logger.info("Authenticating user: {}", username);
        logger.info("Roles in token: {}", roles);
        validateTokenType(token);

        List<GrantedAuthority> authorities = roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        return new UsernamePasswordAuthenticationToken(username, null, authorities);
    }

    private void validateTokenType(String token) {
        String tokenType = jwtTokenUtil.getTokenTypeFromToken(token);
        if (!"ACCESS".equalsIgnoreCase(tokenType)) {
            throw new SecurityException("Invalid token type. Access token required.");
        }
    }

    private Mono<Void> handleError(ServerHttpResponse response, String message, HttpStatus status) {
        response.setStatusCode(status);
        response.getHeaders().add(HttpHeaders.CONTENT_TYPE, "application/problem+json");
        response.getHeaders().add(AUTH_ERROR_HEADER, "true");

        String body = String.format(
                "{\"type\": \"about:blank\", \"title\": \"%s\", \"status\": %d, \"detail\": \"%s\"}",
                status.getReasonPhrase(),
                status.value(),
                message
        );

        return response.writeWith(Mono.just(response.bufferFactory().wrap(body.getBytes())));
    }
}
