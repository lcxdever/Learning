package com.blackbread.utils;

public class UUID {
	private IdWorker idWorker;

	private UUID() {
		idWorker = new IdWorker(1, 31);
	}

	public String next() {
		return String.valueOf(idWorker.nextId());
	}

	public static UUID getInstance() {
		return SingltonHolder.uuid;
	}

	private static class SingltonHolder {
		private static UUID uuid = new UUID();
	}

	public static void main(String[] args) {
		for (int i = 0; i < 100; i++) {
			System.out.println(UUID.getInstance().next());
		}
	}
}
