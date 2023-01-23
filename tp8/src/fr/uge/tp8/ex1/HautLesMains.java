package fr.uge.tp8.ex1;

public class HautLesMains {
    public static void main(String[] args) throws InterruptedException {
      var t= Thread.ofPlatform().start(() -> {
        for (var i = 1;; i++) {
          try {
            Thread.sleep(1_000);
            System.out.println("Thread slept " + i + " seconds.");
          } catch (InterruptedException e) {
            return;
          }
        }
      });
      Thread.sleep(5000);
      t.interrupt();
     }
}

//Pourquoi n'est il pas possible d’arrêter un thread de façon non coopérative ?
/* En java il nous est pas possible de forcer l'arret d'un thread 
 */

//Rappeler ce qu'est une opération bloquante
/* Une operation bloquante est un appel systeme ou une methode qui peut rester bloqué dans l'attente d'une ressource
 */

//À quoi sert la méthode d'instance interrupt() de la classe Thread?
/* La methode d'instance interrupt() de la classe thread envoie un signal d'interruption à un autre thread
 * et positionne le statut de l'interruption a true
 */ 

// Expliquer comment interrompre un thread en train d'effectuer une opération bloquante et le faire sur l'exemple suivant : 
//le thread main attend 5 secondes avant d'interrompre le thread qui dort et ce dernier affiche son nom.
/* En utilisant un try catch et faire un retour sur le catch et ajouter un Thread.sleep(5000) puis t.interrupt();
 * 
 *  public static void main(String[] args) throw InterruptedException{
 *  var t = Thread.ofPlatform().start(() -> {
 *    for (var i = 1;; i++) {
 *      try {
 *        Thread.sleep(1_000);
 *        System.out.println("Thread slept " + i + " seconds.");
 *      } catch (InterruptedException e) {
 *        return;
 *      }
 *    }
 *  });
 *  Thread.sleep(5000);
 *  t.interrupt();
 * }
 */

