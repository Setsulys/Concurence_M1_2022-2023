package fr.uge.tp2.ex3;

import java.util.ArrayList;
import java.util.stream.IntStream;

public class HelloListBug {

	public static void main(String[] args) throws InterruptedException {
		  var nbThreads = 4;
		  var threads = new Thread[nbThreads];
		  var list = new ArrayList<Integer>(5000 * nbThreads);

		  IntStream.range(0, nbThreads).forEach(j -> {
		    Runnable runnable = () -> {
		      for (var i = 0; i < 5000; i++) {
		        list.add(i);
		      }
		    };

		    threads[j] = Thread.ofPlatform().start(runnable);
		  });

		  for (var thread : threads) {
		    thread.join(); 
		  }
		  System.out.println(list.size());
		  System.out.println("le programme est fini");
		}
}
//Exécuter le programme plusieurs fois et noter les différents affichages.
/*8014
le programme est fini

 *7172
le programme est fini

 *6827
le programme est fini

 *6412
le programme est fini
*/
//Expliquer comment la taille de la liste peut être plus petite que le nombre total d'appels à la méthode add.
/* Comme le schéduler peut désheduler ou resheduler un thread, il est possible que l'un des thread A a lu le pointeur de la liste cependant il a été déshédulé 
 * puis il y a eu plusieurs threads qui sont passés et ont fait leurs executions
 * Ce n'est que lorsque A est reshédulé il va écrire sur la case qu'il a lu avec le pointeur donc écraser une case et ainsi de suite
 */
