package com.ocloudwork.boot.demo.annotation;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Component
@ConfigurationProperties(prefix = "runtime.log")
@Order(100)//优先级低
public class RuntimeLogParser {
	Logger logger = LoggerFactory.getLogger(getClass());

	@Pointcut("@annotation(com.ocloudwork.boot.demo.annotation.RuntimeLog)")
	private void cut() {
		throw new UnsupportedOperationException();
	}

	private boolean enableLog = false;

	@Around("cut()")
	public Object runtimeDeal(ProceedingJoinPoint joinPoint) {
		Object obj = null;
		try {
			if (!enableLog) {
				return joinPoint.proceed();
			}
			long start = System.currentTimeMillis();
			obj = joinPoint.proceed();
			long result = System.currentTimeMillis() - start;
			String methodName = joinPoint.getStaticPart().toLongString();
			logger.info(Marker.ANY_MARKER, "{0}:{1}", methodName, result);
		} catch (Throwable e) {
			logger.error(e.getMessage());
		}
		return obj;
	}

	public boolean getEnableLog() {
		return enableLog;
	}

	public void setEnableLog(boolean enableLog) {
		this.enableLog = enableLog;
	}

}
