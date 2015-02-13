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
			javax.servlet.http.HttpServletRequest request,
			javax.servlet.http.HttpServletResponse httpServletResponse,
			java.lang.Object o, java.lang.Exception e) {
		request.setAttribute("ex", e);
		if(e.getCause()!=null&&e.getCause().getMessage()!=null){
			logger.error("error",e);
			request.setAttribute("error","系统内部异常，请联系管理员");
		}
		else
			request.setAttribute("error",e.getMessage());
		if (e instanceof BadSqlGrammarException)
			logger.error("数据库操作异常", e);
		else
			logger.error("未知异常", e);
		return super.doResolveException(request,
				httpServletResponse, o, e);
	}
}
