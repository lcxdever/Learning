package com.blackbread.utils.uuid;

public interface IdWorker {
	public long nextId(long workerId, long datacenterId);

	public long nextId(long dataId);
}
