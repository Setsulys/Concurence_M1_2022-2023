package exam;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Sack {

	private final ReentrantLock lock = new ReentrantLock();
	private final Condition condition = lock.newCondition();
	private final ArrayDeque<Integer> queue = new ArrayDeque<>();
	private int weight;
	private int totalList;
	private int tmp;

	public Sack(int weight) {
		this.weight = weight;
	}

	private int sum() {
		return queue.stream().mapToInt(Integer::valueOf).sum();
	}

	public void putGift(int weight) throws InterruptedException {
		lock.lock();
		try {
			if ((sum() <= this.weight)) {
				queue.add(weight);
				condition.signal();
			} else {
				condition.await();
			}
		} finally {
			lock.unlock();
		}
	}

	public int takeGift() throws InterruptedException {
		lock.lock();
		try {
			if (queue.isEmpty()) {
				condition.await();
			}
			var tmp = queue.pollFirst();
			condition.signal();
			return tmp;
		} finally {
			lock.unlock();
		}
	}

	public int weightNeeded() {
		lock.lock();
		try {
			return tmp - totalList;
		} finally {
			lock.unlock();
		}

	}

	public List<Integer> takeGiftsUntil(int weight) throws InterruptedException {
		lock.lock();
		try {
			this.tmp = weight;
			List<Integer> list = new ArrayList<>();
			while (totalList <= weight) {
				var tmp = takeGift();
				totalList += tmp;
				list.add(tmp);
				condition.await();
			}
			condition.signal();
			return list;
		} finally {
			lock.unlock();
		}
	}

	public static void main(String[] args) {
		var sack = new Sack(500);
		Thread.ofPlatform().start(() -> {
			try {
				for (;;) {
					Thread.sleep(100);
					sack.putGift(10);
					sack.putGift(5);
					System.out.println(sack.sum());
					Thread.sleep(200);
					System.out.println("le poid du cadeau recupéré est de " + sack.takeGift() + "\n");
				}
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		});
		

	}
}