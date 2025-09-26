package InteractiveTile;

import Main.GamePanel;

public class Trunk extends InteractiveTile{

    public Trunk(GamePanel gp, int worldX, int worldY){
        super(gp);

        this.worldX = worldX * gp.tileSize;
        this.worldY = worldY * gp.tileSize;

        down1 = setup("/InteractiveTile/trunk", gp.tileSize, gp.tileSize);
        //destructable

        //Need to config solidArea
        solidArea.x = 0;
        solidArea.y = 0;
        solidArea.width = 0;
        solidArea.height = 0;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
    }
}
