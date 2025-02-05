package com.profitkey.stock.util;

import com.profitkey.stock.entity.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtil {
	public static User getUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null && authentication.getPrincipal() instanceof User) {
			User user = (User)authentication.getPrincipal();
			return user;
		}
		return User.builder().build();
	}
}
