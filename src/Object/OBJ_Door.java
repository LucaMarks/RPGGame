package Object;

import Entity.Entity;
import Main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_Door extends Entity {

    public static final String objName = "door";

    public OBJ_Door(GamePanel gp){
        super(gp);

        name = objName;
        type = type_obstacle;

        down1 = setup("/Objects/door", gp.tileSize, gp.tileSize);
        Collision = true;

        solidArea.x = 0;
        solidArea.y = 16;
        solidArea.width = 48;
        solidArea.height = 32;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        setDialogues();
    }

    public void setDialogues(){
        dialogues[0][0] = "You need a key to open this.";
    }

    public void interact(){
        startDialogue(this, 0);
    }

    public OBJ_Door createNew(GamePanel gp){
        return new OBJ_Door(gp);
    }
}
