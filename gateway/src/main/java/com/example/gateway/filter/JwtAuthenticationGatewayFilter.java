package com.example.gateway.filter;

import com.example.gateway.redis.repository.AccessTokenRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.security.Key;

@Component
public class JwtAuthenticationGatewayFilter extends AbstractGatewayFilterFactory<JwtAuthenticationGatewayFilter.Config> {
    private final Key key;
    private final AccessTokenRepository accessTokenRepository;

    @Autowired
    public JwtAuthenticationGatewayFilter(
            @Value("${jwt.secret}") String secretKey,
            AccessTokenRepository accessTokenRepository
    ) {
        super(Config.class);
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
        this.accessTokenRepository = accessTokenRepository;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            String path = exchange.getRequest().getURI().getPath();

            if (isExcludedPath(path)) {
                return chain.filter(exchange);
            }

            String authorizationHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                String token = authorizationHeader.substring(7);
                try {
                    Claims claims = Jwts.parserBuilder()
                            .setSigningKey(key)
                            .build()
                            .parseClaimsJws(token)
                            .getBody();

                    accessTokenRepository.findById(
                            claims.get("deviceId").toString()
                    ).orElseThrow();

                    exchange.getRequest().mutate()
                            .header("X-User-Id", claims.get("userId").toString())
                            .build();

                } catch (Exception e) {
                    return Mono.error(new RuntimeException("Invalid Token"));
                }
            }

            return chain.filter(exchange);
        };
    }

    private boolean isExcludedPath(String path) {

        if (path.startsWith("/user")) {
            path = path.replaceFirst("/user", "");

            if (path.equals("/verifycode") || path.equals("/verify") ||
                    path.equals("/signup") || path.equals("/signin")) {
                return true;
            }
        } else if (path.startsWith("/clothes")) {
            return true;
        }

        return false;
    }
    public static class Config {

    }
}