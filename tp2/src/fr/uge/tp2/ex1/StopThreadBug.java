package fr.uge.tp2.ex1;

public class StopThreadBug implements Runnable {
	  private boolean stop = false;

	  public void stop() {
	    stop = true;
	  }

	  @Override
	  public void run() {
	    while (!stop) {
	      System.out.println("Up");
	    }
	    System.out.print("Done");
	  }

	  public static void main(String[] args) throws InterruptedException {
	    var stopThreadBug = new StopThreadBug();
	    Thread.ofPlatform().start(stopThreadBug::run);
	    Thread.sleep(5_000);
	    System.out.println("Trying to tell the thread to stop");
	    stopThreadBug.stop();
	  }
	}

// Exécuter la classe plusieurs fois. Qu'observez-vous ?
/* il n'y a pas de changement lorsque l'on execute plusieurs fois la classe
 */

// Modifiez la classe StopThreadBug.java pour supprimer l'affichage dans la boucle du thread. 
// la classe à nouveau plusieurs fois. Essayez d'expliquer ce comportement.
/* comme il n'y a pas de code dans la boucle l'optimisation de java ne regarde plus l'état de la variable stop qui ne sera donc pas changé on a alors une boucle infini
 * 
 */

//Le code avec l'affichage va-t-il toujours finir par arrêter le thread ?
/* Oui le code avec l'affichage s'arretera toujours car il y a du code dans la boucle
 */