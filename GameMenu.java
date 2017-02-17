import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.Border;
import java.io.*;
import javax.sound.sampled.*;

public class GameMenu extends JFrame {
  private JButton button1;
  private JButton button2;
  private JButton button3;
  private JButton button4;
  private JLabel label;
  private JPanel panel;
  private Clip clip;
  
  public GameMenu() {
    setResizable(false);
    setBackground(new Color(178, 74, 126));
    createComponents();
    setTitle("RHYTHM GAME: Main Menu");
    // NOTE: song time length <= 1MB (on MAC OS X)
    String FileName = "MENUMUSIC.wav";
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
      if (event.getSource() == button1) {
        System.out.println("START"); // Line used for testing.
        dispose();
        JFrame frame = new GameFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        clip.stop();
      }
      if (event.getSource() == button2) {
        System.out.println("HOW TO PLAY"); // Line used for testing.
        dispose();
        JFrame frame = new HowToPlay();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        clip.stop();
      }
      if (event.getSource() == button3) {
        System.out.println("HIGH SCORES"); // Line used for testing.
        dispose();
        JFrame highScores = new HighScore(0);
        clip.stop();
      }
      if (event.getSource() == button4) {
        System.out.println("EXIT"); // Line used for testing.
        dispose(); // Closes window.
        clip.stop();
      }
    }            
  }
  
  private void createComponents() { 
    panel = new JPanel(); 
    panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS)); 
    panel.setBackground(Color.BLACK);
    
    //A border that puts 10 extra pixels at the sides and bottom of panel - createEmptyBorder(int top, int left, int bottom, int right)
    panel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10)); 
    
    // Button: raised border effect
    Border raisedbevel = BorderFactory.createRaisedBevelBorder();
    
    button1 = new JButton("START");
    button1.setOpaque(true);
    button1.setFont(new Font("Copperplate", Font.BOLD, 30));     
    button1.setBackground(Color.WHITE);
    button1.setForeground(Color.BLACK);
    button1.setOpaque(true);
    button1.setBorder(raisedbevel);
    ActionListener listener = new ChoiceListener();
    button1.addActionListener(listener);      
    
    button2 = new JButton("HOW TO PLAY");
    button2.setOpaque(true);
    button2.setFont(new Font("Copperplate", Font.BOLD, 30));     
    button2.setBackground(Color.WHITE);
    button2.setForeground(Color.BLACK);
    button2.setOpaque(true);
    button2.setBorder(raisedbevel);
    ActionListener listener2 = new ChoiceListener();
    button2.addActionListener(listener2);    
    
    button3 = new JButton("HIGH SCORES");
    button3.setOpaque(true);
    button3.setFont(new Font("Copperplate", Font.BOLD, 30));     
    button3.setBackground(Color.WHITE);
    button3.setForeground(Color.BLACK);
    button3.setOpaque(true);
    button3.setBorder(raisedbevel);
    ActionListener listener3 = new ChoiceListener();
    button3.addActionListener(listener3);  
    
    button4 = new JButton("EXIT");
    button4.setOpaque(true);
    button4.setFont(new Font("Copperplate", Font.BOLD, 30));     
    button4.setBackground(Color.WHITE);
    button4.setForeground(Color.BLACK);
    button4.setOpaque(true);
    button4.setBorder(raisedbevel);
    ActionListener listener4 = new ChoiceListener();
    button4.addActionListener(listener4);
    
    // Center buttons 
    button1.setAlignmentX(Component.CENTER_ALIGNMENT);      
    button2.setAlignmentX(Component.CENTER_ALIGNMENT);
    button3.setAlignmentX(Component.CENTER_ALIGNMENT);
    button4.setAlignmentX(Component.CENTER_ALIGNMENT);
    
    label = new JLabel("                    .:..:..:RHYTHM GAME..:.::..:.:");
    label.setFont(new Font("Copperplate", Font.BOLD, 30));     
    
    panel.add(button1);
    panel.add(Box.createRigidArea(new Dimension(0,5))); // Creates space between buttons
    panel.add(button2);
    panel.add(Box.createRigidArea(new Dimension(0,5)));
    panel.add(button3);
    panel.add(Box.createRigidArea(new Dimension(0,5)));
    panel.add(button4);
    
    JLabel imageLabel = new JLabel();
    ImageIcon ii = new ImageIcon("equalizer.gif");
    imageLabel.setIcon(ii);
    JPanel panel2= new JPanel();
    panel2.add(imageLabel);
    panel2.setBackground(Color.BLACK);
    
    Container c = getContentPane();
    c.setLayout(new BorderLayout());
    c.add(panel2, BorderLayout.SOUTH);
    c.add(label, BorderLayout.NORTH);
    c.add(panel, BorderLayout.CENTER);
    
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    pack(); // This automatically sizes the JFrame based on the size of the components. 
    setVisible(true);
  }
}