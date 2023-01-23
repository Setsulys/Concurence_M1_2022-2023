package fr.uge.tp8.ex1;

import java.util.NoSuchElementException;
import java.util.OptionalLong;
import java.util.concurrent.ThreadLocalRandom;

public class HautLesMains2 {
  public static boolean isPrime(long candidate) {
    if (candidate <= 1) {
      return false;
    }
    for (var i = 2; i <= Math.sqrt(candidate); i++) {
      if (candidate % i == 0) {
        return false;
      }
      if(!Thread.currentThread().isInterrupted()) {
        return false;
      }
    }
    return true;
  }

  public static OptionalLong findPrime() {
    var generator = ThreadLocalRandom.current();
    for (;!Thread.currentThread().isInterrupted();) {
      var candidate = generator.nextLong();
      if (isPrime(candidate)) {
        return OptionalLong.of(candidate);
      }
    }
    return OptionalLong.empty();
  }

  public static void main(String[] args) throws InterruptedException {
    @SuppressWarnings("preview")
    var t =Thread.ofPlatform().start(() -> {
      try {
      System.out.println("Found a random prime : " + findPrime().orElseThrow());
      }catch( NoSuchElementException e) {
        System.out.println("too late ...");
      }
      
    });
    Thread.sleep(3000);
    t.interrupt();
  }
}

//Expliquer, sur l'exemple suivant, comment utiliser la méthode Thread.interrupted pour arrêter le calcul de findPrime() qui n'est pas une opération bloquante. 
//Modifier le code de findPrime (mais ni sa signature, ni isPrime) pour pouvoir l'interrompre. Dans ce cas, elle renvoie un OptionalLong vide.
//Puis faire en sorte que le main attende 3 secondes avant d'interrompre le thread qui cherche un nombre premier, en affichant "too late...".


//Expliquer la (trop) subtile différence entre les méthodes Thread.interrupted et thread.isInterrupted de la classe Thread.
/* La subtile difference entre les méthodes Thread.interrupted et Thread.isInterrupted est que interrupted ne reinitialise pas son statut alors que isInterrupted si
 */

//On souhaite maintenant faire en sorte que findPrime s'arrête dès que possible si le thread qui l’utilise est interrompu. 
//Pour cela, modifier le code de findPrime et/ou isPrime sans modifier leur signature.
/* 
 *public static boolean isPrime(long candidate) {
 *  if (candidate <= 1) {
 *    return false;
 *  }
 *  for (var i = 2; i <= Math.sqrt(candidate)); i++) {
 *    if (candidate % i == 0) {
 *      return false;
 *    }
 *    if(!Thread.currentThread().isInterrupted()){
 *      return false
 *    }
 *  }
 *  return true;
 *}
 *
 *public static OptionalLong findPrime() {
 *  var generator = ThreadLocalRandom.current();
 *  for (;!Thread.currentThread().interrupted();) {
 *    var candidate = generator.nextLong();
 *    if (isPrime(candidate)) {
 *      return OptionalLong.of(candidate);
 *    }
 *  }
 *  return OptionalLong.Empty();
 *}
 *
 *public static void main(String[] args) throws InterruptedException {
 *  Thread.ofPlatform().start(() -> {
 *    System.out.println("Found a random prime : " + findPrime().orElseThrow());
 *  });
 *  Thread.sleep(3000);
 *  System.out.println("too late");
 *  t.interrupt();
 *}
 */
