package Main;

import java.net.URL;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class Sound {

    Clip clip;
    URL[] soundURL = new URL[30];
    FloatControl fc;
    int volumeScale = 3;
    float volume;

    public Sound(){
        soundURL[0] = initSound("/sound/BlueBoyAdventure");//Turn on in keyHandler
        soundURL[1] = initSound("/sound/coin");
        soundURL[2] = initSound("/sound/powerup");
        soundURL[3] = initSound("/sound/unlock");
        soundURL[4] = initSound("/sound/fanfare");
        soundURL[5] = initSound("/sound/hitmonster");
        soundURL[6] = initSound("/sound/receivedamage");
        soundURL[7] = initSound("/sound/swingsword");//.flac extension
        soundURL[8] = initSound("/sound/levelup");
        soundURL[9] = initSound("/sound/cursor");
        soundURL[10] = initSound("/sound/burning");
        soundURL[11] = initSound("/sound/cuttree");
        soundURL[12] = initSound("/sound/stairs");
        soundURL[13] = initSound("/sound/blocked");
        soundURL[14] = initSound("/sound/parry");
        soundURL[15] = initSound("/sound/Merchant");
        soundURL[16] = initSound("/sound/Dungeon");
        soundURL[17] = initSound("/sound/chipwall");
        soundURL[18] = initSound("/sound/dooropen");

    }

    public URL initSound(String path){return getClass().getResource(path + ".wav");}

    public void setFile(int i){

        try{
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
            clip = AudioSystem.getClip();
            clip.open(ais);
            fc = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            checkVolume();

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void play(){clip.start();}

    public void loop(){clip.loop(Clip.LOOP_CONTINUOUSLY);}

    public void stop(){clip.stop();}

    public void checkVolume(){

        switch(volumeScale){
            case 0: volume = -80f;break;
            case 1: volume = -20f;break;
            case 2: volume = -12f;break;
            case 3: volume = -5f;break;
            case 4: volume = 1f;break;
            case 5: volume = 6f;break;
        }

        fc.setValue(volume);
    }
}
