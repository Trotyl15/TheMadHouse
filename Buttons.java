/*Buttons.java
 * @version 1.0
 * @author Trotyl
 * @since 2019.Dec.16
 * A class that simulates a button*/
public class Buttons {
   boolean isOn;
   Buttons() {
      isOn=false;
   }
   void set() {//set the buttons' statics
      if(isOn==false) {
         isOn=true;
      }else {
         isOn=false;
      }
   }
   boolean get() {
      return isOn;
   }
}
