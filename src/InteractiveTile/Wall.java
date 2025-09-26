package InteractiveTile;

import Main.GamePanel;

import java.awt.*;
import java.util.Random;

public class Wall extends InteractiveTile{

    GamePanel gp;

    public Wall(GamePanel gp, int worldX, int worldY){
        super(gp);
        this.gp = gp;

        this.worldX = worldX * gp.tileSize;
        this.worldY = worldY * gp.tileSize;

        down1 = setup("/InteractiveTile/destructiblewall", gp.tileSize, gp.tileSize);

        type = type_interactive_tile;
        correctItemType = type_pickaxe;
        life = 3;
        destructable = true;
        invincible = false;
        name = "Wall";

    }

    public void playSE(){}

    public InteractiveTile getDestroyedForm(){return null;}

    public void checkDrop(){

        int i = new Random().nextInt(0, 100);

        //set the drop
        if(i < 50){}
        if(i >= 50){}
        if(i >= 75){}
    }

    public Color getParticleColor(){return new Color(65, 35, 30);}

    public int getParticleSize(){return 6;}

    public int getParticleSpeed(){return 1;}

    public int getParticleMaxLife(){return 20;}
}
