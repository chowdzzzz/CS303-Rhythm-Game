import java.awt.event.KeyEvent;

public class KeyVariable {

/**
 * Store KeyEvent fields in an array to simplify code in other classes.
 */ 
 public static int []keyNoteCode = {
   KeyEvent.VK_LEFT, // Constant for the non-numpad left arrow key.
   KeyEvent.VK_DOWN, // Constant for the non-numpad down arrow key.
   KeyEvent.VK_UP, // Constant for the non-numpad up arrow key.
   KeyEvent.VK_RIGHT // Constant for the non-numpad right arrow key.
 };
}
