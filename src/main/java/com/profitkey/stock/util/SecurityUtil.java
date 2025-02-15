package com.profitkey.stock.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.profitkey.stock.entity.Auth;

public class SecurityUtil {
	public static Auth getUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null && authentication.getPrincipal() instanceof Auth) {
			Auth auth = (Auth)authentication.getPrincipal();
			return auth;
		}
		return Auth.builder().build();
	}
}
