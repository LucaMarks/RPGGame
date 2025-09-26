package Object;

import Entity.Entity;
import Main.GamePanel;

public class OBJ_Coin_Bronze extends Entity {

    public static final String objName = "Bronze Coin";

    public OBJ_Coin_Bronze(GamePanel gp){
        super(gp);
        name = objName;
        type = type_pickupOnly;
        value = 1;
        down1 = setup("/Objects/coin_bronze", gp.tileSize, gp.tileSize);

    }

    public boolean use(Entity e){
        gamePanel.playSE(1);
        gamePanel.ui.addMessage("+" + value + " coin");
        gamePanel.player.coin += value;
        return true;
    }

    public OBJ_Coin_Bronze createNew(GamePanel gp){
        return new OBJ_Coin_Bronze(gp);
    }
}
