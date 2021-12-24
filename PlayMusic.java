//import
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JOptionPane;
import sun.audio.AudioData;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;
import sun.audio.ContinuousAudioDataStream;
/*PlayMusic.java
 * @version 1.0
 * @author Trotyl
 * @since 2019.Dec.16
 * A class that plays the musics*/
public class PlayMusic {
   public void playMusic(String filepath,boolean isLoop) {
      try {
         File musicPath=new File("music/"+filepath);//load the music
         AudioInputStream audioInput=AudioSystem.getAudioInputStream(musicPath);
         Clip clip=AudioSystem.getClip();
         clip.open(audioInput);
         clip.start();
         if(isLoop) {
            clip.loop(Clip.LOOP_CONTINUOUSLY);//loop the music
         }
      }catch(Exception e) {
         System.out.println("error loading music");
      }
   }
}//end of PlayMusic.java