package Object;

import Entity.Entity;
import Main.GamePanel;

public class OBJ_Potion_Red extends Entity {

    public static final String objName = "Healing Potion?";

    public OBJ_Potion_Red(GamePanel gp){
        super(gp);

        down1 = setup("/Objects/potion_red", gp.tileSize, gp.tileSize);
        type = type_consumable;
        name = objName;
        value = 5;
        description = "{" + name + "}\nRecovers " + value + " life";
        price = 25;
        stackable = true;

        setDialogues();
    }

    public void setDialogues(){}

    public boolean use(Entity e){
        e.life += value;
        if(e.life > e.maxLife){
            int diff = 5 - (e.life - e.maxLife);
            gamePanel.ui.addMessage("+" + diff + " Life");
        }else {gamePanel.ui.addMessage("+" + value + " Life");}

        gamePanel.playSE(2);
        return true;
    }

    public OBJ_Potion_Red createNew(GamePanel gp){
        return new OBJ_Potion_Red(gp);
    }
}
