package Object;

import Entity.Entity;
import Main.GamePanel;

public class OBJ_Sword_Normal extends Entity {

    public static final String objName = "Normal Sword";

    public OBJ_Sword_Normal(GamePanel gp){
        super(gp);

        name = objName;
        down1 = setup("/Objects/sword_normal", gp.tileSize, gp.tileSize);
        attackValue = 1;
        attackArea.width = 36;
        attackArea.height = 36;
        type = type_sword;
        motion1_duration = 5;
        motion2_duration = 25;
        description = "{" + name + "}\nAn old sword";
        price = 20;
        knockBackValue = 10;

    }

    public OBJ_Sword_Normal createNew(GamePanel gp){
        return new OBJ_Sword_Normal(gp);
    }
}
