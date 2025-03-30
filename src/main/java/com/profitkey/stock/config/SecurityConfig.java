package com.profitkey.stock.config;

import com.profitkey.stock.dto.common.RequestMatcherPaths;
import com.profitkey.stock.jwt.JwtAuthenticationFilter;
import java.util.List;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class SecurityConfig {

	private final JwtAuthenticationFilter jwtAuthenticationFilter;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.cors(cors -> cors.configurationSource(corsConfigurationSource()))
			.csrf(csrf -> csrf.disable())
			.authorizeHttpRequests(auth -> auth.requestMatchers(RequestMatcherPaths.PERMIT_ALL_PATHS)
				.permitAll()
				.requestMatchers(RequestMatcherPaths.AUTHENTICATED_PATHS)
				.authenticated()
				.anyRequest()
				.authenticated()
			)
			.oauth2Login(oauth2 -> oauth2.disable())
			.logout(logout -> logout.disable())
			.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}

	@Bean
	public UrlBasedCorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOrigins(
			List.of("http://localhost:3000", "http://localhost:5173", "https://dev-server.profitkey.click",
				"https://dev.profitkey.click", "https://profitkey-inky.vercel.app"));
		configuration.setAllowedHeaders(List.of("*"));
		configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
		configuration.setAllowCredentials(true);
		configuration.setMaxAge(3600L);

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}
}
