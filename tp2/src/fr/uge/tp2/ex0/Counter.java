package fr.uge.tp2.ex0;


public class Counter {
	  private int value;

	  public void addALot() {
	    for (var i = 0; i < 100_000; i++) {
	      this.value++;
	    }
	  }

	  public static void main(String[] args) throws InterruptedException {
	    var counter = new Counter();
	    var thread1 = Thread.ofPlatform().start(counter::addALot);
	    var thread2 = Thread.ofPlatform().start(counter::addALot);
	    thread1.join();
	    thread2.join();
	    System.out.println(counter.value);
	  }
	}

//Essayez d'expliquer ce que vous observez.

/* J'observe que la valeur affiché n'est pas la même pour chaque opérations 
 */

//Est-il possible que ce code affiche moins que 10000 ? Expliquer précisément pourquoi.

/* Non il n'est pas possible que le code affiche moins de 100_000,
 * Comme il y a threads qui sont passé et qui incrémentent jusqu'a 100_000
 * la ligne "this.value++" n'est pas atomique, en byte code elle est traduite en plusieurs lignes  
 *
 * On a plusieurs Thread qui font une écriture sur le tas, 
 * on peut avoir des effets de bords de la non atomicité 
 */