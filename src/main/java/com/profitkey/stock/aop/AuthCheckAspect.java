package com.profitkey.stock.aop;

import com.profitkey.stock.util.SecurityUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AuthCheckAspect {

	@Around("@annotation(com.profitkey.stock.annotation.AuthCheck)")
	public Object validateUserId(ProceedingJoinPoint joinPoint) throws Throwable {
		Object[] args = joinPoint.getArgs();
		boolean hasUserIdProvider = false;

		for (Object arg : args) {
			if (arg instanceof UserIdProvider) {
				hasUserIdProvider = true;
				UserIdProvider request = (UserIdProvider)arg;
				Long authId = SecurityUtil.getCurrentUserId();
				Long requestUserId = Long.parseLong(request.getUserId());

				if (!authId.equals(requestUserId)) {
					throw new RuntimeException("사용자 인증 실패: 요청한 사용자 ID가 현재 로그인한 사용자와 일치하지 않습니다.");
				}
				break;
			}
		}

		// request 없을경우 로그인 정보만 확인
		if (!hasUserIdProvider) {
			Long authId = SecurityUtil.getCurrentUserId();
			if (authId == null) {
				throw new RuntimeException("로그인이 필요합니다.");
			}
		}
		return joinPoint.proceed();
	}
}
