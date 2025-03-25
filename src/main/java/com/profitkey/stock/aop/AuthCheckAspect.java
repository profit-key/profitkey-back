package com.profitkey.stock.aop;

import com.profitkey.stock.exception.testexception.faq.UnAuthorizedException;
import com.profitkey.stock.util.SecurityUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Order(0)
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
					throw new UnAuthorizedException();
				}
				break;
			}
		}

		// request 없을경우 로그인 정보만 확인
		if (!hasUserIdProvider) {
			Long authId = SecurityUtil.getCurrentUserId();
			if (authId == 0) {
				throw new UnAuthorizedException();
			}
		}
		return joinPoint.proceed();
	}
}
