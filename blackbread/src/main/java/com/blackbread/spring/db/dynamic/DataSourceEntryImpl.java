package com.blackbread.spring.db.dynamic;

import org.aspectj.lang.JoinPoint;

/**
 * 数据源切换实现类类
 * 
 * @author shadow
 * @create 2013.04.03
 */
public class DataSourceEntryImpl implements DataSourceEntry {

	private final static ThreadLocal<String> local = new ThreadLocal<String>();

	public void clear() {
		local.remove();
	}

	public String get() {
		return local.get();
	}

	public void restore(JoinPoint join) {
		local.set(DEFAULT_SOURCE); // 设置null数据源
	}

	public void set(String source) {
		local.set(source);
	}

}