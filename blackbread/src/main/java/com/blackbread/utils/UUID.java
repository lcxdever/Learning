package com.blackbread.utils;

public class UUID {
	private IdWorker idWorker;

	private UUID() {
		idWorker = new IdWorker(1, 31);
	}

	public long next() {
		return idWorker.nextId();
	}

	public static UUID getInstance() {
		return SingltoHolder.uuid;
	}

	private static class SingltoHolder {
		private static UUID uuid = new UUID();
	}

	public static void main(String[] args) {
		for (int i = 0; i < 100; i++) {
			System.out.println(UUID.getInstance().next());
		}
	}
}
