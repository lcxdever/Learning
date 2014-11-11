package com.blackbread.aop;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import com.blackbread.controller.UserController;

@Component
@Aspect
public class LogAspect {
	private static final Logger logger = Logger.getLogger(UserController.class);

	@Before(value = "execution(* com.peacemap.action.*.*(..))")
	public void authorith() {
		System.out.println("模拟进行权限检查。");
	}

	@After("execution(* com.peacemap.action.*.*(..))")
	public void authoritha() {
		System.out.println("模拟权限检查完毕。");
	}

	@Around(value = "execution(* com.peacemap.service.imp.*.*(..))")
	public void authorithar(ProceedingJoinPoint pjp) throws Throwable {
		long startTime = System.currentTimeMillis();
		System.out.println("记录日志开始");
		System.out.println(pjp.getTarget().getClass());
		pjp.proceed();
		System.out.println("记录日志完毕,耗时："
				+ (System.currentTimeMillis() - startTime));
	}

	@AfterThrowing(value = "execution(* com.peacemap.service.imp.*.*(..))", throwing = "th")
	public void exOp(Throwable th) {
		System.out.println("出现问题了啊少年:" + th.getMessage());
	}

	@AfterReturning(value = "execution(* com.peacemap.action.*.*(..))", returning = "str")
	public void retOp(String str) {
		System.out.println("返回了啊少年:" + str);
	}
}
