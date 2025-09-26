package InteractiveTile;

import Main.GamePanel;

import java.awt.*;

public class DryTree extends InteractiveTile{

    public DryTree(GamePanel gp, int worldX, int worldY){
        super(gp);

        this.worldX = worldX * gp.tileSize;
        this.worldY = worldY * gp.tileSize;
        type = type_interactive_tile;
        correctItemType = type_axe;
        life = 2;
        name = "Dry Tree";
        down1 = setup("/InteractiveTile/drytree", gp.tileSize, gp.tileSize);
        destructable = true;
        invincible = false;


    }

    public void playSE(){
        gamePanel.playSE(11);
    }

    public InteractiveTile getDestroyedForm(){
        InteractiveTile tile = new Trunk(gamePanel, worldX/ gamePanel.tileSize, worldY / gamePanel.tileSize);
        return tile;}

    public Color getParticleColor(){
        Color color = new Color(65, 50, 30);
        return color;
    }
    public int getParticleSize(){
        int size = 6;//6 pixels
        return size;
    }
    public int getParticleSpeed(){
        int speed = 1;
        return speed;
    }
    public int getParticleMaxLife(){
        int maxLife = 20;
        return maxLife;
    }

}
