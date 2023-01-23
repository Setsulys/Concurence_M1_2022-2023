package fr.uge.tp3.ex1;

import java.util.ArrayList;
import java.util.stream.IntStream;

public class HelloListBug {
	private final static Object lock = new Object();
	
	public static void main(String[] args) throws InterruptedException {
		var nbThreads = 4;
		var threads = new Thread[nbThreads]; 
		var list = new ArrayList<Integer>(5_000 * nbThreads);
		

		IntStream.range(0, nbThreads).forEach(j -> {
			Runnable runnable = () -> {
				for (var i = 0; i < 5_000; i++) {
					synchronized (lock) {
						list.add(i);
					}
				}
			};
			threads[j] = Thread.ofPlatform().start(runnable);
		});
		
		for (Thread thread : threads) {
			thread.join();
	    }
		System.out.println("taille de la liste:" + list.size());
	}
}

//Rappeler quelles doivent être les propriétés de l'objet qui sert de lock.
/*
 * Les objets qui servent de lock doivent etres déclarés comme des champs privates final car on ne veut pas que la reference de l'objet soit changé
 * L'objet doit etre crée dans un constructeur de classe
 * Et l'objet ne doit pas fournir d'accesseurs à ses champs
 */





