import java.awt.Graphics;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;

public class NoteGraphic {
  
  public int first;
  public boolean isClick = false;
  public Note second;
  public int width;
  
  private BufferedImage up;
  private BufferedImage down;
  private BufferedImage right;
  private BufferedImage left;
  
  public NoteGraphic(Note second, int width) {
    
    this.width = width;
    this.second = second;
    
    try {
      up = ImageIO.read(getClass().getResourceAsStream("ArrowUp.png"));
      right = ImageIO.read(getClass().getResourceAsStream("ArrowRight.png"));
      down = ImageIO.read(getClass().getResourceAsStream("ArrowDown.png"));
      left = ImageIO.read(getClass().getResourceAsStream("ArrowLeft.png"));
    } catch (IOException e){ e.printStackTrace(); } 
  }
  
  /**
   * draw falling arrows
   * @param g
   */
  public void draw(Graphics g) {
    int y = this.first; // Y-coordinate
    Note note = this.second;
    int slot = note.slot;  // slot of note (arrow)
    switch (slot) {
      case 0:
        g.drawImage( left, slot * width + 133, y, 100, 100, null);
        break;
      case 1:
        g.drawImage( down, slot * width + 133, y, 100, 100, null);
        break;
      case 2:
        g.drawImage( up, slot * width + 133, y, 100, 100, null);
        break;
      case 3:
        g.drawImage( right, slot * width + 133, y, 100, 100, null);
        break;
    }
  }
}