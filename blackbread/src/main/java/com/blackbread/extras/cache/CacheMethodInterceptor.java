package com.blackbread.extras.cache;

import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;

/**
 * 缓存方法拦截器核心代码
 * 
 * @author chengxiang.li
 * @date 2014年8月13日 下午6:57:04
 */
public class CacheMethodInterceptor implements MethodInterceptor,
		InitializingBean {

	private static final Logger logger = Logger
			.getLogger(CacheMethodInterceptor.class);

	private Ehcache ehcache;

	public void afterPropertiesSet() throws Exception {
	}

	public Object invoke(MethodInvocation invocation) throws Throwable {
		String targetName = invocation.getThis().getClass().getName();
		String methodName = invocation.getMethod().getName();
		Object[] arguments = invocation.getArguments();
		String cacheKey = getCacheKey(targetName, methodName, arguments);

		if (ehcache.isDisabled()) {
			logger.error("Ehcache服务初始化失败...");
			return invocation.proceed();
		}

		// 缓存节点不存在的情况
		if (ehcache.getQuiet(cacheKey) == null) {
			synchronized (ehcache) {
				logger.info("缓存:" + cacheKey + "不存在或已经失效!");
				// 这里判断是为了降低强制同步的负面影响,只需第一个操作该添加过程,后来者则跳过
				if (ehcache.getQuiet(cacheKey) == null) {
					Object obj = invocation.proceed();
					Element element = new Element(cacheKey, obj);
					ehcache.put(element);
				}
			}
		}
		// 返回缓存值
		return ehcache.getQuiet(cacheKey).getObjectValue();

	}

	/**
	 * 
	 * 返回具体的方法(全路径+方法名+参数值 例：com.blackbread.service.imp.UserServiceImp.list(2))
	 * 
	 * @param targetName
	 *            全路径
	 * @param methodName
	 *            方法名称
	 * @param arguments
	 *            参数
	 * @return String
	 */
	private String getCacheKey(String targetName, String methodName,
			Object[] arguments) {
		StringBuffer buffer = new StringBuffer("");
		buffer.append(targetName).append(".").append(methodName).append("(");
		for (Object argument : arguments) {
			buffer.append(argument.toString()).append(",");
		}
		if (arguments.length > 0)
			buffer.deleteCharAt(buffer.length() - 1);
		buffer.append(")");
		return buffer.toString();
	}

	public void setEhcache(Ehcache ehcache) {
		this.ehcache = ehcache;
	}

}