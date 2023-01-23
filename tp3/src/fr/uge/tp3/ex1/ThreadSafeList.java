package fr.uge.tp3.ex1;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class ThreadSafeList {
	public final ArrayList<Integer>threadSafe = new ArrayList<Integer>();
	private final static Object lock = new Object();
	
	public void add(int n) {
		synchronized (lock) {
			threadSafe.add(n);
		}
	}
	
	public int Size() {
		return threadSafe.size();
	}
 
	@Override
	public String toString() {
		return threadSafe.stream().map(e -> e.toString()).collect(Collectors.joining("\n"));
	}
}
