package Object;

import Entity.Entity;
import Main.GamePanel;

public class OBJ_Iron_Door extends Entity {

    GamePanel gp;
    public static final String objName = "Iron Door";

    public OBJ_Iron_Door(GamePanel gp){
        super(gp);

        type = type_obstacle;
        name = objName;

        down1 = setup("/Objects/door_iron", gp.tileSize, gp.tileSize);
        Collision = true;

        solidArea.x = 0;
        solidArea.y = 16;
        solidArea.width = 48;
        solidArea.height = 32;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        setDialogue();
    }

    public void setDialogue(){

        dialogues[0][0] = "You need somthing stronger to open this...";
    }

    public void interact(){

        startDialogue(this, 0);
    }

    public OBJ_Iron_Door createNew(GamePanel gp){return new OBJ_Iron_Door(gp);}
}
