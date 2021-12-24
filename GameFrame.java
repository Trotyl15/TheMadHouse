//Graphics &GUI imports
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Toolkit;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
//Keyboard imports
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
//Mouse imports
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.io.File;
import java.net.URI;
import java.net.URL;
import java.awt.event.MouseEvent;
/*GameFrame.java
 * @version 1.0
 * @author Trotyl
 * @since 2019.Dec.16
 * A class for the game frame*/
class GameFrame extends JFrame{
   //varibles
   public static double x=800, y;
   private static double xMap=290,yMap=630;
   static int change=0;
   static int changeX=0;
   static int changeY=0;
   static int imageX=0;
   static int imageY=0;
   static GameAreaPanel gamePanel;
   static int cur=1;
   static int indexO=0;//the index of the options
   static double darkness=3;
   static Player player;
   static Player playerOnMap;
   static String display;
   //images
   Image room1;
   Image room2;
   Image room3;
   Image paint1;
   Image paint2;
   Image door;
   Image back;
   Image map;
   Image cup;
   Image pen;
   Image button1;
   Image button2;
   Image button3;
   Image on;
   Image off;
   Image news1;
   Image news2;
   Image talk;
   Image npc2;
   Image game;
   Image card1;
   Image card2;
   Image lock;
   Image win;
   Buttons []button;
   static int card=0;
   static int ending=0;
   static int point=0;
   static Dialog dialog=new Dialog();
   static Items items=new Items();
   boolean doorOpen=false;
   static Options options[]=new Options[5];
   static Lock theLock=new Lock();
   static boolean unknownGirl=false;
   public static PlayMusic music;
   //Constructor 
   GameFrame(){
      super("The Madhouse");
      this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      Dimension sz = Toolkit.getDefaultToolkit().getScreenSize();
      this.setSize(1200,800);
      this.setBounds((int) (sz.getWidth()/2-600),(int) (sz.getHeight()/2-400),1200,800);
      this.setResizable(false);
      display="sleep";
      loadImage();//load the images
      gamePanel = new GameAreaPanel();
      this.add(new GameAreaPanel());
      MyKeyListener keyListener = new MyKeyListener();
      this.addKeyListener(keyListener);
      MyMouseWheelListener wheelListener=new MyMouseWheelListener();
      MyMouseListener mouseListener = new MyMouseListener();
      this.addMouseListener(mouseListener);
      this.addMouseMotionListener(mouseListener);
      this.addMouseWheelListener(wheelListener );
      this.requestFocusInWindow(); //make sure the this has focus   
      player=new Player((int)x,(int)y);//the player's coordinate in the rooms
      playerOnMap=new Player(50,450);//the player's coordinate on the map
      //make the buttons
      button=new Buttons[3];
      button[0]=new Buttons();
      button[1]=new Buttons();
      button[2]=new Buttons();
      //music class
      music=new PlayMusic();
      player.loadSprites();
      //Start the game loop in a separate thread
      Thread t = new Thread(new Runnable(){public void run() { animate(); }}); //start the gameLoop 
      t.start();//start the game
   } //End of Constructor  
   
   //the main gameloop - this is where the game state is updated
   public void animate(){
      //the options that'are going to be used
      options[0]= new Options("                    wake up");
      options[1]= new Options("                       talk");
      options[0].open();
      options[1].close();
      options[2]=new Options("Who are you?","How can I get out of here?","Do you know Freder?");
      options[2].close();
      options[3]=new Options("Play game with the girl","Maybe later...");
      options[3].close();
      options[4]=new Options("Do you want to come with me?","Leave");
      options[4].close();
      Toolkit tk = Toolkit.getDefaultToolkit();
      //image of the cursors
      Image c1 = tk.getImage("image/cursor1.png");
      Image c2 = tk.getImage("image/cursor2.png");
      Image c3 = tk.getImage("image/cursor3.png");
      Image c4 = tk.getImage("image/cursor4.png");
      Cursor cursor1 = tk.createCustomCursor(c1 , new Point(0,0), "img");
      Cursor cursor2=tk.createCustomCursor(c2 , new Point(0,0), "img");
      Cursor cursor3=tk.createCustomCursor(c3 , new Point(0,0), "img");
      Cursor cursor4=tk.createCustomCursor(c4 , new Point(0,0), "img");
      this.setCursor(cursor1);//initialize the cursor
      display="sleep";//initialize the display
      this.setVisible(true);
      //start the game loop
      while(true){
         if(display.equals("waking up")){//waking up
            for(int i=4;i>1;i--) {
               darkness--;
               try{ Thread.sleep(500);} catch (Exception exc){}  //delay
               this.repaint();
            }
            music.playMusic("bgm1.wav",true);//start playing the bgm
            display="room1";//change the display to the first room
         }
         if(button[0].get()==true&&button[1].get()==true&&button[2].get()==true&&items.get("all")==true){//tell the player that all the buttons have been enabled
            items.setFalse("all");
            dialog.changeSen("You've enable all the buttons");
            dialog.changeSpeaker("***");
         }
         if(!display.equals("Map")){//in the rooms
            if(x<=0&&player.getDirection().equals("left")&&doorOpen==false){//bound of the left
               change=0;
            }else if(x>=1000&&player.getDirection().equals("right")) {//bound of the right
               change=0;
            }else if(x<=-40&&player.getDirection().equals("left")) {//going through the door
               display="map";//the player is on the main map now
               x=-20;
               change=0;
               player.setX(x);
               cur=1;//change the cursor
               player.setDirection("right");//set the direction of the player
            }
            x = x+change;//update coordinates
            player.setX(x);
            y = 350;
            //tell the user that it is the last button to press
            if(display.equals("room3")&&items.get("room3")==true&&button[0].get()==true&&button[1].get()==true&&items.get("last")==true) {
               items.setFalse("last");
               dialog.changeSen("That is the last button");
               dialog.changeSpeaker("***");
               items.setFalse("room3");
               dialog.open();
            }else if(display.equals("room2")&&button[2].get()==true&&button[0].get()==true&&items.get("last")==true) {
               items.setFalse("last");
               dialog.changeSen("That is the last button");
               dialog.changeSpeaker("***");
               dialog.open();
            }else if(display.equals("room1")&&button[2].get()==true&&button[1].get()==true&&items.get("last")==true){
               items.setFalse("last");
               dialog.changeSen("That is the last button");
               dialog.changeSpeaker("***");
               dialog.open();
            }
         }
         //when the player is on the map
         if(display.equals("map")){
            indexO=1;
            if(xMap>=250&&xMap<=330&&yMap>=640) {//when the player enters room1
               xMap=290;
               yMap=630;
               display="room1";
               changeX=0;
               changeY=0;
            }else if(xMap>=790&&xMap<=900&&yMap>=640){//when the player enters room2
               xMap=840;
               yMap=630;
               display="room2";
               changeX=0;
               changeY=0;
               if(button[0].get()==true&&button[1].get()==true&&button[2].get()==true){//when npc2 shouts at the player
                  indexO=4;
                  options[indexO].open();
               }
            }else if(xMap==100&&yMap<=290&&yMap>=220){//when the player enters room3
               xMap=110;
               yMap=250;
               display="room3";
               changeX=0;
               changeY=0;
            }else if(yMap>630&&player.getDMap().equals("s")){//move down
               changeY=0;
            }else if(xMap<=100&&player.getDMap().equals("w")){//move left
               changeX=0;
            }else if(yMap<=90&&player.getDMap().equals("n")){//move up
               changeY=0;
            }else if(xMap>=1020&&player.getDMap().equals("e")) {//move right
               changeX=0;
            }
            //when the player is around the npc1
            if(yMap==380&&xMap<=510&&xMap>=390&&player.getDMap().equals("s")) {
               changeY=0;
               if(dialog.isOn()==false) {
                  options[1].open();
               }
            }else if(yMap==380&&xMap<=510&&xMap>=390&&!(player.getDMap().equals("s"))){
               options[1].close();
            }else if(yMap>=380&&yMap<=500&&xMap==390&&player.getDMap().equals("e")) {
               changeX=0;
               if(dialog.isOn()==false) {
                  options[1].open();
               }     
            }else if(yMap>=380&&yMap<=500&&xMap==390&&!(player.getDMap().equals("e"))) {
               options[1].close();
            }else if(yMap==500&&xMap<=510&&xMap>=390&&player.getDMap().equals("n")) {
               changeY=0;
               if(dialog.isOn()==false) {
                  options[1].open();
               }     
            }else if(yMap==500&&xMap<=510&&xMap>=390&&!(player.getDMap().equals("n"))){
               options[1].close();
            }else if(yMap>=380&&yMap<=500&&xMap==510&&player.getDMap().equals("w")) {
               changeX=0;
               if(dialog.isOn()==false) {
                  options[1].open();
               }
            }else if(yMap>=380&&yMap<=500&&xMap==510&&!(player.getDMap().equals("w"))){
               options[1].close();
            }else if(yMap==90&&xMap<=970&&xMap>=850){//go trough the gate
               if(button[0].get()==true&&button[1].get()==true&&button[2].get()==true){//if all the buttons have been pressed
                  if(items.get("npc1Out")&&items.get("npc2Out")){//only the player escaped
                     ending=1;
                  }else if(!items.get("npc1Out")&&items.get("npc2Out")){//the player and npc1 escaped
                     ending=2;
                  }else if(items.get("npc1Out")&&!items.get("npc2Out")){//the player and np2 escaped
                     ending=3;
                  }else if(!items.get("npc1Out")&&!items.get("npc2Out")){//the player and both the players escaped
                     ending=4;
                  }
                  win=new ImageIcon("image/gameCleared"+ending+".png").getImage();//load the wing page according the the ending
                  display="win";
               }else {//if some buttons have not been pressed
                  dialog.changeSpeaker("***");
                  dialog.changeSen("The gate is locked");
               }
            }else {
               dialog.close();//close the dialog that tells the player the gate is locked
            }
            //update the coordinate of the map
            xMap+=changeX;
            yMap+=changeY;
            player.setxMap(xMap);
            player.setyMap(yMap);
         }
         //set the cursors
         if(cur==1) {
            this.setCursor(cursor1);
         }else if(cur==2){
            this.setCursor(cursor2);
         }else if(cur==3) {
            this.setCursor(cursor3);
         }else if(cur==4) {
            this.setCursor(cursor4);
         }
         //if the player is in one of the rooms
         if(display.equals("room1")||display.equals("room2")||display.equals("room3")){
            if(change<0){
               player.move("left");
            }else if(change>0) {
               player.move("right");
            }else {
               player.move("stand");
            }
         }
         try{ Thread.sleep(20);} catch (Exception exc){}//delay
         this.repaint();
      } 
   }
   
   
   //a method for loading the image
   private void loadImage(){
      try{
         room1 = new ImageIcon("image/room1.png" ).getImage();
         room2=new ImageIcon("image/room2.png").getImage();
         paint1=new ImageIcon("image/paint1Pic.png").getImage();
         paint2=new ImageIcon("image/paint2Pic.png").getImage();
         door=new ImageIcon("image/door.png").getImage();
         back=new ImageIcon("image/back.png").getImage();
         map=new ImageIcon("image/map.png").getImage();
         cup=new ImageIcon("image/cup.png").getImage();
         pen=new ImageIcon("image/pen.png").getImage();
         button1=new ImageIcon("image/button1.png").getImage();
         button2=new ImageIcon("image/button2.png").getImage();
         button3=new ImageIcon("image/button3.png").getImage();
         on=new ImageIcon("image/ON.png").getImage();
         off=new ImageIcon("image/OFF.png").getImage();
         news1=new ImageIcon("image/news1.png").getImage();
         news2=new ImageIcon("image/news2.png").getImage();
         talk=new ImageIcon("image/talk.png").getImage();
         npc2=new ImageIcon("image/npc2.png").getImage();
         game=new ImageIcon("image/game.png").getImage();
         card1=new ImageIcon("image/starLeft.png").getImage();
         card2=new ImageIcon("image/starRight.png").getImage();
         room3=new ImageIcon("image/room3.png").getImage();
         lock=new ImageIcon("image/lock.png").getImage();
      }catch(Exception e){
         System.out.println("error loading image");
      }
   }
   
   // Inner class for the the game area - this is where all the drawing of the screen occurs
   private class GameAreaPanel extends JPanel{
      public void paintComponent(Graphics g){
         super.paintComponent(g); //required
         setDoubleBuffered(true); 
         this.setBackground(new Color(0,0,0,0));
         if(display.equals("room1")){//draw the thing in room1
            g.drawImage(room1,0,0,null);
            player.draw(g);
            if(doorOpen==true) {
               g.drawImage(door,0,0,null);
            }
         }else if(display.equals("paint1")){//draw paint1 and the back button
            g.drawImage(paint1,0,0,null);
            g.drawImage(back,0,0,null);
         }else if(display.equals("paint2")){//draw paint2 and the back button
            g.drawImage(paint2,0,0,null);
            g.drawImage(back,0,0,null);
         }else if(display.equals("map")||display.equals("talk")){
            g.drawImage(map,0,0,null);
            player.drawOnMap(g);
            if(display.equals("talk")) {
               g.drawImage(talk,0,0,null);
            }
         }else if(display.equals("cup")){//draw the cup
            g.drawImage(cup,0,0,null);
            g.drawImage(back,0,0,null);
         }else if(display.equals("pen")){
            g.drawImage(cup,0,0,null);
            g.drawImage(pen,0,0,null);
         }else if(display.equals("news2")) {
            g.drawImage(news2,0,0,null);
            g.drawImage(back,0,0,null);
         }else if(display.equals("room2")){//draw the things inside room2
            g.drawImage(room2,0,0,null);
            if(items.get("npc2")==false) {
               g.drawImage(npc2,0,0,null);
            }
            player.draw(g);
            if(doorOpen==true) {
               g.drawImage(door,0,0,null);
            }
         }else if(display.equals("room3")){//draw thee things inside room3
            g.drawImage(room3,0,0,null);
            player.draw(g);
            if(doorOpen==true) {
               g.drawImage(door,0,0,null);
            }
         }
         else if(display.equals("button1")){
            g.drawImage(button1, 0, 0, null);
            g.drawImage(back, 0,0,null);
            if(button[0].get()==true) {
               g.drawImage(on,0,0,null);
            }else {
               g.drawImage(off,0,0,null);
            }
         }else if(display.equals("button2")){
            g.drawImage(button2, 0, 0, null);
            g.drawImage(back, 0,0,null);
            if(button[1].get()==true) {
               g.drawImage(on,0,0,null);
            }else {
               g.drawImage(off,0,0,null);
            }
         }else if(display.equals("button3")){
            g.drawImage(button3, 0, 0, null);
            g.drawImage(back, 0,0,null);
            if(button[2].get()==true) {
               g.drawImage(on,0,0,null);
            }else {
               g.drawImage(off,0,0,null);
            }
         }else if(display.equals("lock")) {
            g.drawImage(button3, 0, 0, null);
            if(button[1].get()==true) {
               g.drawImage(on,0,0,null);
            }else {
               g.drawImage(off,0,0,null);
            }
            g.drawImage(lock,0,0,null);
            g.drawImage(back, 0,0,null);
            theLock.draw(g);
         }else if(display.equals("news1")){
            g.drawImage(news1,0,0,null);
            g.drawImage(back,0,0,null);
         }else if(display.equals("game")) {//the card game
            g.drawImage(game,0,0,null);
            if(card==1) {
               g.drawImage(card1,0,0,null);
            }else if(card==2) {
               g.drawImage(card2,0,0,null);
            }
         }else if(display.equals("sleep")){
            g.setColor(new Color(0,0,0,255));
            g.fillRect(0,0,1200, 800);
         }else if(display.equals("waking up")){
            g.drawImage(room1,0,0,null);
            player.draw(g);
            g.setColor(new Color(0,0,0,(int)(255*darkness/5)));
            g.fillRect(0,0,1200, 800);
            g.setColor(new Color(0,0,0,255));
         }else if(display.equals("win")) {//winning page
            g.drawImage(win, imageX, imageY, null);
         }
         if(dialog.isOn()==true){//if dialog is on
            dialog.draw(g);//draw dialog
         }
         if(options[0].isOn()) {//if options are on
            options[0].draw(g);//draw dialog
         }else if(options[1].isOn()) {
            options[1].draw(g);
         }else if(options[2].isOn()) {
            options[2].draw(g);
         }else if(options[3].isOn()) {
            options[3].draw(g);
         }else if(options[4].isOn()) {
            options[4].draw(g);
         }
      }
   }//end of GameAreaPanel class
   
   // -----------  Inner class for the keyboard listener - this detects key presses and runs the corresponding code
   private class MyKeyListener implements KeyListener{
      public void keyTyped(KeyEvent e) {
      }
      public void keyPressed(KeyEvent e) {
         if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {//If ESC is pressed
            System.out.println("YIKES ESCAPE KEY!"); //close this & quit
         }
         //System.out.println("keyPressed="+KeyEvent.getKeyText(e.getKeyCode()));
         if(options[0].isOn()==false&&options[3].isOn()==false){
            if(display.equals("room1")||display.equals("room2")||display.equals("room3")){
               if (e.getKeyCode()==KeyEvent.VK_A) {
                  player.setDirection("left");
                  change=-10;
               }else if(e.getKeyCode()==KeyEvent.VK_D){
                  player.setDirection("right");
                  change=10;
                  
               }
            }else if(display.equals("map")){
               if (e.getKeyCode()==KeyEvent.VK_A) {
                  player.setDMap("w");
                  changeY=0;
                  changeX=-10;
               }else if(e.getKeyCode()==KeyEvent.VK_D){   
                  player.setDMap("e");
                  changeY=0;
                  changeX=10;
               }else if(e.getKeyCode()==KeyEvent.VK_W) {  
                  player.setDMap("n");
                  changeX=0;
                  changeY=-10;
               }else if(e.getKeyCode()==KeyEvent.VK_S) {
                  player.setDMap("s");
                  changeX=0;
                  changeY=10;
               }
            }
         }
      }
      public void keyReleased(KeyEvent e){
         if(!display.equals("map")){
            if(e.getKeyCode()==KeyEvent.VK_A||e.getKeyCode()==KeyEvent.VK_D) {
               change=0;
            }
         }else if(display.equals("map")){
            if (e.getKeyCode()==KeyEvent.VK_A) { 
               changeX=0;
            }else if(e.getKeyCode()==KeyEvent.VK_D){     
               changeX=0;
            }else if(e.getKeyCode()==KeyEvent.VK_W) {     
               changeY=0;
            }else if(e.getKeyCode()==KeyEvent.VK_S) {
               changeY=0;
            }
         }
      }
   } //end of keyboard listener
   
   private class MyMouseWheelListener implements MouseWheelListener {
      @Override
      public void mouseWheelMoved(MouseWheelEvent e) {
         
         if(display.equals("win")) {
            if (e.getWheelRotation() < 0) {
               if(imageY<0){
                  imageY+=50;
               }
            }else if(e.getWheelRotation()>0){
               if(-imageY+800<1600) {
                  imageY-=50;
               }
               
            }
            
         }
      }
      
   }
   
   
   // -----------  Inner class for the keyboard listener - this detects mouse movement & clicks and runs the corresponding methods 
   private class MyMouseListener implements MouseListener,MouseMotionListener{
      public void mouseClicked(MouseEvent e){
//         System.out.println("Mouse Clicked");
//         System.out.println("X:"+e.getX() + " y:"+e.getY()+"xMap"+xMap+"yMap"+yMap);
         if(options[indexO].isOn()==false&&dialog.isOn()==false){
            if(display.equals("room1")) {
               if(e.getX()>=510&&e.getX()<=740&&e.getY()>=140&&e.getY()<=260) {//paint
                  display="paint1";
                  if(items.get("paint")) {
                     dialog.changeSen("This is a photo of you taken 2 weeks before you came here");;
                     dialog.changeSpeaker("***");
                     items.setFalse("paint");
                  }
                  cur=1;
               }else if(e.getX()<=80&&e.getY()>=210&&e.getY()<=720) {//door
                  if(doorOpen==true){
                     doorOpen=false;
                  }else {
                     doorOpen=true;
                  }
               }else if(e.getX()>=450&&e.getX()<=495&&e.getY()>=365&&e.getY()<=430){//cup
                  display="cup";
                  cur=1;
               }
               else if(e.getX()>=514&&e.getX()<=560&&e.getY()>=430&&e.getY()<=468){//button
                  display="button1";
                  cur=4;
               }else if(e.getX()>=240&&e.getX()<=380&&e.getY()<=465&&e.getY()>=415){//news1
                  display="news1";
                  cur=1;
               }
            }else if(display.equals("room2")){
               if(e.getX()<=80&&e.getY()>=210&&e.getY()<=720){//door
                  if(doorOpen==true) {
                     doorOpen=false;
                  }else {
                     doorOpen=true;
                  }
               }else if(e.getX()>=457&&e.getX()<=500&&e.getY()<=465&&e.getY()>=428) {//button2
                  if(items.get("room2")==true){
                     unknownGirl=true;
                     dialog.changeSen("Don't touch that!  If you really want to press the button, beat me in the game.");
                     dialog.changeSpeaker("Unknown girl");
                     items.setFalse("npc2");
                     indexO=3;
                  }else {
                     display="button2";
                  }
               }else if(e.getX()<=325&&e.getX()>=272&&e.getY()<=450&&e.getY()>=412){//poker
                  dialog.changeSen("Some pokers");
                  dialog.changeSpeaker("***");
               }else if(e.getX()>=655&&e.getX()<=740&&e.getY()<=537&&e.getY()>=480) {//trash
                  display="news2";
               }
            }else if(display.equals("room3")){
               
               if(e.getX()<=80&&e.getY()>=210&&e.getY()<=720){//door
                  if(doorOpen==true) {
                     doorOpen=false;
                  }else {
                     doorOpen=true;
                  }
               }else if(e.getX()>=514&&e.getX()<=560&&e.getY()>=430&&e.getY()<=468){//button
                  if(items.get("lock")==true) {
                     display="lock";
                  }else {
                     display="button3";
                  }
               }
            }else if(display.equals("paint1")||display.equals("paint2")){
               if(e.getX()>=250&&e.getX()<=950&&e.getY()>=700&&e.getY()<=800) {//back
                  display="room1";
                  cur=1;
               }else if(e.getX()>=460&&e.getX()<=740&&e.getY()>=24&&e.getY()<=100) {
                  if(display.equals("paint1")) {
                     display="paint2";
                  }else {
                     display="paint1";
                  }
               }
            }else if(display.equals("cup")){
               if(e.getX()>=250&&e.getX()<=950&&e.getY()>=700&&e.getY()<=800) {//back
                  display="room1";
                  cur=1;
               }else if(e.getX()>=360&&e.getX()<=700&&e.getY()>=45&&e.getY()<=290) {//pen
                  display="pen";
                  dialog.changeSen("This pen was left by the previous roomer");;
                  dialog.changeSpeaker("***");
                  cur=1;
               }
            }else if(display.equals("pen")){
               if(e.getX()>=50&&e.getX()<=1132&&e.getY()>=580&&e.getY()<=770) {
                  dialog.close();
               }else {
                  display="cup";
                  dialog.close();
                  cur=1;
               }
            }else if(display.equals("button1")) {
               if(e.getX()>=250&&e.getX()<=950&&e.getY()>=700&&e.getY()<=800){//back
                  display="room1";
                  cur=1;
               }else if(e.getX()>=310&&e.getX()<=830&&e.getY()>=230&&e.getY()<=600) {//press
                  button[0].set();
               }
            }else if(display.equals("news1")) {
               if(e.getX()>=250&&e.getX()<=950&&e.getY()>=700&&e.getY()<=800) {//back
                  display="room1";
                  cur=1;
               }
            }else if(display.equals("news2")) {
               if(e.getX()>=250&&e.getX()<=950&&e.getY()>=700&&e.getY()<=800) {//back
                  display="room2";
                  cur=1;
               }
            }else if(display.equals("button2")){
               if(e.getX()>=250&&e.getX()<=950&&e.getY()>=700&&e.getY()<=800){//back
                  display="room2";
                  cur=1;
               }else if(e.getX()>=310&&e.getX()<=830&&e.getY()>=230&&e.getY()<=600) {//press
                  button[1].set();
               }
            }else if(display.equals("button3")){
               if(e.getX()>=250&&e.getX()<=950&&e.getY()>=700&&e.getY()<=800){//back
                  display="room3";
                  cur=1;
               }else if(e.getX()>=310&&e.getX()<=830&&e.getY()>=230&&e.getY()<=600) {//press
                  button[2].set();
               }
            }else if(display.equals("game")){
               if(e.getX()>=325&&e.getX()<=475&&e.getY()>=635&&e.getY()<=735) {//back   
                  if(Math.random()<0.5) {
                     point+=2;
                     if(point>=10) {
                        dialog.changeSen("Fine, you have 10 points now, I will let you press the button.");
                        items.setFalse("room2");
                        card=1;
                        
                     }else {
                        dialog.changeSen("Correct, now you have "+point+" points.");
                        card=1;
                     }
                  }else {
                     point--;
                     dialog.changeSen("Wrong, now you only have "+point+" points.");
                     card=2;
                  }
                  dialog.open();
               }else if(e.getX()>=765&&e.getX()<=915&&e.getY()>=635&&e.getY()<=735) {//cards
                  if(Math.random()<0.5) {
                     point+=2;
                     if(point>=10) {
                        dialog.changeSen("Fine, you have 10 points now, I will let you press the button.");
                        items.setFalse("room2");
                        card=2;
                     }else{
                        dialog.changeSen("Correct, now you have "+point+" points.");
                        card=2;
                     }
                  }else {
                     point--;
                     dialog.changeSen("Wrong, now you only have "+point+" points.");
                     card=1;
                  }
                  dialog.open();
               }
               
            }else if(display.equals("lock")){
               if(e.getX()>=250&&e.getX()<=950&&e.getY()>=700&&e.getY()<=800) {//back
                  display="room3";
                  
                  cur=1;
               }else if(e.getX()>=469&&e.getX()<=685&&e.getY()<=579&&e.getY()>=475) {//confirm
                  if(theLock.get().equals("3363")) {
                     items.setFalse("lock");
                     dialog.changeSen("The lock has been opened");
                     dialog.changeSpeaker("***");
                     dialog.open();
                     display="button3";
                  }else {
                     dialog.changeSen("Sorry, wrong password");
                     dialog.changeSpeaker("***");
                     dialog.open();
                  }
               }else if(e.getX()>=273&&e.getX()<=382){//num1
                  if(e.getY()<=193&&e.getY()>=155) {
                     theLock.up(1);
                  }else if(e.getY()<=412&&e.getY()>=365) {
                     theLock.down(1);
                  }
               }else if(e.getX()>=448&&e.getX()<=557) {//num2
                  if(e.getY()<=193&&e.getY()>=155) {
                     theLock.up(2);
                  }else if(e.getY()<=412&&e.getY()>=365) {
                     theLock.down(2);
                  }
               }else if(e.getX()>=623&&e.getX()<=732) {//num3
                  if(e.getY()<=193&&e.getY()>=155) {
                     theLock.up(3);
                  }else if(e.getY()<=412&&e.getY()>=365) {
                     theLock.down(3);
                  }
               }else if(e.getX()>=783&&e.getX()<=892){//num4
                  if(e.getY()<=193&&e.getY()>=155) {
                     theLock.up(4);
                  }else if(e.getY()<=412&&e.getY()>=365) {
                     theLock.down(4);
                  }
               }
            }
         }else if(dialog.isOn()==true){//click on the dialog
            if(dialog.getSen().equals("Let's start, you need to get 10 points to win the game.")) {
               dialog.changeSen("Guess which card have a star label on it");
            }else if(dialog.getSen().equals("Guess which card have a star label on it")){
               dialog.changeSen("You will gain 2 points if you are right, and lose 1 point if you are wrong");
            }else {
               if(e.getX()>=50&&e.getX()<=1132&&e.getY()>=580&&e.getY()<=770) {
                  if(display.equals("talk")) {
                     display="map";
                  }
                  if(dialog.getSen().equals("Hi, May I help you?")){
                     options[indexO].close();
                     display="talk";
                     if(items.get("all")==true) {
                        indexO=2;
                     }else {
                        indexO=4;
                     }
                     options[indexO].open();
                     
                  }else if(dialog.getSen().equals("Don't touch that!  If you really want to press the button, beat me in the game.")) {
                     options[3].open();
                     
                  }else if(display.equals("game")) {
                     card=0;
                     if(point>=10){
                        display="room2";
                     }
                  }
                  dialog.close();
               }
            }
         }else if(options[indexO].isOn()){//instruction
            if(indexO==0){
               if(e.getX()>=372&&e.getX()<=850&&e.getY()>=140&&e.getY()<=240) {//waking up
                  display="waking up";
                  music.playMusic("bgm0.wav", false);
                  dialog.changeSpeaker("***");
                  dialog.changeSen("Move using <A> and <D>(click on a dialog box to dipose the box)");
                  dialog.close();
               }
            }else if(indexO==1){
               if(e.getX()>=372&&e.getX()<=850&&e.getY()>=140&&e.getY()<=240) {
                  dialog.changeSpeaker("Stranger");
                  dialog.changeSen("Hi, May I help you?");
                  dialog.open();
               }
            }
            if(options[indexO].getNum()==1){
               if(e.getX()>=372&&e.getX()<=850&&e.getY()>=140&&e.getY()<=240){
                  options[indexO].setAns(1);
                  options[indexO].close();
                  dialog.open();
               }
            }
            if(options[indexO].getNum()==3){
               if(e.getX()>=372&&e.getX()<=850&&e.getY()>=140&&e.getY()<=240) {
                  if(display.equals("talk")) {
                     dialog.changeSen("I'm also a patient in this hospital");
                  }
                  options[indexO].setAns(1);
                  options[indexO].close();
                  dialog.open();
               }else if(e.getX()>=372&&e.getX()<=850&&e.getY()>=290&&e.getY()<=390) {
                  if(display.equals("talk")) {
                     dialog.changeSen("..When you are cured. Or,,,if you can unlock the gate and escape.?");
                  }
                  options[indexO].setAns(2);
                  options[indexO].close();
                  dialog.open();
               }else if(e.getX()>=372&&e.getX()<=850&&e.getY()>=440&&e.getY()<=540) {
                  if(display.equals("talk")) {
                     dialog.changeSen("Yes, he is kind of weird, Freder likes to record things on walls.");
                  }
                  options[indexO].setAns(3);
                  options[indexO].close();
                  dialog.open();
               }
            }else if(options[indexO].getNum()==2) {
               if(e.getX()>=372&&e.getX()<=850&&e.getY()>=140&&e.getY()<=240) {
                  if(indexO==3){
                     dialog.changeSen("Let's start, you need to get 10 points to win the game.");
                     display="game";
                  }else if(indexO==4) {
                     if(display.equals("talk")){
                        dialog.changeSpeaker("Stranger");
                        dialog.changeSen("Thank you for the invitation, I will come just after you later.");
                        items.setFalse("npc1Out");
                        dialog.open();
                     }else if(display.equals("room2")){
                        dialog.changeSpeaker("Unknown girl");
                        dialog.changeSen("??!!..Well, fine, I will go with you");
                        items.setFalse("npc2Out");
                        dialog.open();
                     }
                  }
                  options[indexO].close();
                  dialog.open();
               }else if(e.getX()>=372&&e.getX()<=850&&e.getY()>=290&&e.getY()<=390) {
                  if(indexO==3) {
                     options[indexO].close();
                     
                  }else if(indexO==4) {
                     if(display.equals("talk")){
                        display="map";
                     }
                     options[indexO].close();
                  }else {
                     options[indexO].setAns(2);
                     options[indexO].close();
                     dialog.open();
                  }
               }
            }else if(options[indexO].getNum()==1) {
               if(e.getX()>=372&&e.getX()<=850&&e.getY()>=140&&e.getY()<=240) {
                  options[indexO].setAns(1);
                  options[indexO].close();
                  dialog.open();
               }
            }
         }
      }
      public void mousePressed(MouseEvent e) {
         if(cur==1){
            cur=2;
         }
      }
      public void mouseReleased(MouseEvent e) {
         if(cur==2){
            cur=1;
         }
      }
      public void mouseEntered(MouseEvent e) {
         cur=1;
      }
      public void mouseExited(MouseEvent e) {
      }
      @Override
      public void mouseDragged(MouseEvent e){
      }
      @Override
      public void mouseMoved(MouseEvent e){
         if(options[indexO].isOn()==false&&dialog.isOn()==false) {//when there is no dialog or options displaying
            //changing the cursors when it's on something that can be pressed
            if(display.equals("room1")){
               if(e.getX()>=510&&e.getX()<=740&&e.getY()>=140&&e.getY()<=260) {//paint
                  cur=3;
               }else if(e.getX()>=240&&e.getX()<=380&&e.getY()<=465&&e.getY()>=415){//news1
                  cur=3;
               }else if(e.getX()>=450&&e.getX()<=495&&e.getY()>=365&&e.getY()<=430){//cup
                  cur=3;
               }else if(e.getX()>=514&&e.getX()<=560&&e.getY()>=430&&e.getY()<=468){//button
                  cur=3;
               }else if(e.getX()<=80&&e.getY()>=210&&e.getY()<=720) {//door
                  cur=4;
               }else{
                  cur=1;
               }
            }else if(display.equals("paint1")||display.equals("paint2")) {
               if(e.getX()>=250&&e.getX()<=950&&e.getY()>=700&&e.getY()<=800) {//back     
                  cur=4;
               }else if(e.getX()>=460&&e.getX()<=740&&e.getY()>=24&&e.getY()<=100) {//changeing paint
                  cur=4;
               }else {
                  cur=1;
               }
            }else if(display.equals("room2")){
               if(e.getX()<=80&&e.getY()>=210&&e.getY()<=720) {//door
                  cur=4;
               }else if(e.getX()<=325&&e.getX()>=272&&e.getY()<=450&&e.getY()>=412){//poker
                  cur=3;
               }else if(e.getX()>=457&&e.getX()<=500&&e.getY()<=465&&e.getY()>=428){//button2
                  cur=3;
               }else if(e.getX()>=655&&e.getX()<=740&&e.getY()<=537&&e.getY()>=480) {//trash
                  cur=3;
               }else {
                  cur=1;
               }
            }else if(display.equals("room3")) {
               if(e.getX()<=80&&e.getY()>=210&&e.getY()<=720) {//door
                  cur=4;
               }else if(e.getX()>=514&&e.getX()<=560&&e.getY()>=430&&e.getY()<=468){//button
                  cur=3;
               }else {
                  cur=1;
               }
            }else if(display.equals("cup")) {
               if(e.getX()>=360&&e.getX()<=700&&e.getY()>=45&&e.getY()<=290) {//pen
                  cur=3;
               }else if(e.getX()>=250&&e.getX()<=950&&e.getY()>=700&&e.getY()<=800) {//back     
                  cur=4;
               }else {
                  cur=1;
               }
            }else if(display.equals("button1")) {
               if(e.getX()>=250&&e.getX()<=950&&e.getY()>=700&&e.getY()<=800) {//back     
                  cur=4;
               }else if(e.getX()>=310&&e.getX()<=830&&e.getY()>=230&&e.getY()<=600) {//press
                  cur=4;
               }else {
                  cur=1;
               }
            }else if(display.equals("news1")) {
               if(e.getX()>=250&&e.getX()<=950&&e.getY()>=700&&e.getY()<=800) {//back     
                  cur=4;
               }else {
                  cur=1;
               }
            }else if(display.equals("news2")) {
               if(e.getX()>=250&&e.getX()<=950&&e.getY()>=700&&e.getY()<=800) {//back     
                  cur=4;
               }else {
                  cur=1;
               }
            }else if(display.equals("pen")) {
               cur=1;
            }else if(display.equals("button2")) {
               if(e.getX()>=250&&e.getX()<=950&&e.getY()>=700&&e.getY()<=800) {//back     
                  cur=4;
               }else if(e.getX()>=310&&e.getX()<=830&&e.getY()>=230&&e.getY()<=600) {//press
                  cur=4;
               }else {
                  cur=1;
               }
            }else if(display.equals("button3")) {
               if(e.getX()>=250&&e.getX()<=950&&e.getY()>=700&&e.getY()<=800) {//back     
                  cur=4;
               }else if(e.getX()>=310&&e.getX()<=830&&e.getY()>=230&&e.getY()<=600) {//press
                  cur=4;
               }else {
                  cur=1;
               }
            }else if(display.equals("game")) {
               if(e.getX()>=325&&e.getX()<=475&&e.getY()>=635&&e.getY()<=735) {//back     
                  cur=4;
               }else if(e.getX()>=765&&e.getX()<=915&&e.getY()>=635&&e.getY()<=735) {//cards
                  cur=4;
               }else {
                  cur=1;
               }
            }else if(display.equals("lock")) {
               if(e.getX()>=250&&e.getX()<=950&&e.getY()>=700&&e.getY()<=800) {//back     
                  cur=4;
               }else if(e.getX()>=469&&e.getX()<=685&&e.getY()<=579&&e.getY()>=475) {//confirm
                  cur=4;
               }else if(e.getX()>=273&&e.getX()<=382) {//num1
                  if(e.getY()<=193&&e.getY()>=155) {
                     cur=4;
                  }else if(e.getY()<=412&&e.getY()>=365) {
                     cur=4;
                  }else {
                     cur=1;
                  }
               }else if(e.getX()>=448&&e.getX()<=557) {//num2
                  if(e.getY()<=193&&e.getY()>=155) {
                     cur=4;
                  }else if(e.getY()<=412&&e.getY()>=365) {
                     cur=4;
                  }else {
                     cur=1;
                  }
               }else if(e.getX()>=623&&e.getX()<=732) {//num3
                  if(e.getY()<=193&&e.getY()>=155) {
                     cur=4;
                  }else if(e.getY()<=412&&e.getY()>=365) {
                     cur=4;
                  }else {
                     cur=1;
                  }
               }else if(e.getX()>=783&&e.getX()<=892) {//num4
                  if(e.getY()<=193&&e.getY()>=155) {
                     cur=4;
                  }else if(e.getY()<=412&&e.getY()>=365) {
                     cur=4;
                  }else {
                     cur=1;
                  }
               }else {
                  cur=1;
               }
            }else{
               cur=1;
            }
         }
         if(dialog.isOn()==true) {//draw the dialog
            if(e.getX()>=50&&e.getX()<=1132&&e.getY()>=580&&e.getY()<=770) {
               cur=4;
            }else {
               cur=1;
            }
         }
         if(options[indexO].isOn()){//draw the options
            if(options[indexO].getNum()==3) {
               if(e.getX()>=372&&e.getX()<=850&&e.getY()>=140&&e.getY()<=240) {
                  cur=4;
               }else if(e.getX()>=372&&e.getX()<=850&&e.getY()>=290&&e.getY()<=390) {
                  cur=4;
               }else if(e.getX()>=372&&e.getX()<=850&&e.getY()>=440&&e.getY()<=540) {
                  cur=4;
               }else {
                  cur=1;
               }
            }else if(options[indexO].getNum()==2) {
               if(e.getX()>=372&&e.getX()<=850&&e.getY()>=140&&e.getY()<=240) {
                  cur=4;
               }else if(e.getX()>=372&&e.getX()<=850&&e.getY()>=290&&e.getY()<=390) {
                  cur=4;
               }else {
                  cur=1;
               }
            }else if(options[indexO].getNum()==1) {
               if(e.getX()>=372&&e.getX()<=850&&e.getY()>=140&&e.getY()<=240){
                  cur=4;
               }else {
                  cur=1;
               }
            }
         }
         
      }
   } //end of mouselistener
}//end of GameFrame.java