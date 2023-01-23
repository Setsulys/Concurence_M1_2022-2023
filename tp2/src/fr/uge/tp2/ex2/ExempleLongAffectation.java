package fr.uge.tp2.ex2;

class ExampleLongAffectation {
	  long l = -1L;

	  public static void main(String[] args) {
	    var e = new ExampleLongAffectation();
	    Thread.ofPlatform().start(() -> {
	      System.out.println("l = " + e.l);
	    });
	    e.l = 0;
	  }
	}

//Quand on exécute le code précédent, quels peuvent être les différents affichages constatés ?
/*  l=0
 * (l=-1)
 * Comme on utilise un long il est possible que ca se déshédule entre les bits de poids fort et poids faible d'ou les deux
 * affichages differents
 */