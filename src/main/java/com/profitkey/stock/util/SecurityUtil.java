package com.profitkey.stock.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtil {
	public static Long getCurrentUserId() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (authentication == null || authentication.getPrincipal() == "anonymousUser") {
			throw new RuntimeException("로그인한 사용자가 아닙니다.");
		}

		return Long.parseLong(authentication.getName());
	}

}
