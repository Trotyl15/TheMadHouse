/*Items.java
 * @version 1.0
 * @author Trotyl
 * @since 2019.Dec.16
 * A class that manages all the items used in the game*/
public class Items{
   boolean paint=true;
   boolean pen=true;;
   boolean news=true;
   boolean room2=true;
   boolean npc2=true;
   boolean room3=true;
   boolean lock=true;
   boolean last=true;
   boolean all=true;
   boolean npc1Out=true;
   boolean npc2Out=true;
   public void setFalse(String item) {
      if(item.equals("paint")){
         paint=false;
      }else if(item.equals("pen")) {
         pen=false;
      }else if(item.equals("news")) {
         news=false;
      }else if(item.equals("room2")) {
         room2=false;
      }else if(item.equals("npc2")) {
         npc2=false;
      }else if(item.equals("room3")) {
         room3=false;
      }else if(item.equals("lock")) {
         lock=false;
      }else if(item.equals("last")) {
         last=false;
      }else if(item.equals("all")) {
         all=false;
      }else if(item.equals("npc1Out")) {
         npc1Out=false;
      }else if(item.equals("npc2Out")) {
         npc2Out=false;
      }
   }
   
   public boolean get(String item) {
      if(item.equals("paint")){
         return paint;
      }else if(item.equals("pen")) {
         return pen;
      }else if(item.equals("news")) {
         return news;
      }else if(item.equals("room2")) {
         return room2;
      }else if(item.equals("npc2")){
         return npc2;
      }else if(item.equals("room3")) {
         return room3;
      }else if(item.equals("lock")) {
         return lock;
      }else if(item.equals("last")) {
         return last;
      }else if(item.equals("all")) {
         return all;
      }else if(item.equals("npc1Out")) {
         return npc1Out;
      }else if(item.equals("npc2Out")) {
         return npc2Out;
      }
      return false;
   }
}
