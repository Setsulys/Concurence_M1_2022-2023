package fr.uge.tp11.ex1;

public class StopThreadBug {
  private volatile boolean stop;

  public void runCounter() {
    var localCounter = 0;
    for(;;) {
      if (stop) {
        break;
      }
      localCounter++;
    }
    System.out.println(localCounter);
  }

  public void stop() {
    stop = true;
  }

  public static void main(String[] args) throws InterruptedException {
    var bogus = new StopThreadBug();
    var thread = Thread.ofPlatform().start(bogus::runCounter);
    Thread.sleep(100);
    bogus.stop();
    thread.join();
  }
}

//Rappelez rapidement où est la data-race et pourquoi on peut observer que le programme ne s'arrête jamais.

//Rendez la classe thread-safe sans utiliser de mécanisme de verrou. Quelle propriété garantit que le programme s'arrête ?
/*
 * On doit ajouter un modifier volatille au champs stop
 */

