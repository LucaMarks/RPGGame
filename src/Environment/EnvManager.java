package Environment;

import Main.GamePanel;

import java.awt.*;

public class EnvManager {

    GamePanel gp;
    public Lighting lighting;

    public EnvManager(GamePanel gp){
        this.gp = gp;
    }

    public void setup(){
        lighting = new Lighting(gp);
    }

    public void update(){
        lighting.update();
    }

    public void draw(Graphics2D g2){

        lighting.draw(g2);
    }
}
