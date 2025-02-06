package com.profitkey.stock.jwt;

import com.profitkey.stock.entity.AuthProvider;
import com.profitkey.stock.entity.User;
import com.profitkey.stock.service.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@RequiredArgsConstructor
@Component
@Slf4j
public class JwtProvider {
    //JWT 토큰 생성/검증/파싱

    @Value("${jwt.secret}")
    private String secretKey;

    private final long validityInMilliseconds = 3600000; // 1시간
    private final UserService userService;

    // 토큰 생성
    public String createToken(User user) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + validityInMilliseconds);
        log.info("mindotori log user nickname : {}", user.getNickname());
        log.info("mindotori log user provider : {}", user.getProvider().toString());

        // JWT 토큰 생성
        return Jwts.builder()
                .setSubject(user.getEmail()) // 사용자 이메일을 subject로 설정
                .claim("nickname", user.getNickname()) // 추가 클레임으로 닉네임 포함
                .claim("provider", user.getProvider().toString()) // 추가 클레임으로 소셜 제공자 포함
                .setIssuedAt(now) // 발급 시간
                .setExpiration(validity) // 만료 시간
                .signWith(SignatureAlgorithm.HS256, secretKey) // Base64 디코딩 없이 평문 그대로 사용
                .compact();
    }

    public String refreshToken(String oldToken, User user) {
        log.info("Attempting to refresh token for user: {}", user.getEmail());

        if (validateToken(oldToken)) {
            log.info("Old token is valid. Generating new token...");
            String newToken = createToken(user);
            log.info("New Refresh Token: {}", newToken);
            return newToken;
        }

        log.warn("Old token is invalid, cannot refresh.");
        return null;
    }

    // 인증 정보 추출
    public Authentication getAuthentication(String token) {
        String email = getEmailFromToken(token); // 토큰에서 이메일 추출
        User user = userService.findOrCreateUser(email, token, "", AuthProvider.KAKAO);
        return new UsernamePasswordAuthenticationToken(user, "", null); // 인증 객체 생성
    }

    // Authorization 헤더에서 토큰 추출
    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    // 토큰 유효성 검사
    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            log.error("Token validation failed: {}", e.getMessage());
            return false;
        }
    }

    // 토큰에서 이메일 추출
    private String getEmailFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody()
                .getSubject(); // subject로 이메일을 설정했으므로 이메일 반환
    }

}
