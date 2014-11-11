package com.blackbread.test;

import java.util.ArrayList;
import java.util.List;

public class Observer {
	public static void main(String[] args) {
		Watched girl = new ConcreteWatched();

		Watcher watcher1 = new ConcreteWatcher();
		Watcher watcher2 = new ConcreteWatcher();
		Watcher watcher3 = new ConcreteWatcher();

		girl.addWatcher(watcher1);
		girl.addWatcher(watcher2);
		girl.addWatcher(watcher3);

		girl.notifyWatchers("开心");
	}

}

interface Watcher {
	public void update(String str);
}

interface Watched {
	public void addWatcher(Watcher watcher);

	public void removeWatcher(Watcher watcher);

	public void notifyWatchers(String str);
}

class ConcreteWatcher implements Watcher {
	public void update(String str) {
		System.out.println(str);
	}
}

class ConcreteWatched implements Watched {
	// 存放观察者
	private List<Watcher> list = new ArrayList<Watcher>();

	public void addWatcher(Watcher watcher) {
		list.add(watcher);
	}

	public void removeWatcher(Watcher watcher) {
		list.remove(watcher);
	}

	public void notifyWatchers(String str) {
		for (Watcher watcher : list) {
			watcher.update(str);
		}
	}
}