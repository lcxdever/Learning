package com.blackbread.spring.db.dynamic;

import org.aspectj.lang.JoinPoint;

/**
 * 数据源切换接口
 * 
 * @author shadow
 * @create 2013.04.03
 */
public interface DataSourceEntry {

	// 默认数据源
	public final static String DEFAULT_SOURCE = null;

	/**
	 * 还原数据源
	 * 
	 * @param joinPoint
	 */
	public void restore(JoinPoint join);

	/**
	 * 设置数据源
	 * 
	 * @param dataSource
	 */
	public void set(String source);

	/**
	 * 获取数据源
	 * 
	 * @return String
	 */
	public String get();

	/**
	 * 清空数据源
	 */
	public void clear();
}
