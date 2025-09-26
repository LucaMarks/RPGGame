package Object;

import Entity.Entity;
import Main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class OBJ_Boots extends Entity {

    public static final String objName = "boots";

    public OBJ_Boots(GamePanel gp) {
        super(gp);
        name = objName;
        down1 = setup("/Objects/boots",gp.tileSize, gp.tileSize);
    }

    public OBJ_Boots createNew(GamePanel gp){
        return new OBJ_Boots(gp);
    }

    }
