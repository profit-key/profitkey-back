package com.profitkey.stock.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.profitkey.stock.dto.common.RequestMatcherPaths;
import com.profitkey.stock.handler.JwtAuthenticationSuccessHandler;
import com.profitkey.stock.handler.OAuth2SuccessHandler;
import com.profitkey.stock.jwt.CustomOAuth2UserService;
import com.profitkey.stock.jwt.JwtAuthenticationFilter;
import com.profitkey.stock.jwt.JwtProvider;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

	private final JwtProvider jwtProvider;
	private final CustomOAuth2UserService customOAuth2UserService;
	private final JwtAuthenticationSuccessHandler jwtAuthenticationSuccessHandler; // SuccessHandler 주입
	private final OAuth2SuccessHandler oAuth2SuccessHandler;

	@Bean
	public WebSecurityCustomizer webSecurityCustomizer() { // security를 적용하지 않을 리소스
		return web -> web.ignoring()
			// error endpoint를 열어줘야 함, favicon.ico 추가!
			.requestMatchers("/error", "/favicon.ico");
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
			.cors(cors -> cors.configurationSource(corsConfigurationSource()))  // CORS 설정
			.csrf(csrf -> csrf.disable())  // CSRF 비활성화
			.authorizeHttpRequests(auth -> auth
				.requestMatchers(RequestMatcherPaths.PERMIT_ALL_PATHS).permitAll()  // 누구나 접근 가능
				.requestMatchers(RequestMatcherPaths.AUTHENTICATED_PATHS).authenticated()  // 인증 필요
				.anyRequest().authenticated()  // 나머지는 인증된 사용자만
			)
			// oauth2 설정
			.oauth2Login(oauth -> // OAuth2 로그인 기능에 대한 여러 설정의 진입점
				// OAuth2 로그인 성공 이후 사용자 정보를 가져올 때의 설정을 담당
				oauth.userInfoEndpoint(c -> c.userService(customOAuth2UserService))
					// 로그인 성공 시 핸들러
					.successHandler(oAuth2SuccessHandler)
			)
			.logout(AbstractHttpConfigurer::disable) // 로그아웃 허용
			.addFilterBefore(new JwtAuthenticationFilter(jwtProvider),
				UsernamePasswordAuthenticationFilter.class); // JWT 필터 추가

		return http.build();
	}

	@Bean
	public UrlBasedCorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOrigins(List.of("http://localhost:3000")); // CORS 허용 도메인
		configuration.setAllowedHeaders(List.of("*"));
		configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
		configuration.setAllowCredentials(true);
		configuration.setMaxAge(3600L);

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}
}
