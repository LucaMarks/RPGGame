package InteractiveTile;

import Main.GamePanel;

public class Tent extends InteractiveTile{

    public Tent(GamePanel gp, int worldX, int worldY){
        super(gp);

        this.worldX = worldX * gp.tileSize;
        this.worldY = worldY * gp.tileSize;

        type = type_interactive_tile;
        life = 2;
        name = "Tent";
        down1 = setup("/InteractiveTile/tent", gp.tileSize, gp.tileSize);
        destructable = false;
        invincible = true;
    }

}
