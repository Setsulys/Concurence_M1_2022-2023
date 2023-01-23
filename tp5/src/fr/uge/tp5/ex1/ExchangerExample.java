package fr.uge.tp5.ex1;

//import java.util.concurrent.Exchanger;

public class ExchangerExample {
  
  @SuppressWarnings("preview")
  public static void main(String[] args) throws InterruptedException {
    var exchanger = new Exchanger<String>();
    Thread.ofPlatform().start(() -> {
      try {
        System.out.println("thread 1 " + exchanger.exchange("foo1"));
      } catch (InterruptedException e) {
        throw new AssertionError(e);
      }
    });
    System.out.println("main " + exchanger.exchange(null));
    
    Thread.ofPlatform().start(() -> {
      try {
        System.out.println("thread 2 " + exchanger.exchange("foo2"));
      } catch (InterruptedException e) {
        throw new AssertionError(e);
      }
    });
    System.out.println("main " + exchanger.exchange("null2"));
  }
}

// Quel est l'affichage attendu ?
/* main foo1
 * thread 1 null
 * cependant nous avons 
 * thread 1 null
 * main foo1
 */

//Comment faire pour distinguer le premier et le second appel à la méthode exchange ?
/* On ajoute un string aux prints pour les distinguer
 * 
 */
