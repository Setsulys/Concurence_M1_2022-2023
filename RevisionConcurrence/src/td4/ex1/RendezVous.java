package td4.ex1;

import java.util.Objects;

public class RendezVous<V> {
	  private V value;
	  private final Object lock = new Object();
	  public boolean done;
	  
	  public void set(V value) {
	    Objects.requireNonNull(value);
	    synchronized (lock) {
	    	this.value = value;
	    	done = true;
	    	lock.notifyAll();
		}
	   
	  }
	  
	  public V get() throws InterruptedException {
		synchronized (lock) {
		    while(value == null) {
		        lock.wait();
		    }
	    	return value;
		}
	    
	  }
	  
	  
	  public static void main(String[] args) throws InterruptedException {
		    var rdv = new RendezVous<String>();
		    Thread.ofPlatform().start(() -> {
		      try {
		        Thread.sleep(20_000);
		        rdv.set("Message");
		      } catch (InterruptedException e) {
		        throw new AssertionError(e);
		      }
		    });
		    System.out.println(rdv.get());
		  }
}
