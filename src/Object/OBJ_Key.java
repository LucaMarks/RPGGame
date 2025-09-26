package Object;

import Entity.Entity;
import Main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_Key extends Entity {

    public static final String objName = "Basic Key";

    public OBJ_Key(GamePanel gp){

        super(gp);
        type = type_consumable;
        name = objName;

        down1 = setup("/Objects/key", gp.tileSize, gp.tileSize);

        description = "{" +  name + "}\nOpens a door";

        price = 100;

        stackable = true;

        setDialogues();
    }

    public void setDialogues(){}

    public boolean use(Entity e) {

        int objIndex = getDetected(e, gamePanel.obj, "door");

        if (objIndex != 999) {
            gamePanel.ui.addMessage("Door Opened");
            gamePanel.playSE(3);
            gamePanel.obj[gamePanel.currMap][objIndex] = null;

            return true;
        }else{return false;}
    }

    public OBJ_Key createNew(GamePanel gp){
        return new OBJ_Key(gp);
    }

}
