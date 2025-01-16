package com.profitkey.stock.dto;

public class RequestMatcherPaths {
    public static final String[] PERMIT_ALL_PATHS = {
            "/login",  // 로그인
            "/images/**",  // 이미지 파일
            "/js/**",  // JS 파일
            "/swagger-ui.html",  // Swagger UI
            "/swagger-ui/**",  // Swagger UI 관련
            "/v3/api-docs/**",  // Swagger Docs
            "/v3/api-docs.yaml",  // Swagger Docs
            "/swagger-resources/**",  // Swagger 자원
            "/webjars/**",  // 웹 자원
            "/swagger-ui/**",
                                                        "/v3/api-docs/**",
                                                        "/api/**"
    };

    public static final String[] AUTHENTICATED_PATHS = {
            "/api/board/**",
            "/api/social/**"
    };
}