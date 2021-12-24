//Imports
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.ActionEvent;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.Dimension;
import javax.swing.ImageIcon;
import javax.swing.BorderFactory;
/*StartFrame.java
 * @version 1.0
 * @author Trotyl
 * @since 2019.Dec.16
 * A class for the starting frame*/
class StartFrame extends JFrame {
   JFrame thisFrame;
   static int cur=1;
   static Toolkit tk = Toolkit.getDefaultToolkit();
   static Image c1 = tk.getImage("image/cursor1.png");
   static Image c4 = tk.getImage("image/cursor4.png");
   static Cursor cursor1 = tk.createCustomCursor(c1 , new Point(0,0), "img");
   static Cursor cursor4=tk.createCustomCursor(c4 , new Point(0,0), "img");
   //Constructor - this runs first
   StartFrame(){ 
      super("Start Screen");
      this.thisFrame = this; 
      //configure the window
      this.setSize(900,600);    
      this.setLocationRelativeTo(null); //start the frame in the center of the screen
      this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
      this.setResizable (false);
      
      //Display the frame without border and invisible
      this.setUndecorated(true);
      setBackground(new Color(0,0,0,0));
      
      //Create a Panel for stuff
      JPanel decPanel = new DecoratedPanel();
      JPanel mainPanel = new JPanel();
      mainPanel.setLayout(new BorderLayout());
      mainPanel.setBackground(new Color(0, 0, 0, 0));
      mainPanel.setPreferredSize(new Dimension(800,300));
      
      
      //Create a JButton for the centerPanel
      ImageIcon sb =new ImageIcon("image/startbutton.png");
      ImageIcon qb=new ImageIcon("image/quitbutton.png");
      
      this.setCursor(cursor1);
      JButton startButton = new JButton(sb);
      JButton quitButton=new JButton(qb);
      startButton.setBackground(new Color(0, 0, 0, 0));
      startButton.setRolloverIcon(new ImageIcon("image/startbuttonpressed.png"));
      startButton.setBorder(BorderFactory.createEmptyBorder());
      startButton.setFocusPainted(false);
      startButton.addActionListener(new ActionListener(){
         public void actionPerformed(ActionEvent e){
            thisFrame.dispose();
            new GameFrame(); //create a new FunkyFrame (another file that extends JFrame)
         }
      });
      startButton.addMouseListener(new MouseListener() {
         
         @Override
         public void mouseClicked(MouseEvent e) {
            
            
         }
         
         @Override
         public void mousePressed(MouseEvent e) {
            
            
         }
         
         @Override
         public void mouseReleased(MouseEvent e) {
            
            
         }
         
         @Override
         public void mouseEntered(MouseEvent e) {
            
            thisFrame.setCursor(cursor4);
            
         }
         
         @Override
         public void mouseExited(MouseEvent e) {
            
            thisFrame.setCursor(cursor1);
         }
         
         
      });
      quitButton.setBackground(new Color(0, 0, 0, 0));
      quitButton.setRolloverIcon(new ImageIcon("image/quitbuttonpressed.png"));
      quitButton.setBorder(BorderFactory.createEmptyBorder());
      quitButton.setFocusPainted(false);
      quitButton.addActionListener(new ActionListener(){
         public void actionPerformed(ActionEvent e){
            System.exit(0);
         }
      });
      quitButton.addMouseListener(new MouseListener(){
         
         @Override
         public void mouseClicked(MouseEvent e) {
            
            
         }
         
         @Override
         public void mousePressed(MouseEvent e) {
            
            
         }
         
         @Override
         public void mouseReleased(MouseEvent e) {
            
            
         }
         @Override
         public void mouseEntered(MouseEvent e) {
            
            thisFrame.setCursor(cursor4);
         }
         @Override
         public void mouseExited(MouseEvent e) {
            
            thisFrame.setCursor(cursor1);
         }
      });
      JPanel bottomPanel = new JPanel();
      bottomPanel.setBackground(new Color(0, 0, 0, 0));
      bottomPanel.add(startButton,BorderLayout.WEST);
      bottomPanel.add(quitButton,BorderLayout.EAST);
      //Add all panels to the mainPanel according to border layout
      mainPanel.add(bottomPanel,BorderLayout.NORTH);
      decPanel.add(mainPanel);
      //add the main panel to the frame
      this.add(decPanel);
      //Start
      this.setVisible(true);
   }
   //INNER CLASS - Overide Paint Component for JPANEL
   private class DecoratedPanel extends JPanel {
      DecoratedPanel() {
         this.setBackground(new Color(0,0,0,0));
      }
      public void paintComponent(Graphics g) { 
         super.paintComponent(g);     
         Image pic = new ImageIcon("image/start.png" ).getImage();
         g.drawImage(pic,0,0,null); 
      }
   }
}//end of StartFrame.java