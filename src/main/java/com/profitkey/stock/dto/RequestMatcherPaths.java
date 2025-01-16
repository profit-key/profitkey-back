package com.profitkey.stock.dto;

public class RequestMatcherPaths {
    public static final String[] PERMIT_ALL_PATHS = {   "/swagger-ui/**",
                                                        "/v3/api-docs/**",
                                                        "/api/**"
    };

    public static final String[] AUTHENTICATED_PATHS = {"/api/social/**"
    };
}
리