package td3.ex1;

import java.util.ArrayList;
import java.util.Objects;

public class ThreadSafeList<E> {

	private final Object lock = new Object();
	private final ArrayList<E> list;
	
	public ThreadSafeList(int capacity){
		this.list = new ArrayList<E>(capacity);
	}
	
	
	public void add(E element) {
		Objects.requireNonNull(element);
		synchronized (lock) {
			list.add(element);
		}
	}
	
	public int size() {
		synchronized (lock) {
			return list.size();
		}
	}
	
	public String toString() {
		synchronized (lock) {
			return list.toString();
		}
	}
	
}
