package com.profitkey.stock.controller;

import com.profitkey.stock.entity.RefreshTokenEntity;
import com.profitkey.stock.entity.User;
import com.profitkey.stock.repository.user.RefreshTokenRepository;
import com.profitkey.stock.service.AuthService;
import com.profitkey.stock.util.JwtUtil;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/oauth2")
@RequiredArgsConstructor
@Slf4j
public class AuthController {
    private final AuthService authService;
    private final JwtUtil jwtUtil;
    private final RefreshTokenRepository refreshTokenRepository;

    @GetMapping("/login/kakao")
    public ResponseEntity<?> kakaoLogin(@RequestParam("code") String accessCode, HttpServletResponse httpServletResponse) {
        User user = authService.oAuthLogin(accessCode, httpServletResponse);
        return ResponseEntity.ok(user);
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
