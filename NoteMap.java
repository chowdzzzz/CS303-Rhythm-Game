import java.util.*;
import javax.swing.*;

public class NoteMap extends JFrame {
  public static boolean finishedConstruct = false;
  public static ArrayList <Note> notes = new ArrayList <Note>();
  public static double delayTime = 0.0;
  
  public NoteMap() {
    // delay time = delay time + (new note * length of time on the screen) + rand2  
    delayTime = 0; // NOTE: delay time is not in milliseconds
    
    // Create notes for game while delayTime <= 40 (Length of gameplay).
    while (delayTime <= 40) {
      int rand1 = (int) (Math.random()*4);  // rand1: arrow slot
      int noteType = (int) (Math.random()*4); 
      double rand2 = 0; // rand2: arrow delay
      switch(noteType) {
        case 0:
          rand2 = 0;      // DOUBLE NOTE STACK
          break;
        case 1:
          rand2 = 0.500;  // QUARTER NOTE
          break;
        case 2:
          rand2 = 0.250;  // EIGHTH NOTE
          break;
        case 3:
          rand2 = 0.500;  // QUARTER NOTE
          break;   
      } 
      notes.add(new Note (rand1, rand2));
      delayTime = delayTime + rand2;
      System.out.println("DelayTime: " + delayTime); // Line used for testing.
    }
    finishedConstruct = true;
    System.out.println("FINISHED MAKING NOTES!"); // Line used for testing.
    GameFrame.play(); // Play song for gameplay.
  }
}