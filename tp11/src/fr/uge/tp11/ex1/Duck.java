package fr.uge.tp11.ex1;

public class Duck {
  private volatile long a = 1;
  private volatile long b = 1;

  public void foo() {
      a = 2;
      b = -1;
  }

  public static void main(String[] args) {
      var duck = new Duck();
      Thread.ofPlatform().start(() -> {
          System.out.println("b = " + duck.b);
          System.out.println("a = " + duck.a);
      });
      Thread.ofPlatform().start(duck::foo);
  }
} 

//Quels sont les affichages possibles
/*
 *  b = 1
 *  a = 1
 */
