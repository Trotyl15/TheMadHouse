import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
/*Dialog.java
 * @version 1.0
 * @author Trotyl
 * @since 2019.Dec.16
 * A class that manages the dialogs*/
public class Dialog {
   String sentence;
   String speaker;
   Font f = new Font("Couier", Font.PLAIN, 25);
   Image dialog=new ImageIcon("image/dialog.png").getImage();
   boolean haveD;
   Dialog(){
      haveD=false;
      sentence="";
      speaker="";
   }
   public void changeSen(String str) {//setting the sentence
      sentence=str;
      haveD=true;
   }
   public void open() {//open the dialog
      haveD=true;
   }
   public String getSen() {//get the sentence
      return sentence;
   }
   public void close() {//close the dialog
      haveD=false;
   }
   public void changeSpeaker(String name) {//setting the speaker
      speaker=name;
   }
   public boolean isOn() {
      return haveD;
   }
   public void draw(Graphics g) {
      g.drawImage(dialog,0,0,null);
      g.setFont(f);
      g.drawString(speaker+":", 80,605); 
      g.drawString(sentence, 120, 660);
   }
}
