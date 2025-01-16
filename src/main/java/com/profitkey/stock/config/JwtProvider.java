package com.profitkey.stock.config;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;  // 변경된 부분
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtProvider {

    @Value("${jwt.secret}")
    private String secretKey;

    private long validityInMilliseconds = 3600000; // 1시간

    // 토큰 생성
    public String createToken(Authentication authentication) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + validityInMilliseconds);

        return Jwts.builder()
                .setSubject(authentication.getName())  // 사용자명
                .setIssuedAt(now)  // 발급 시간
                .setExpiration(validity)  // 만료 시간
                .signWith(SignatureAlgorithm.HS256, secretKey)  // 서명: HS256 사용
                .compact();
    }

    // 인증 정보 추출
    public Authentication getAuthentication(String token) {
        String userName = getUserName(token);  // 토큰에서 사용자 이름 추출
        return new UsernamePasswordAuthenticationToken(userName, "", null);  // 인증 객체 생성
    }

    // Authorization 헤더에서 토큰 추출
    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");  // 헤더에서 Bearer 토큰 추출
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);  // Bearer 접두어 제거 후 토큰 반환
        }
        return null;
    }

    // 토큰 유효성 검사
    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);  // 토큰 유효성 검사
            return true;
        } catch (Exception e) {  // JwtException, IllegalArgumentException 처리를 한 번에
            return false;
        }
    }

    // 토큰에서 사용자명 추출
    private String getUserName(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();  // 토큰에서 사용자명 추출
    }

    // 토큰 갱신
    public String refreshToken(String oldToken) {
        if (validateToken(oldToken)) {
            String userName = getUserName(oldToken);
            Authentication authentication = new UsernamePasswordAuthenticationToken(userName, "", null);
            return createToken(authentication);  // 새로운 토큰 반환
        }
        return null;
    }
}
