package Object;

import Entity.Entity;
import Main.GamePanel;

public class OBJ_Lantern extends Entity {

    public static final String objName = "Lantern";

    public OBJ_Lantern(GamePanel gp){
        super(gp);

        type = type_light;
        name = objName;
        down1 = setup("/Objects/lantern", gp.tileSize, gp.tileSize);
        description = "[" + name + "]\nIlluminates your \nsurroundings";
        price = 200;
        lightRadius = 250;
    }

    public OBJ_Lantern createNew(GamePanel gp){
        return new OBJ_Lantern(gp);
    }
}
