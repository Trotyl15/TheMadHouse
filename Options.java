import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
/*Options.java
 * @version 1.0
 * @author Trotyl
 * @since 2019.Dec.16
 * A class that manages the options*/
public class Options {
   String op1;
   String op2;
   String op3;
   int opNum;
   boolean haveO;
   Font f = new Font("Couier", Font.PLAIN, 25);
   Image box=new ImageIcon("image/option.png").getImage();
   int answer;
   Options(String op1){
      this.op1=op1;
      opNum=1;
      answer=0;
      haveO=true;
   }
   Options(String op1,String op2){
      this.op1=op1;
      this.op2=op2;
      opNum=2;
      haveO=true;
   }
   Options(String op1,String op2,String op3){
      this.op1=op1;
      this.op2=op2;
      this.op3=op3;
      opNum=3;
      haveO=false;
   }
   public void open() {
      haveO=true;
   }
   public void close() {
      haveO=false;
   }
   public boolean isOn() {
      return haveO;
   }
   public int getNum() {
      return opNum;
   }
   public void setAns(int a) {
      answer=a;
   }
   public int getAns() {
      return answer;
   }
   public void draw(Graphics g) {
      if(opNum>=1) {
         g.drawImage(box,0,-50,null);
         g.setFont(f);
         g.drawString(op1, 400,180); 
      }
      if(opNum>=2) {
         g.drawImage(box,0,100,null);
         g.setFont(f);
         g.drawString(op2, 400,330); 
      }
      if(opNum>=3) {
         g.drawImage(box,0,250,null);
         g.setFont(f);
         g.drawString(op3, 400,480); 
      }
   }
}
