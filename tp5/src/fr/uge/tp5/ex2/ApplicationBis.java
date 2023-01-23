package fr.uge.tp5.ex2;

import java.util.List;

public class ApplicationBis {
  public static void main(String[] args) throws InterruptedException {
    var rooms = List.of("bedroom1", "bedroom2", "kitchen", "dining-room", "bathroom", "toilets");
    var globTemp = new RoomsTemp2(rooms.size());
      for (String room : rooms) {
        Thread.ofPlatform().start(()->{
        try {
          for(;;) {
            var temperature = Heat4J.retrieveTemperature(room);
            System.out.println("Temperature in room " + room + " : " + temperature);
            globTemp.add(room, temperature);
          }
        } catch (InterruptedException e) {
          System.out.println(e.getMessage());
        }
  
        });
      }
      for(;;) {
        System.out.println(globTemp.globTemp());
      }

  }
  
}