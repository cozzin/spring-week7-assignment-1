package com.codesoom.utils;

import com.codesoom.assignment.errors.InvalidTokenException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;

/**
 * 토큰 관련 처리를 하는 클래스
 */
@Component
public class JwtUtil {
    private final Key key;

    public JwtUtil(@Value("${jwt.secret}") String secret) {
        key = Keys.hmacShaKeyFor(secret.getBytes());
    }

    // TODO: 토큰을 반환해야 한다.
    /**
     * userId로 생성한 토큰을 반환한다.
     *
     * @param userId 토큰을 생성할 id
     * @return 생성된 토큰
     */
    public String encodeUserId(Long userId) {
        return Jwts.builder()
                .claim("userId", userId)
                .signWith(key)
                .compact();
    }

    // TODO: 토큰을 디코딩해야 한다.
    /**
     * 토큰 디코딩을 통해 얻어낸 userId 정보가 들어있는 Claims를 반환한다.
     *
     * @param token 디코딩할 토큰
     * @return userId 정보가 들어있는 Claims
     */
    public Claims decodeToken(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (SignatureException e) {
            throw new InvalidTokenException(token);
        }
    }
}
