package com.sparta.cloud.movie_reservation_auth;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class AuthService {


    @Value("${spring.application.name}")
    private String issuer;

    @Value("${service.jwt.access-expiration}")
    private Long accessExpiration;

    @Value("${service.jwt.refresh-expiration}")
    private Long refreshExpiration;

    private final SecretKey secretKey;

    /**
     * AuthService 생성자.
     * Base64 URL 인코딩된 비밀 키를 디코딩하여 HMAC-SHA 알고리즘에 적합한 SecretKey 객체를 생성합니다.
     *
     * @param secretKey Base64 URL 인코딩된 비밀 키
     */
    public AuthService(@Value("${service.jwt.secret-key}") String secretKey
    ) {
        this.secretKey = Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(secretKey));
    }

    /**
     * 사용자 ID를 받아 JWT 액세스 토큰을 생성합니다.
     *
     * @param userId 사용자 ID
     * @return 생성된 JWT 액세스 토큰
     */
    public TokenResponse createToken(UserRequest userRequest) {

        return TokenResponse.builder()
                .accessToken(createAccessToken(userRequest))
                .refreshToken(createRefreshToken(userRequest.getUserId()))
                .build();

    }

    private String createAccessToken(UserRequest userRequest) {
        return Jwts.builder()
                .claim("user_id", userRequest.getUserId())
                .claim("role", userRequest.getRole())
                .issuer(issuer)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + accessExpiration))
                .signWith(secretKey, io.jsonwebtoken.SignatureAlgorithm.HS512)
                .compact();
    }

    public String createRefreshToken(Long userId) {
        return Jwts.builder()
                .claim("user_id", userId)  // ✅ RefreshToken에는 최소 정보만 포함
                .issuer(issuer)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + refreshExpiration))  // ✅ RefreshToken 만료 시간 적용
                .signWith(secretKey, io.jsonwebtoken.SignatureAlgorithm.HS512)
                .compact();
    }
}
