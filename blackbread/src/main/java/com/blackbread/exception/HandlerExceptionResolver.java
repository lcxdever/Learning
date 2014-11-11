package com.blackbread.exception;

import org.apache.log4j.Logger;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

/**
 * 异常处理
 */
public class HandlerExceptionResolver extends SimpleMappingExceptionResolver {
	private final static Logger logger = Logger
			.getLogger(HandlerExceptionResolver.class);

	protected ModelAndView doResolveException(
			javax.servlet.http.HttpServletRequest httpServletRequest,
			javax.servlet.http.HttpServletResponse httpServletResponse,
			java.lang.Object o, java.lang.Exception e) {
		httpServletRequest.setAttribute("ex", e);
		if (e instanceof BadSqlGrammarException)
			logger.error("数据库操作异常", e);
		else
			logger.error("未知异常", e);
		return super.doResolveException(httpServletRequest,
				httpServletResponse, o, e);
	}
}
