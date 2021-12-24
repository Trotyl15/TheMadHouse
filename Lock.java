import java.awt.Font;
import java.awt.Graphics;
/*Lock.java
 * @version 1.0
 * @author Trotyl
 * @since 2019.Dec.16
 * A class that simulates a lock*/
public class Lock {
   Font f = new Font("Couier", Font.PLAIN,100);
   int num[]=new int[4];
   Lock(){
      for(int i=0;i<4;i++) {
         
      }
   }
   public void up(int n) {//when the player clicks on the upper arrow
      if(num[n-1]==9) {
         num[n-1]=0;
      }else {
         num[n-1]++;
      }
   }
   public void down(int n) {//when the player clicks on the down arrow
      if(num[n-1]==0) {
         num[n-1]=9;
      }else {
         num[n-1]--;
      }
   }
   public String get() {
      return num[0]+""+num[1]+""+num[2]+""+num[3];//returns the code of the lock
   }
   public void draw(Graphics g) {//draw the number on the lock
      g.setFont(f);
      g.drawString(num[0]+"", 300,300);
      g.drawString(num[1]+"", 475,300);
      g.drawString(num[2]+"", 650,300);
      g.drawString(num[3]+"", 810,300);
   }
}
