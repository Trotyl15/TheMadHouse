import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
/*Player.java
 * @version 1.0
 * @author Trotyl
 * @since 2019.Dec.16
 * A class that simulated the character*/
class Player {
   public int xPos;//position of the player
   public int yPos; 
   public int xMap;
   public int yMap;
   String direction;
   String dMap;
   BufferedImage[] sprites;
   int currentSprite;
   int currentStep;
   Image playerOnMap=new ImageIcon("image/g1Head.png").getImage();//the player's look that is shown on the map
   public Player(int x,int y) {
      loadSprites();//load the sprited
      currentSprite=2;
      currentStep=0;
      this.xPos=x;
      this.yPos=y;
      this.direction="left";
   }
   public void setX(double x) {
      xPos=(int)x;
   }
   public String getDirection() {
      return direction;
   }
   public void setxMap(double x) {
      xMap=(int)x;
   }
   public void setyMap(double y) {
      yMap=(int)y;
   }
   public void loadSprites() {
      try {
         BufferedImage sheet = ImageIO.read(new File("image/girl1.png"));//the sprite image
         final int width = 250;
         final int height = 500;
         final int rows=1;
         final int cols = 6;
         sprites = new BufferedImage[rows * cols];
         for (int j = 0; j < rows; j++)
            for (int i = 0; i < cols; i++)
            sprites[(j * cols) + i] = sheet.getSubimage(i * width,j * height,width,height);
      }catch(Exception e) {
         System.out.println("error loading sprite");
      };
   }
   public void draw(Graphics g) {
      g.drawImage(sprites[currentSprite],xPos,350,null);//draw the character in the rooms
      
   }
   public void drawOnMap(Graphics g) {
      g.drawImage(playerOnMap,xMap,yMap,null);//draw the character on the map
   }
   public void update() {//update the sprite
      if(direction.equals("left")&&currentStep==3) {
         currentSprite--;
         currentStep=0;
         if (currentSprite<0) {
            currentSprite=2;
         }
      }else if(direction.equals("right")&&currentStep==3){
         currentSprite++;
         currentStep=0;
         if (currentSprite>5) {
            currentSprite=3;
         }
      }else {
         currentStep++;
      }
   }
   public void setDirection(String d) {//update direction in rooms
      direction=d;
   }
   public void setDMap(String d) {//update direction on the map
      dMap=d;
   }
   public String getDMap() {//get the direction on the map
      return dMap;
   }
   public void move(String movement){//movement of the player
      if (movement.equals("stand")) { //not moving
         currentStep=0;
         if(direction.equals("left")){
            currentSprite=2;
         }else {
            currentSprite=3;
         }
      }else if(movement.equals("left")) {
         update();
      }else if(movement.equals("right")) {
         update();
      }
   }
}//end of Player.java