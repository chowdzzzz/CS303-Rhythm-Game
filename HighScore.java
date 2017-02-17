import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.sound.sampled.*;
import javax.swing.*;
import javax.swing.border.Border;
import java.util.*;

public class HighScore extends JFrame {
  private Clip clip; 
  private JButton button;
  private static Container c;
  private static JLabel label;
  private static JLabel label1;
  private static JLabel label2;
  private static JLabel label3;
  private static JLabel label4;
  private static JPanel panel;
  private static JPanel namePanel = new JPanel();

  private static PrintWriter out;
  private static JOptionPane namePane;
  private static int newIndex;
  private static int userScore;
  private static String[] topUsers = {"", "", ""};
  private static int[] score = {0, 0, 0};
  private boolean topScore = false;
  
  public HighScore(int userScore) {
    this.userScore = userScore;
    setResizable(false);
    setBackground(new Color(178, 74, 126)); 
    setSize(700,700);
    
    setTitle("RHYTHM GAME: High Scores");
    //song time length <= 1MB (MAC OS X)
    String FileName = "MENUMUSIC.wav"; //formats wav, ogg
    try {
      File file = new File(FileName);
      clip = AudioSystem.getClip();
      clip.open(AudioSystem.getAudioInputStream(file));
      clip.start();
      clip.loop(Clip.LOOP_CONTINUOUSLY);
      readScores();
    } catch (Exception e) { System.err.println(e.getMessage()); }
    createComponents(userScore);
    // Check if users score is a high score.
    for (int i = 0; i < 3 ; i++) {
      if (userScore > score[i]) { 
        int j = 2;
        while (j > i){
          score[j] = score[j-1];
          topUsers[j] = topUsers[j-1];
          j--;
        }
        
        score[i] = userScore;  
        newIndex = i;
        updateScores();
        break;
      } 
    }
  }
  
  class ChoiceListener implements ActionListener {
    public void actionPerformed(ActionEvent event) {
      if (event.getSource() == button) {
        System.out.println("WORKING BACK BUTTON"); // Line used for testing.
        setVisible(false);    
        // open GameMenu frame
        JFrame frame = new GameMenu();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        clip.stop();
      }
    }            
  }
  
  private void createComponents(int scorePassed) { 
    panel = new JPanel(); 
    panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS)); 
    panel.setBackground(Color.WHITE);
    
    //A border that puts 10 extra pixels at the sides and bottom of panel - createEmptyBorder(int top, int left, int bottom, int right)
    panel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10)); 
    
    // Button: raised border effect
    Border raisedbevel = BorderFactory.createRaisedBevelBorder();
    
    button = new JButton("BACK TO MAIN MENU");
    button.setOpaque(true);
    button.setFont(new Font("Copperplate", Font.BOLD, 30));     
    button.setBackground(Color.WHITE);
    button.setForeground(Color.BLACK);
    button.setOpaque(true);
    button.setBorder(raisedbevel);
    ActionListener listener = new ChoiceListener();
    button.addActionListener(listener);      
    
    label = new JLabel("Your Score: " + scorePassed);
    label.setFont(new Font("Copperplate", Font.BOLD, 30));
    label.setAlignmentX(Component.CENTER_ALIGNMENT); 
    
    label1 = new JLabel("--- HIGH SCORES ---");
    label1.setFont(new Font("Copperplate", Font.BOLD, 40));   
    label1.setAlignmentX(Component.CENTER_ALIGNMENT);  
    label1.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
    
    label2 = new JLabel(topUsers[0] + " - " + score[0]);
    label2.setFont(new Font("Copperplate", Font.BOLD, 30)); 
    label2.setAlignmentX(Component.CENTER_ALIGNMENT);  
    
    label3 = new JLabel(topUsers[1] + " - " + score[1]);
    label3.setFont(new Font("Copperplate", Font.BOLD, 30));  
    label3.setAlignmentX(Component.CENTER_ALIGNMENT);  
    
    label4 = new JLabel(topUsers[2] + " - " + score[2]);
    label4.setFont(new Font("Copperplate", Font.BOLD, 30));    
    label4.setAlignmentX(Component.CENTER_ALIGNMENT);  
    
    panel.setBackground(Color.BLACK);
    label.setForeground(Color.WHITE);
    label1.setForeground(Color.WHITE);
    label2.setForeground(Color.WHITE);
    label3.setForeground(Color.WHITE);
    label4.setForeground(Color.WHITE);
    
    panel.add(label);
    panel.add(label1);
    panel.add(label2);
    panel.add(label3);
    panel.add(label4);
    
    //GIF BACKGROUND
    JLabel imageLabel = new JLabel();
    ImageIcon ii = new ImageIcon("equalizer.gif");
    imageLabel.setIcon(ii);
    JPanel panel2= new JPanel();
    panel2.add(imageLabel);
    panel2.setBackground(Color.BLACK);
    
    c = getContentPane();
    c.setLayout(new BorderLayout());
    c.add(panel, BorderLayout.CENTER);
    c.add(button, BorderLayout.NORTH);
    c.add(panel2, BorderLayout.SOUTH);
    
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    pack(); // This automatically sizes the JFrame based on the size of the components. 
    setVisible(true);
  }
  
  /**
   * Use newIndex variable as the index to set user's
   * score in the score and name in the topUsers array.
   */ 
  public static void updateScores() {
    score[newIndex] = userScore;
    topUsers[newIndex] = namePane.showInputDialog("Congratuations on your new high score! Enter your name: ");
    try {
      saveTopScores();
    } catch (Exception e){}
  }
  
  /**
   * Output updated data into a text file,
   * using a delimiter(;) to separate values.
   */ 
  private static void saveTopScores() throws FileNotFoundException {
    out = new PrintWriter(new FileOutputStream("topThreeScores.txt", false));
    for (int i=0; i< 3; i++){   //Use printwriter to print out line numbering
      out.println(topUsers[i] + ";" + score[i]);
    }
    out.close();
    label.setText("Your score has been saved. Thank you for playing!");
    label2.setText(topUsers[0] + " - " + score[0]);
    label3.setText(topUsers[1] + " - " + score[1]);
    label4.setText(topUsers[2] + " - " + score[2]);
    label.setFont(new Font("Copperplate", Font.BOLD, 20));
  }
  
  /**
   * Read text file to retrieve name and scores, & 
   * store in the topUser and score array, respectively.
   */ 
  private static void readScores() throws FileNotFoundException {
    File inputFile = new File("topThreeScores.txt"); 
    Scanner in = new Scanner(inputFile); 
    
    for (int i = 0; i < 3; i++){  // There are 3 lines in text file.
      String line = in.nextLine(); 
      int userNameEnd=0;
      for (int j = 0; j<line.length(); j++){  // Read each character.
        char ch = line.charAt(j);
        if (ch == ';') { // Text file contains delimiter-separated values.
          topUsers[i] = line.substring(0,j);
          userNameEnd = j;
        }  
      }
      score[i] = Integer.parseInt(line.substring((userNameEnd+1), line.length()).trim()); 
    }
  }
}