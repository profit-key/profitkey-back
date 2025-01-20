package com.profitkey.stock.jwt;

import com.profitkey.stock.jwt.JwtProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.security.core.Authentication;

import java.io.IOException;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;

    public JwtAuthenticationFilter(JwtProvider jwtProvider) {
        this.jwtProvider = jwtProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = jwtProvider.resolveToken(request);  // 헤더에서 JWT 토큰 추출

        if (token != null && jwtProvider.validateToken(token)) {  // 유효한 토큰이면
            Authentication authentication = jwtProvider.getAuthentication(token);  // 인증 객체 생성
            SecurityContextHolder.getContext().setAuthentication(authentication);  // 인증 설정
        }

        filterChain.doFilter(request, response);  // 다음 필터로 넘김
    }
}
