package Object;

import Entity.Entity;
import Main.GamePanel;

public class OBJ_ManaCrystal extends Entity {

    public static final String objName = "Mana Crystal";

    public OBJ_ManaCrystal(GamePanel gp){
        super(gp);

        type = type_pickupOnly;
        name = objName;
        image = setup("/Objects/manacrystal_full", gp.tileSize, gp.tileSize);
        image2 = setup("/Objects/manacrystal_blank", gp.tileSize, gp.tileSize);
        down1 = image;
        value = 1;

    }

    public boolean use(Entity e){
        gamePanel.playSE(2);
        gamePanel.ui.addMessage("+" + value + " Mana");
        e.mana += value;

        return true;
    }

    public OBJ_ManaCrystal createNew(GamePanel gp){
        return new OBJ_ManaCrystal(gp);
    }
}
