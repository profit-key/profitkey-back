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
		Object[] args = joinPoint.getArgs();  // 메서드의 모든 파라미터 가져오기
		for (Object arg : args) {
			if (arg instanceof UserIdProvider) {  // UserIdProvider 인터페이스를 구현한 경우
				UserIdProvider request = (UserIdProvider)arg;
				Long authId = SecurityUtil.getCurrentUserId();
				Long requestUserId = Long.parseLong(request.getUserId());

				if (!authId.equals(requestUserId)) {
					throw new RuntimeException("사용자 인증 실패: 요청한 사용자 ID가 현재 로그인한 사용자와 일치하지 않습니다.");
				}
				break;
			}
		}
		return joinPoint.proceed();
	}
}
