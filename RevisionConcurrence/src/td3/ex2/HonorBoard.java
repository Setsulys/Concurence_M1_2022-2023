package td3.ex2;

public class HonorBoard {
  private String firstName;
  private String lastName;
  private Object lock = new Object();
  
  public void set(String firstName, String lastName) {
    this.firstName = firstName;
    this.lastName = lastName;
  }
  
  @Override
  public String toString() {
	  synchronized (lock) {
		  return firstName + ' ' + lastName;
	  }
  }
  
  public String firstName() {
	  	synchronized (lock) {
	  		return firstName;
	  	}
  }
  public String lastName() {
	  synchronized (lock) {
		  return lastName;
	  }
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
        //System.out.println(board);
        System.out.println(board.firstName() + ' ' + board.lastName());
      }
    });
  }
}
