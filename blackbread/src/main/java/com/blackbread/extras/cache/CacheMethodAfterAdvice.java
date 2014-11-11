package com.blackbread.extras.cache;

import java.util.List;

import net.sf.ehcache.Ehcache;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;

/**
 * 缓存数据变动后让数据保持同步状态
 * 
 * @author chengxiang.li
 * @date 2014年8月13日 下午6:57:04
 */
@SuppressWarnings("unchecked")
public class CacheMethodAfterAdvice {

	private static final Logger logger = Logger
			.getLogger(CacheMethodAfterAdvice.class);

	private Ehcache ehcache;

	/**
	 * 原始数据发生变更时保持强一致性(刪除緩存管理器里指定key的value)
	 * 
	 * @param joinPoint
	 */
	public void afterReturning(JoinPoint joinPoint) {
		if (ehcache.isDisabled()) {
			logger.error("Ehcache服务初始化失败...");
			return;
		}
		// 获取当前访问类
		Class<?> clazz = joinPoint.getTarget().getClass();
		// 获取ehcache的key集合
		List<String> keys = ehcache.getKeys();
		// 遍历删除匹配的key
		for (String key : keys) {
			if (key.startsWith(clazz.getName())) {
				ehcache.remove(key);
			}
		}
	}

	public void setEhcache(Ehcache ehcache) {
		this.ehcache = ehcache;
	}

}
