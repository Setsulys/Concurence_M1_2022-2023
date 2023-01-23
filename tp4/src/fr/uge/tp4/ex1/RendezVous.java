package fr.uge.tp4.ex1;

import java.util.Objects;

public class RendezVous<V> {

	private V value;
	private final Object lock = new Object();

	  public void set(V value) {
	    synchronized (lock) {
	      Objects.requireNonNull(value);
	      this.value = value;
        lock.notify(); 
      }
	  }

	  public V get() throws InterruptedException {
	    synchronized (lock) {
	      while (value == null) {   
	        lock.wait();
	        Thread.sleep(1); // then comment this line !
        }
	    		
	    }
	    return value;
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

//Tester votre classe avec le main ci-dessous. Vous devriez observer avec la commande top que votre programme ne consomme quasiment aucun temps processeur.
/* Je constate une fluctuation maximale de  0.7%* /
 */