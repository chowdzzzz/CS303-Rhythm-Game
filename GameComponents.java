import java.awt.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.util.*;

public class GameComponents extends JPanel {
  private final GameFrame mf;
  private ArrayList<NoteGraphic> noteList = new ArrayList<NoteGraphic>();
  private ListIterator<NoteGraphic> itr;
  private boolean startgame = false;
  private int noteSpeed = 5; 
  private int yBound = 450;
  private int perfectRange = 15;
  private int goodRange = 30;
  private int rangeClick = 100;
  private int noteWidth = 130;
  private int frameX = 800; // Frame Width
  // Image variables for falling arrows
  private BufferedImage up;
  private BufferedImage left;
  private BufferedImage down;
  private BufferedImage right;
  private BufferedImage background;
  // Image variables for stationary bottom arrows
  private BufferedImage upBottom;
  private BufferedImage leftBottom;
  private BufferedImage downBottom;
  private BufferedImage rightBottom;
  private BufferedImage upPressed;
  private BufferedImage leftPressed;
  private BufferedImage downPressed;
  private BufferedImage rightPressed;
  // Variables for accuracy, score, & streak
  private Color textColor = Color.white;
  private String accuracyText = "";
  private String scoreText = "";
  private String streakText = "";
  private String streakCountText = "";
  private String longestStreakText = "";
  public static int userScore = 0;
  private static int streak = 0;
  private static int longestStreak = 0;
  
  
  public GameComponents(GameFrame mf) {
    this.mf = mf;
    setLayout(null);   
    
    try{
      up = ImageIO.read(getClass().getResourceAsStream("ArrowUp.png"));
      right = ImageIO.read(getClass().getResourceAsStream("ArrowRight.png"));
      down = ImageIO.read(getClass().getResourceAsStream("ArrowDown.png"));
      left = ImageIO.read(getClass().getResourceAsStream("ArrowLeft.png"));
      
      upPressed = ImageIO.read(getClass().getResourceAsStream("upPressed.png"));
      rightPressed = ImageIO.read(getClass().getResourceAsStream("rightPressed.png"));
      downPressed = ImageIO.read(getClass().getResourceAsStream("downPressed.png"));
      leftPressed = ImageIO.read(getClass().getResourceAsStream("leftPressed.png"));
      
      background = ImageIO.read(getClass().getResourceAsStream("CROWD2.jpg"));
    } catch (IOException e){ e.printStackTrace(); } 
    
    setBackground(Color.black);
    
    // Set bottom arrows to their default images.
    upBottom = up;
    leftBottom = left;
    downBottom = down;
    rightBottom = right;
  }
  
  public void paint(Graphics g) {
    super.paint(g);
    int adjX = frameX/6, adjY = yBound; // To center objects in frame.
    g.drawImage(background, 0, 0, getWidth(), getHeight(), null);
    
    // Set Font.
    Font accuracyFont = new Font("Arial", Font.BOLD, 50);
    Font scoreFont = new Font("Arial", Font.BOLD, 35);
    Font comboFont = new Font("Arial", Font.BOLD, 35);
    FontMetrics fontMetrics = g.getFontMetrics();
    
    // Draw falling arrows.
    try{
      itr = noteList.listIterator();
      while(itr.hasNext()) {
        NoteGraphic b2 = (NoteGraphic)itr.next();
        b2.draw(g);
        
      }
    } catch (Exception e) {           
      e.printStackTrace();
    }
    
    // Draw stationary bottom arrows.
    for (int i = 0; i < 4; i++) {
      switch (i){
        case 0:
          g.drawImage(leftBottom, adjX + i * noteWidth + 2, adjY + 2, 100, 100, null);
          break;
        case 1:
          g.drawImage(downBottom, adjX + i * noteWidth + 2, adjY + 2, 100, 100, null);
          break;
        case 2:
          g.drawImage(upBottom, adjX + i * noteWidth + 2, adjY + 2, 100, 100, null);
          break;
        case 3:
          g.drawImage(rightBottom, adjX + i * noteWidth + 2, adjY + 2, 100, 100, null);
          break;
      }
    }
    
    // For combo text.
    g.setFont(comboFont);
    g.setColor(Color.white);
    g.drawString(streakText + " " + streakCountText, 500, 45);
    // For accuracy text
    g.setFont(accuracyFont);
    g.setColor(textColor);
    g.drawString(accuracyText, ((mf.getWidth() / 3) + 22), 230);
    // For score text
    g.setFont(scoreFont);
    g.setColor(Color.white);
    
    if (NoteMap.finishedConstruct){
      g.drawString("SCORE: " + scoreText, 20 , 35); // For score text.
      g.drawString("Longest Streak: " + longestStreakText, 20 , 70); // For longest streak text.
    } else {
      g.drawString("Constructing Notes... Please wait (" +((int)((NoteMap.delayTime/40)*100)) + "%)", 20, 35);
    }
  }
  
  /**
   * keyPress Action: Determines the keypress range for 
   * accuracy and changes the bottom arrow images.
   * @param j: the position in keyNoteCode array that holds the keyCode value of pressed key.
   */
  public void keyPress(int j) {
    if (j == 0) {leftBottom = leftPressed;}
    if (j == 1) {downBottom = downPressed;}
    if (j == 2) {upBottom = upPressed;}
    if (j == 3) {rightBottom = rightPressed;}
    
    for (NoteGraphic note : noteList) {
      if (note.second.slot == j) {
        if (!note.isClick) {
          int y = note.first;
          int diff = Math.abs(y - yBound);
          note.isClick = true;
          // Perfect Range.
          if (diff < perfectRange) { 
            System.out.println("PERFECT"); // Line used for testing.
            userScore = userScore+ 1000;
            scoreText = String.valueOf(userScore);  
            accuracyText = "PERFECT!"; 
            textColor = Color.cyan;
            setStreak();
          // Good Range.
          } else if (diff < goodRange) { 
            System.out.println("GOOD"); // Line used for testing.
            userScore = userScore+ 500;
            scoreText = String.valueOf(userScore);
            accuracyText = "  GOOD  ";
            textColor = Color.green;
            setStreak();
          // Miss range.
          } else if (diff < rangeClick) { 
            System.out.println("MISS"); // Line used for testing.
            scoreText = String.valueOf(userScore);
            accuracyText = "  MISS  ";
            textColor = Color.red;
            streakCountText=""; streakText=""; streak=0; 
          } else {
            note.isClick = false;
            streakCountText=""; streakText=""; streak=0; 
          }
        }
        break;
      }
    }
    repaint();
  }
  
  /**
   * keyRelease Action: Change bottom arrows back to the default arrow images.
   * @param j: the position in keyNoteCode array that holds the keyCode value of released key.
   */
  public void keyRelease(int j) {
    if (j == 0) {leftBottom = left;}
    if (j == 1) {downBottom = down;}
    if (j == 2) {upBottom = up;}
    if (j == 3) {rightBottom = right;}
  }
  
  /**
   * Increase the streak by one. If streak is greater than longestStreak,
   * then assign streak as the new value of longestStreak. 
   */ 
  public void setStreak() {
    streak++;
    if (streak > longestStreak) { 
      longestStreak = streak; 
      longestStreakText = String.valueOf(longestStreak); 
    } 
    streakText="STREAK: ";
    streakCountText = String.valueOf(streak);
  }
  
  /**
   * Add new note into notelist to display in game canvas.
   * @param note: the slot and delay of new note to be added into list.
   */
  public void addNote(Note note) {
    itr = noteList.listIterator();
    NoteGraphic b = (new NoteGraphic(note, noteWidth));
    itr.add(b);
  }
  
  /**
   * Update note's location by incrementing the y-coordinate
   * by the noteSpeed value. Remove note if out of bounds.
   */
  public void updateNotes() {
    try {
      itr = noteList.listIterator();
      while(itr.hasNext()) {
        NoteGraphic b = (NoteGraphic)itr.next();
        b.first += noteSpeed;
        if (b.first > yBound) // Remove if current location is greater than yBound.
          itr.remove();
      }
      if (!itr.hasNext()) {} 
      repaint();
    } catch (Exception e) {}
  }
  
  /**
   * Start the game.
   */
  public void startGame() {
    if (!startgame) {
      startgame = true;
      noteList.clear();
      new ThreadGroup(this);
      repaint();
    }
  }
  
  /**
   * Check if game is done.
   * @return true if noteList is empty, false otherwise.
   */
  public boolean isFinish() {
    return noteList.isEmpty();
  }
  
  /**
   * When game is done, reset game values, kill clip, & open HighScore frame.
   */
  public void finish() {
    startgame = false;
    System.out.println("FINISH"); // Line used for testing.
    mf.clip.stop();
    mf.dispose(); 
    // Open HighScore frame
    HighScore frame = new HighScore(userScore);
    // Reset game values for new game.
    scoreText = "";
    accuracyText = ""; 
    NoteMap.notes.clear();
    NoteMap.finishedConstruct = false;
    userScore = 0;
    longestStreak = 0;
    longestStreakText = "";
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setVisible(true);    
  }
}