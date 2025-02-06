package com.profitkey.stock.service;

import com.profitkey.stock.entity.AuthProvider;
import com.profitkey.stock.entity.User;
import com.profitkey.stock.repository.user.UserRepository;
import com.profitkey.stock.util.JwtUtil;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final KakaoOAuth2Service kakaoOAuth2Service;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    public User oAuthLogin(String code, HttpServletResponse response) {
        String accessToken = kakaoOAuth2Service.getAccessToken(code);
        Map<String, Object> userInfo = kakaoOAuth2Service.getUserInfo(accessToken);

        String email = (String) userInfo.get("email");
        String nickname = (String) userInfo.get("nickname");

        Optional<User> userOptional = userRepository.findByEmail(email);
        User user = userOptional.orElseGet(() -> {
            User newUser = new User(email, AuthProvider.KAKAO, accessToken, nickname);
            return userRepository.save(newUser);
        });
        
        String jwtToken = jwtUtil.generateToken(user.getEmail());
        response.setHeader("Authorization", "Bearer " + jwtToken);

        return user;
    }
}
