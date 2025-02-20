package com.profitkey.stock.handler;

import org.springframework.core.MethodParameter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.profitkey.stock.annotation.UserInfo;
import com.profitkey.stock.dto.response.LoginUser;
import com.profitkey.stock.entity.Auth;
import com.profitkey.stock.service.AuthService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class LoginUserArgumentResolver implements HandlerMethodArgumentResolver {

	private final AuthService authService;

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return parameter.getParameterAnnotation(UserInfo.class) != null
			&& parameter.getParameterType().equals(LoginUser.class);
	}

	// @Override
	// public Object resolveArgument(MethodParameter parameter,
	// 	ModelAndViewContainer mavContainer,
	// 	NativeWebRequest webRequest,
	// 	WebDataBinderFactory binderFactory) {
	// 	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	// 	if (authentication != null && authentication.getPrincipal() instanceof Auth) {
	// 		Auth auth = (Auth)authentication.getPrincipal();
	// 		return new LoginUser(auth);
	// 	}
	// 	return new LoginUser();
	// }
	@Override
	public Object resolveArgument(MethodParameter parameter,
		ModelAndViewContainer mavContainer,
		NativeWebRequest webRequest,
		WebDataBinderFactory binderFactory) {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null && authentication.getPrincipal() instanceof Auth) {
			Auth auth = (Auth)authentication.getPrincipal();

			// AuthService에서 닉네임 가져오기
			String nickname = authService.getNickname(auth);

			// LoginUser 생성할 때 닉네임 포함하도록 수정
			return LoginUser.from(auth, nickname);
		}
		return new LoginUser();
	}
}
