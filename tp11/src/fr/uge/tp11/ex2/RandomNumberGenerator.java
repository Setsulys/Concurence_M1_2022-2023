package fr.uge.tp11.ex2;

import java.util.HashSet;
import java.util.concurrent.atomic.AtomicLong;

public class RandomNumberGenerator {
  private AtomicLong x ;
  
  public RandomNumberGenerator(long seed) {
    if (seed == 0) {
      throw new IllegalArgumentException("seed == 0");
    }
    x = new AtomicLong(seed);
  }
  
  public long next() {  // Marsaglia's XorShift
//    for(;;) {
//      var current = x.get();
//      var tmp = current ^ (current >>> 12);
//      tmp ^= (tmp << 25);
//      tmp ^= (tmp >>>27); 
//      if(x.compareAndSet(current, tmp)) {
//        return tmp * 2685821657736338717L;
//      }
//    }
    return x.updateAndGet( x ->{
      var tmp = x ^ (x >>> 12);
      tmp ^= (tmp << 25);
      tmp ^= (tmp >>>27);
      return tmp* 2685821657736338717L;
    });
  }
  
  public static void main(String[] args) throws InterruptedException {
    var set0 = new HashSet<Long>();
    var set1 = new HashSet<Long>();
    var set2 = new HashSet<Long>();
    var rng0 = new RandomNumberGenerator(1);
    var rng = new RandomNumberGenerator(1);

    for (int i = 0; i < 10_000; i++) {
      set0.add(rng0.next());
    }

    var thread = Thread.ofPlatform().start(() -> {
      for (var i = 0; i < 5_000; i++) {
        set1.add(rng.next());
      }
    });

    for (var i = 0; i < 5_000; i++) {
      // System.out.println(rng.next());
      set2.add(rng.next());
    }
    thread.join();

    System.out.println("set1: " + set1.size() + ", set2: " + set2.size());
    set1.addAll(set2);
    System.out.println("union (should be 10000): " + set1.size());

    System.out.println("inter (should be true): " + set0.containsAll(set1));
    set0.removeAll(set1);
    System.out.println("inter (should be 0): " + set0.size());
  }
}

//Expliquer comment fonctionne un générateur pseudo-aléatoire et pourquoi l'implantation ci-dessous n'est pas thread-safe.
/*
 * Un générateur pseudo-aléatoire fonctionne avec une seed que l'on choisis au début 
 * 
 * ici l'implantation n'est pas thread safe car on accede au champ x alors qu'il n'est pas volatile
 */

//Utiliser la classe AtomicLong et sa méthode compareAndSet pour obtenir une implantation lock-free du générateur pseudo-aléatoire.

//Simplifiez votre code en utilisant la méthode updateAndGet de la classe AtomicLong.
