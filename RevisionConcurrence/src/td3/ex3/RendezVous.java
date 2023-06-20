package td3.ex3;

import java.util.Objects;

public class RendezVous<V> {
	  private V value;
	  private final Object lock = new Object();
	  
	  
	  public void set(V value) {
	    Objects.requireNonNull(value);
	    synchronized (lock) {
	    	this.value = value;
		}
	   
	  }
	  
	  public V get() throws InterruptedException {
	    while(value == null) {
	        Thread.sleep(1);  // then comment this line !
	        System.out.println("");
	    }
	    synchronized (lock) {
	    	return value;
		}
	    
	  }
}
