package com.profitkey.stock.config;

import com.profitkey.stock.dto.common.RequestMatcherPaths;
import com.profitkey.stock.jwt.CustomOAuth2UserService;
import com.profitkey.stock.jwt.JwtAuthenticationFilter;
import com.profitkey.stock.jwt.JwtProvider;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	private final JwtProvider jwtProvider;
	private final CustomOAuth2UserService customOAuth2UserService;

	// JwtProvider와 CustomOAuth2UserService 주입
	public SecurityConfig(JwtProvider jwtProvider, CustomOAuth2UserService customOAuth2UserService) {
		this.jwtProvider = jwtProvider;
		this.customOAuth2UserService = customOAuth2UserService;
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
			.cors(cors -> cors.configurationSource(corsConfigurationSource()))  // CORS 설정
			.csrf(csrf -> csrf.disable())  // CSRF 비활성화
			.authorizeHttpRequests(auth -> auth
				.requestMatchers(RequestMatcherPaths.PERMIT_ALL_PATHS)
				.permitAll()  // permitAllPaths는 누구나 접근 가능
				.requestMatchers(RequestMatcherPaths.AUTHENTICATED_PATHS)
				.authenticated()  // authenticatedPaths는 인증된 사용자만
				.anyRequest()
				.authenticated()  // 나머지는 인증된 사용자만
			)
			.formLogin(form -> form
				.defaultSuccessUrl("/", true)
				.permitAll()
			)
			.logout(logout -> logout.permitAll())
			.oauth2Login(oauth2 -> oauth2
				.userInfoEndpoint(userInfo -> userInfo
					.userService(customOAuth2UserService))  // OAuth2 로그인 후 사용자 정보 처리
				.permitAll()
			)

			// JWT Authentication Filter 추가
			.addFilterBefore(new JwtAuthenticationFilter(jwtProvider), UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}

	@Bean
	public UrlBasedCorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOrigins(List.of("http://localhost:3000"));
		configuration.setAllowedHeaders(List.of("*"));
		configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
		configuration.setAllowCredentials(true);
		configuration.setMaxAge(3600L);

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}
}



