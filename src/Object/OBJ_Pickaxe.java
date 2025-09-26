package Object;

import Entity.Entity;
import Main.GamePanel;

public class OBJ_Pickaxe extends Entity {

    public final String objName = "Pickaxe";

    GamePanel gp;

    public OBJ_Pickaxe(GamePanel gp){
        super(gp);
        this.gp = gp;

        type = type_pickaxe;
        name = objName;
        down1 = setup("/Objects/pickaxe", gp.tileSize, gp.tileSize);
        attackValue = 0;
        attackArea.width = 36;
        attackArea.height = 36;
        motion1_duration = 10;
        motion2_duration = 30;
        description = "{" + name + "}\n An old pickaxe use\n for gatheing stone & ores";
        price = 100;
        knockBackValue = 2;
        stackable = false;
    }

    public OBJ_Pickaxe createNew(GamePanel gp){return new OBJ_Pickaxe(gp);}
}
