package com.blackbread.extras.cache;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

/**
 * 客户端缓存控制
 * 
 * @author shadow
 * @email 124010356@qq.com
 * @create 2012.04.28
 */
public class ViewCacheControler implements Filter {

	private static final String EXPIRATION_TIME = "expirationTime";
	private long expirationTime = 0;// 单位：秒,0表示不缓存

	public void init(FilterConfig filterConfig) {
		// 获取xml配置参数
		String expirationTimeParameter = filterConfig
				.getInitParameter(EXPIRATION_TIME);
		if (expirationTimeParameter != null) {
			expirationTime = Long.valueOf(expirationTimeParameter);
		}
	}

	public void doFilter(ServletRequest servletRequest,
			ServletResponse servletResponse, FilterChain chain)
			throws IOException, ServletException {
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		if (!response.isCommitted()) {
			if (expirationTime > 0) {
				// 参数值>0则设置缓存时间
				response.setDateHeader("Expires", System.currentTimeMillis()
						+ expirationTime * 1000);
				response.setHeader("Cache-Control", "private, max-age="
						+ expirationTime);
			} else {
				// 参数值<=0设置为不缓存
				response.setHeader("progma", "no-cache");
				response.setHeader("Cache-Control", "no-cache");
				response.setHeader("Cache-Control", "no-store");
				response.setDateHeader("Expires", 0);
			}
		}
		chain.doFilter(servletRequest, servletResponse);
	}

	public void destroy() {
	}

}