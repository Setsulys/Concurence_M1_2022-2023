  package fr.uge.tp4.ex3;

import java.util.HashMap;

public class Vote {
  
  private final HashMap<String,Integer>map = new HashMap<>();
  private final int maxVote;
  private int nbVote;
  private String winner;
  
  
  public Vote(int maxVote) {
    if(maxVote < 0) {
      throw new IllegalArgumentException();
    }
    this.maxVote = maxVote;
  }
  
  private void updateMap(String balot) {
    synchronized (map){
      nbVote++;
      map.merge(balot, 1, Integer::sum);
    }
  }
  
  public String vote(String balot) throws InterruptedException {
    synchronized (map) {
      if (nbVote == maxVote) {
        return winner;
      }
      updateMap(balot);
      if(nbVote == maxVote) {
        map.notifyAll();
        winner = computeWinner();
      }
      while(nbVote != maxVote) {
        map.wait();
      }
      return winner;
    }
  }

  private String computeWinner() {
    var score = - 1;
    String winner = null;
      for(var e : map.entrySet()) {
        var key = e.getKey();
        var value = e.getValue();
        if(value > score || (value == score && key.compareTo(winner)<0)) {
          winner = key;
          score = value;
        }
      }
    return winner;
  }

  public static void main(String[] args) throws InterruptedException {
    var vote = new Vote(4);
    Thread.ofPlatform().start(() -> {
      try {
        Thread.sleep(2_000);
        System.out.println("The winner is " + vote.vote("un"));
      } catch (InterruptedException e) {
        throw new AssertionError(e);
      }
    });
    Thread.ofPlatform().start(() -> {
      try {
        Thread.sleep(1_500);
        System.out.println("The winner is " + vote.vote("zero"));
      } catch (InterruptedException e) {
        throw new AssertionError(e);
      }
    });
    Thread.ofPlatform().start(() -> {
      try {
        Thread.sleep(1_000);
        System.out.println("The winner is " + vote.vote("un"));
      } catch (InterruptedException e) {
        throw new AssertionError(e);
      }
    });
    System.out.println("The winner is " + vote.vote("zero"));
  }
}
