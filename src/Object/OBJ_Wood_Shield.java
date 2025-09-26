package Object;

import Entity.Entity;
import Main.GamePanel;

public class OBJ_Wood_Shield extends Entity {

    public static final String objName = "Wood Shield";

    public OBJ_Wood_Shield(GamePanel gp){
        super(gp);

        name = objName;
        down1 = setup("/Objects/shield_wood", gp.tileSize, gp.tileSize);
        defenseValue = 0;
        type = type_shield;
        description = "{" +  name + "}\nAn old Shield";

    }

    public OBJ_Wood_Shield createNew(GamePanel gp){
        return new OBJ_Wood_Shield(gp);
    }
}
