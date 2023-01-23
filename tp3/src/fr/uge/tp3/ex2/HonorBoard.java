package fr.uge.tp3.ex2;

public class HonorBoard {
  private String firstName;
  private String lastName;
  private final static Object lock = new Object();
  
  public void set(String firstName, String lastName) {
    this.firstName = firstName;
    this.lastName = lastName;
  }
  
  @Override
  public String toString() {
    return firstName + ' ' + lastName;
  }
  
  public static void main(String[] args) {
    var board = new HonorBoard();
    Thread.ofPlatform().start(() -> {
      for(;;) {
    	  synchronized (lock) {
    		  board.set("Mickey", "Mouse");
		}
        
      }
    });
    
    Thread.ofPlatform().start(() -> {
      for(;;) { 
    	  synchronized (lock) {
    		  board.set("Donald", "Duck");
    	  }
      }
    });
    
    Thread.ofPlatform().start(() -> {
      for(;;) {
    	  synchronized (lock) {
    		  System.out.println(board);
    	  }
      }
    });
  }
  
}



//Expliquer pourquoi la classe HonorBoard n'est pas thread-safe.
//Si vous ne voyez pas, faites un grep "Mickey Duck" sur la sortie du programme et donner un scénario pouvant mener à cet affichage.
/*
 * La classe n'est pas thread-safe car on accede a des données sans que sa soit synchronized
 */


//Maintenant que votre classe est thread-safe, peut-on remplacer la ligne :
//System.out.println(board);
//par la ligne :
//System.out.println(board.firstName() + ' ' + board.lastName());
//avec les deux accesseurs définis comme d'habitude ?

/*Non car les accesseurs ne sont pas thread safe
 * 
 */


