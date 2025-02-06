package com.profitkey.stock.controller;

import com.profitkey.stock.entity.RefreshTokenEntity;
import com.profitkey.stock.jwt.JwtProvider;
import com.profitkey.stock.repository.user.RefreshTokenRepository;
import com.profitkey.stock.service.CustomOAuth2UserService;
import com.profitkey.stock.service.UserService;
import com.profitkey.stock.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/oauth2")
@RequiredArgsConstructor
@Slf4j
public class AuthController {
    private final UserService userService;
    private final CustomOAuth2UserService customOAuth2UserService;
    private final JwtProvider jwtProvider;
    private final JwtUtil jwtUtil;
    private final RefreshTokenRepository refreshTokenRepository;

    @GetMapping("/home")
    public String home(@AuthenticationPrincipal OAuth2User oauth2User) {
        String nickname = oauth2User.getAttribute("nickname");
        log.info("nickname : {}", nickname);
        return nickname;
    }

    @PostMapping("/refresh")
    public String refreshToken(@RequestParam String email, @RequestParam String refreshToken) {
        Optional<RefreshTokenEntity> savedToken = refreshTokenRepository.findByEmail(email);

        if (savedToken.isPresent() && savedToken.get().getRefreshToken().equals(refreshToken)) {
            return jwtUtil.generateToken(email);
        }

        throw new RuntimeException("Invalid refresh token");
    }
}
