import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.sound.sampled.*;
import javax.swing.*;

public class GameFrame extends JFrame { 
  private Container canvas;
  private GameComponents mp;
  public static Clip clip;
  
  public GameFrame() {
    setTitle("RHYTHM GAME: Game");
    setResizable(false);
    canvas = getContentPane();
    canvas.setLayout(new BorderLayout());
    
    mp = new GameComponents(this);
    canvas.add(mp, BorderLayout.CENTER);
    mp.setVisible(true);
    mp.startGame();
    
    /**
     * Key listener for key events.
     */
    mp.addKeyListener(new KeyListener() {
      
      @Override
      public void keyTyped(KeyEvent e) {
      }
      
      @Override
      public void keyReleased(KeyEvent e) {
        for (int j = 0; j < KeyVariable.keyNoteCode.length; j++) {
          if(e.getKeyCode() == KeyVariable.keyNoteCode[j]){ 
            mp.keyRelease(j);
            return;
          }
        }
      }
      
      @Override
      public void keyPressed(KeyEvent e) {
        for (int j = 0; j < KeyVariable.keyNoteCode.length; j++) {
          if(e.getKeyCode() == KeyVariable.keyNoteCode[j]){ 
            mp.keyPress(j);
            return;
          }
        }
      }
    });
    mp.setFocusable(true); // Line needed to recognize key press.
    
    this.setSize(800, 600); // Frame size
    this.setLocationRelativeTo(null);
  }
  
  /**
   * Play music for gameplay.
   */ 
  public static void play() {
    // NOTE: song time length <= 1MB (on MAC OS X)
    String FileName = "GameplayMusic.wav"; // Music file formats: wav, ogg
    try {
      File file = new File(FileName);
      clip = AudioSystem.getClip();
      clip.open(AudioSystem.getAudioInputStream(file));
      clip.start();
      clip.loop(Clip.LOOP_CONTINUOUSLY);
    } catch (Exception e) { System.err.println(e.getMessage()); }
  }
  
}