package fr.uge.tp11.ex1;

public class HonorBoard {
  private volatile String firstName;
  private volatile String lastName;
  
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
        board.set("Mickey", "Mouse");
      }
    });
    
    Thread.ofPlatform().start(() -> {
      for(;;) {
        board.set("Donald", "Duck");
      }
    });
    
    Thread.ofPlatform().start(() -> {
      for(;;) {
        System.out.println(board);
      }
    });
  }
}

//Est-il toujours possible de voir des affichages de Mickey Duck ou Donald Mouse ?
/*
 * Si, on voit les affichages Mickey Duck ou Donald Mouse .
 */
