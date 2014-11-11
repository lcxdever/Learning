package com.blackbread.spring.db.dynamic;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;

public class DataSourceSwitch {
	private final static Logger logger = Logger
			.getLogger(DataSourceSwitch.class);
	private DataSourceEntry dataSourceEntry;

	public void setDataSourceEntry(DataSourceEntry dataSourceEntry) {
		this.dataSourceEntry = dataSourceEntry;
	}

	public void switchSource(JoinPoint joinPoint, int type) {
		if (type == 2) {
			dataSourceEntry.set("C3P0_POSTGIS2");
			StringBuffer sb = new StringBuffer(20);
			sb.append(joinPoint.getTarget().getClass()).append(":")
					.append(joinPoint.getSignature().getName())

					.append(" 切换数据源为：C3P0_POSTGIS2");
			logger.info(sb.toString());
		}
	}
}
