package fr.uge.tp1.ex1;

public class Main {
//Rappeler à quoi sert un Runnable.
	/*Un runnable permet de executer un thread */
	
	
	public static void main(String[] args) {
		var th= new HelloThread();
		th.threads(4);
		
		//Exécutez le programme plusieurs fois, que remarque-t-on ? Puis, en regardant l'affichage (scroller au besoin), 
		//qu'y a-t-il de bizarre ? Est-ce que tout ceci est bien normal ?
		/* Le resultat n'est pas le même pour chaque execution on 
		 * explique ca car les threads peuvent finir dans un ordre different a chaque execution*/
	}
}
