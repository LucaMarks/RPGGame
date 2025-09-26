package Object;

import Entity.Entity;
import Main.GamePanel;

public class OBJ_Axe extends Entity{

    public static final String objName = "Woodcutter's Axe";

    public OBJ_Axe(GamePanel gp){
        super(gp);
        name = objName;
        down1 = setup("/Objects/axe", gp.tileSize, gp.tileSize);
        attackValue = 2;
        attackArea.width = 30;
        attackArea.height = 30;
        type = type_axe;
        motion1_duration = 20;
        motion2_duration = 40;
        description = "{" + name + "}\n A rusty axe that can pack a punch";
        price = 75;
        knockBackValue = 4;
    }

    public OBJ_Axe createNew(GamePanel gp){
        return new OBJ_Axe(gp);
    }
}
