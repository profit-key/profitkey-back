package com.profitkey.stock.config;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) {
        // 구글 로그인에 대한 처리
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oauth2User = delegate.loadUser(userRequest);

        // 사용자 정보 추출 (예: 이메일, 이름 등)
        Map<String, Object> attributes = oauth2User.getAttributes();
        String email = (String) attributes.get("email");
        String name = (String) attributes.get("name");

        // 추가적인 사용자 정보 처리, 예: DB에 저장 등
        Map<String, Object> customAttributes = new HashMap<>();
        customAttributes.put("email", email);
        customAttributes.put("name", name);

        // 사용자 정보를 커스터마이즈하여 반환
        return new DefaultOAuth2User(oauth2User.getAuthorities(), customAttributes, "name");
    }
}
