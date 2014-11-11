package com.blackbread.interceptor;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.slf4j.MDC;
import org.springframework.core.NamedThreadLocal;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/*                     
 ****************************************************************************
 * 日志拦截器。生成系统日志												       
 * 描述:  
 * 分别实现预处理、后处理（调用了Service并返回ModelAndView，但未进行页面渲染）、返回处理（已经渲染了页面） 
 * 在preHandle中，可以进行编码、安全控制等处理； 
 * 在postHandle中，有机会修改ModelAndView； 
 * 在afterCompletion中，可以根据ex是否为null判断是否发生了异常，进行日志记录。 
 * 参数中的Object handler是下一个拦截器。
 * 版本:	@version 1.0	                                                                   
 * 编制日期: 2013-5-13							    						   
 * 说明: 系统接口调用日志                                    
 * 修改历史: (主要历史变动原因及说明)			      
 */
@SuppressWarnings("unchecked")
public class LogNDCInteceptor extends HandlerInterceptorAdapter {
	private static Logger logger = Logger.getLogger(LogNDCInteceptor.class);
	private NamedThreadLocal<Long> startTimeThreadLocal = new NamedThreadLocal<Long>(
			"StopWatch-StartTime");
	/*  
	 * 最后执行，可用于释放资源
	 */
	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		long endTime = System.currentTimeMillis();// 2、结束时间
		long beginTime = startTimeThreadLocal.get();// 得到线程绑定的局部变量（开始时间）
		long consumeTime = endTime - beginTime;// 3、消耗的时间
		String remoteAddr = getRemortIP(request);
		String queryString = request.getQueryString();
		String method = request.getMethod();
		String url = request.getRequestURI();
		if (method.equalsIgnoreCase("POST")) {// 遍历post参数
			Enumeration<String> e = request.getParameterNames();
			String parameterName, parameterValue;
			while (e.hasMoreElements()) {
				parameterName = e.nextElement();
				parameterValue = request.getParameter(parameterName);
				url += "&" + parameterName + "=" + parameterValue;
			}
			url = url.replaceAll("'", "\"");
		} else {
			if (queryString != null && !"".equals(queryString)
					&& method.equalsIgnoreCase("GET")) {
				url += "&" + queryString;
			}
			url = url.replaceFirst("&", "?");
		}

		StringBuffer sb = new StringBuffer();
		sb.append("request=").append(url);
		MDC.put("ip", remoteAddr);
		MDC.put("usetime", "" + (consumeTime));
		MDC.put("method", method);
		logger.info(sb.toString());
	}

	/*
	 * Action之前执行
	 */
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		long beginTime = System.currentTimeMillis();// 1、开始时间
		startTimeThreadLocal.set(beginTime);// 线程绑定变量（该数据只有当前请求的线程可见）
		return true;// 继续流程
	}

	public String getRemortIP(HttpServletRequest request) {
		if (request.getHeader("x-forwarded-for") == null)
			return request.getRemoteAddr();
		return request.getHeader("x-forwarded-for");
	}
}
