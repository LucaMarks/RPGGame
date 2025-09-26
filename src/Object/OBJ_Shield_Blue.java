package Object;

import Entity.Entity;
import Main.GamePanel;

public class OBJ_Shield_Blue extends Entity{

    public static final String objName = "Sapphire Shield";

    public OBJ_Shield_Blue(GamePanel gp){
        super(gp);

        type = type_shield;
        name = objName;
        down1 = setup("/Objects/shield_blue", gp.tileSize, gp.tileSize);
        defenseValue = 2;
        description = "{" + name + "} Forged out of\nsapphire to\nprotect the weilder";
        price = 220;

    }

    public OBJ_Shield_Blue createNew(GamePanel gp){
        return new OBJ_Shield_Blue(gp);
    }
}
