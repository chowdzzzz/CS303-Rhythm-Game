public class Note {
 
 public double delay; // the delay time before next note is drawn onto frame
 public int slot; // the slot of the note (arrow)
 
 /**
  * Distinguishes which slot the note is & its delay.
  */ 
 public Note(int slot, double delay) {
  this.slot = slot;
  this.delay = delay;
 }
}
