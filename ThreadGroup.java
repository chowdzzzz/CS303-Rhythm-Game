public class ThreadGroup {
  
  private GameComponents mp;
  private Thread noteThread;
  private Thread gameThread;
  private int fps = 60; // the new standard framerate
  private long frameRate = 1000 / fps; // frame rate  
  private boolean hasNext = true;
  
  public ThreadGroup(GameComponents mp) {
    this.mp = mp;
    noteThread();
    gameThread();
  }
  
  /**
   * Thread for adding a new note in note list (GameComponents class).
   */
  private void noteThread() {
    noteThread = new Thread() {
      
      public void run() {
        try {
          NoteMap randomNotes = new NoteMap(); // Create notes.
          for (int i = 0; i < randomNotes.notes.size(); i++) {
            hasNext = true;
            int timer = (int) (randomNotes.notes.get(i).delay * 1000);
            if (timer > 0) // If there is a delay put thread in waiting state.
              sleep(timer); // Sleep for specified delay time.
            mp.addNote(randomNotes.notes.get(i));
          }
        } catch (Exception e) {
          e.printStackTrace();
        }
        hasNext = false;
      }
    };
    noteThread.start();
  }
  
  /**
   * Thread for updating note position (Y-coordinate).
   */
  private void gameThread() {
    gameThread = new Thread() {
      
      public void run() {
        try {
          while (!mp.isFinish() || hasNext) {
            sleep(frameRate);
            mp.updateNotes();
          }
        } catch (Exception e) {
          e.printStackTrace();
        }
        mp.finish(); // End Game.
      }
    };
    gameThread.start();
  }
  
}