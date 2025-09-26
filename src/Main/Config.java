package Main;

import java.io.*;

public class Config {

    GamePanel gp;

    public Config(GamePanel gp){
        this.gp = gp;
    }

    public void saveConfig() throws IOException {

            //This would work as well probably
//        File file = new File("config.txt");
//        FileWriter fw = new FileWriter(file);

    try {
        BufferedWriter bw = new BufferedWriter(new FileWriter("config.txt"));
        //Fullscreen
        if(gp.fullScreenOn){bw.write("On");}
        if(gp.fullScreenOn == false){bw.write("Off");}
        bw.newLine();

        //Music vol
        bw.write(String.valueOf(gp.music.volumeScale));
        bw.newLine();

        //SE vol
        bw.write(String.valueOf(gp.soundEffect.volumeScale));
        bw.newLine();

        //Can add a bunch of stuff here such as inventory player position & mob/item locations

        bw.close();
    }catch(IOException e){e.printStackTrace();}
    }

    public void loadConfig(){

        try {
            BufferedReader br = new BufferedReader(new FileReader("config.txt"));
            String s = br.readLine();

            //Full screen
            if(s.equals("On")){gp.fullScreenOn = true;}
            if(s.equals("Off")){gp.fullScreenOn = false;}


            //Music vol
            s = br.readLine();
            gp.music.volumeScale = Integer.parseInt(s);

            //SE vol
            s = br.readLine();
            gp.soundEffect.volumeScale = Integer.parseInt(s);

            br.close();

        }catch(IOException e){e.printStackTrace();}
    }
}
