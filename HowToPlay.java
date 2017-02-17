import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.BorderLayout;
import java.awt.Container;
import javax.swing.JFrame;
import java.io.*;
import javax.sound.sampled.*;

public class HowToPlay extends JFrame {
  private JButton button;
  private JLabel label;
  private JLabel label1;
  private JLabel label2;
  private JLabel label3;
  private JLabel label4;
  private JLabel label5;
  private JLabel label6;
  private JPanel panel;
  private Clip clip;
  
  public HowToPlay() {
    setResizable(false);
    setBackground(new Color(178, 74, 126)); //purple
    createComponents();
    setTitle("RHYTHM GAME: How To Play");
    //song time length <= 1MB (MAC OS X)
    String FileName = "MENUMUSIC.wav"; // accepts music formats: wav, ogg
    try {
      File file = new File(FileName);
      clip = AudioSystem.getClip();
      clip.open(AudioSystem.getAudioInputStream(file));
      clip.start();
      clip.loop(Clip.LOOP_CONTINUOUSLY);
    } catch (Exception e) { System.err.println(e.getMessage()); }
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
  
  private void createComponents() { 
    panel = new JPanel(); 
    panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS)); 
    panel.setBackground(Color.WHITE);
    
    //A border that puts 10 extra pixels at the sides of panel - createEmptyBorder(int top, int left, int bottom, int right)
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
    
    label = new JLabel("Rhythm Game");
    label.setFont(new Font("Copperplate", Font.BOLD, 30));     
    
    label1 = new JLabel("How To Play:");
    label1.setFont(new Font("Copperplate", Font.BOLD, 40));    
    label1.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
    
    label2 = new JLabel("    1) There are 4 arrow slots for the respective directions on the bottom of the screen.");
    label2.setFont(new Font("Copperplate", Font.BOLD, 26));    
    
    label3 = new JLabel("    2) Press the respective arrow key once it is aligned in the slot.");
    label3.setFont(new Font("Copperplate", Font.BOLD, 26));     
    
    label4 = new JLabel("    3) You will receive points based on your the precision of your timing.");
    label4.setFont(new Font("Copperplate", Font.BOLD, 26));     
    
    label5 =new JLabel("    4) If you are able to score within the TOP-3 Players to have played our game, your score will be saved!");
    label5.setFont(new Font("Copperplate", Font.BOLD, 26));     
    
    label6 = new JLabel("                                                                              HAPPY PLAYING! ");
    label6.setFont(new Font("Copperplate", Font.BOLD, 26));    
    
    panel.setBackground(Color.BLACK);
    label.setForeground(Color.WHITE);
    label1.setForeground(Color.WHITE);
    label2.setForeground(Color.WHITE);
    label3.setForeground(Color.WHITE);
    label4.setForeground(Color.WHITE);
    label5.setForeground(Color.WHITE);
    label6.setForeground(Color.WHITE);
    
    panel.add(label);
    panel.add(label1);
    panel.add(label2);
    panel.add(label3);
    panel.add(label4);
    panel.add(label5);
    panel.add(label6);
    
    // GIF Background
    JLabel imageLabel = new JLabel();
    ImageIcon ii = new ImageIcon("equalizer.gif");
    imageLabel.setIcon(ii);
    JPanel panel2= new JPanel();
    panel2.add(imageLabel);
    panel2.setBackground(Color.BLACK);
    
    Container c = getContentPane();
    c.setLayout(new BorderLayout());
    c.add(panel, BorderLayout.CENTER);
    c.add(button, BorderLayout.NORTH);
    c.add(panel2, BorderLayout.SOUTH);
    
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    pack(); // This automatically sizes the JFrame based on the size of the components. 
    setVisible(true);
  }
}