package Object;

import Entity.Entity;
import Main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_Heart extends Entity {

    public static final String objName = "heart";

    public OBJ_Heart(GamePanel gp){
        super(gp);

        type = type_pickupOnly;
        value = 2;
        image = setup("/Objects/heart_blank", gp.tileSize, gp.tileSize);
        image2 = setup("/Objects/heart_full", gp.tileSize, gp.tileSize);
        image3 = setup("/Objects/heart_half", gp.tileSize, gp.tileSize);
        down1 = image2;


        name = objName;

        //scaleImage("heart_full");
                                                        //This 1 has an issue where it is the same as heart blank. Wait my genius is frightening Edit: naw im restarted
        /*image = scaleImage("heart_blank");
        image2 = scaleImage("heart_full");
        image3 = scaleImage("heart_half");*/

    }

    public boolean use(Entity e){
        gamePanel.playSE(2);
        gamePanel.ui.addMessage("+" + value + " Life");
        e.life += value;

        return true;
    }

    public OBJ_Heart createNew(GamePanel gp){
        return new OBJ_Heart(gp);
    }

}
