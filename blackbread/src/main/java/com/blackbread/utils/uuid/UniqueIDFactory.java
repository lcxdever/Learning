package com.blackbread.utils.uuid;

public class UniqueIDFactory {
	public static IdWorker getIdWorker(IdWorkerType idWorkerType) {
		if (idWorkerType.equals(IdWorkerType.snowflake)) {
			return new IdWorkerFromSnowflake();
		}
		if (idWorkerType.equals(IdWorkerType.mysql)) {
			return null;
		}
		return null;
	}
	public static IdWorker getIdWorker() {
		return getIdWorker(IdWorkerType.snowflake);
	}
	
	public static void main(String[] args) {
		System.out.println(UniqueIDFactory.getIdWorker().nextId(32));
	}
}
