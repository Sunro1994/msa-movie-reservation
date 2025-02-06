package com.sparta.cloud.movie_reservation_gateway;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.crypto.SecretKey;

@Component
@Slf4j
public class LocalJwtAuthenticationFilter implements GlobalFilter,Ordered {

    @Value("${service.jwt.secret-key}")
    private String secretKey;


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();

        if(request.getURI().toString().contains("/users/signUp") || request.getURI().toString().contains("/users/signIn")) {
            return chain.filter(exchange);
        }

        String authHeader = request.getHeaders().getFirst("Authorization");

        log.info("token = {} " , authHeader);
        //token 추출
        String accessToken = extractToken(authHeader);
        log.info("token = {} " , accessToken);

        //token 검증
        if (accessToken == null || !validateToken(exchange, accessToken)) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        return chain.filter(exchange);
    }

    private boolean validateToken(ServerWebExchange exchange, String token) {
        try {
            SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(secretKey));
            Jws<Claims> claimsJws = Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token) ;

            Claims claims = claimsJws.getBody();
            log.info("claims :: " + claims);

            ServerHttpRequest serverHttpRequest = exchange.getRequest().mutate()
                    .header("X-User-Id", claims.get("user_id").toString())
                    .header("X-Role", claims.get("role").toString())
                    .build();

            exchange.mutate().request(serverHttpRequest).build();

            // 요청을 전달하기 전에 헤더를 확인하기 위해 로그를 찍음
            exchange.getRequest().getHeaders().forEach((target, value) ->
                    log.info("Header Key: {} , Value: {}", target, value)
            );

            // 추가적인 검증 로직 (예: 토큰 만료 여부 확인 등)을 여기에 추가할 수 있습니다.
            log.info("검증 완료");
            return true;
        } catch (Exception e) {
            log.info(e.getMessage());
            return false;
        }
    }

    private String extractToken(String authHeader) {
        if(authHeader == null || !authHeader.startsWith("Bearer ")) {
            return null;
        }
        return authHeader.substring(7);
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
