package com.profitkey.stock.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.profitkey.stock.dto.RequestMatcherPaths;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	//    @Bean
	//    public PasswordEncoder passwordEncoder() {
	//        return new BCryptPasswordEncoder();
	//    }

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.cors(cors -> cors.configurationSource(corsConfigurationSource()))
			.csrf(csrf -> csrf.disable())
			.authorizeHttpRequests(auth -> auth.requestMatchers(RequestMatcherPaths.PERMIT_ALL_PATHS)
					.permitAll()
					.requestMatchers(RequestMatcherPaths.AUTHENTICATED_PATHS)
					.authenticated()
				//                        .anyRequest().authenticated()
			)
		//                .formLogin(form -> form
		//                        .defaultSuccessUrl("/", true)
		//                        .permitAll()
		//                )
		//            .logout(logout -> logout.permitAll())
		//         OAuth2 로그인 설정 주석 처리된 부분은 다시 활성화하면 됩니다.
		//        .oauth2Login(oauth2 -> oauth2
		//              .loginPage("/login")
		//              .permitAll()
		//        );
		;
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
