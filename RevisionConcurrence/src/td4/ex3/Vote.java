package td4.ex3;

import java.util.HashMap;
import java.util.Objects;

public class Vote {

	
	private final Object lock = new Object();
	private final int nbVotes;
	private int count;
	private final HashMap<String, Integer> map;
	
	public Vote(int nbVotes) {
		synchronized (lock) {
			if(nbVotes <= 0 ) {
				throw new IllegalArgumentException();
			}
			this.nbVotes = nbVotes;
			this.count = 0;
			map = new HashMap<>();
		}
	}
	
	
	public String computeWinner() {
		synchronized (lock) {
			int score = 0;
			String winner =null;
			for(var e : map.entrySet()) {
				var key = e.getKey();
				var value = e.getValue();
				if(value > score || (value == score && key.compareTo(winner) < 0)) {
					winner = key;
					score = value;
				}
			}
			lock.notify();
			return winner;
		}
	}
	
	public String vote(String name) throws InterruptedException {
		Objects.requireNonNull(name);
		synchronized (lock) {
			if(count == nbVotes) {
				return computeWinner();
			}
			map.putIfAbsent(name, 0);
			map.replace(name,map.get(name),map.get(name)+1);
			count++;
			while(count != nbVotes) {
				lock.wait();
			}
			lock.notify();
			return computeWinner();
		}
	}
	
	  public static void main(String[] args) throws InterruptedException {
		    var vote = new Vote(4);
		    Thread.ofPlatform().start(() -> {
		      try {
		        Thread.sleep(2_000);
		        System.out.println("The winner is " + vote.vote("un"));
		      } catch (InterruptedException e) {
		        throw new AssertionError(e);
		      }
		    });
		    Thread.ofPlatform().start(() -> {
		      try {
		        Thread.sleep(1_500);
		        System.out.println("The winner is " + vote.vote("zero"));
		      } catch (InterruptedException e) {
		        throw new AssertionError(e);
		      }
		    });
		    Thread.ofPlatform().start(() -> {
		      try {
		        Thread.sleep(1_000);
		        System.out.println("The winner is " + vote.vote("un"));
		      } catch (InterruptedException e) {
		        throw new AssertionError(e);
		      }
		    });
		    System.out.println("The winner is " + vote.vote("zero"));
		  }
}
