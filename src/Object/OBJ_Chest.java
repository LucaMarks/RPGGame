package Object;

import Entity.Entity;
import Main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_Chest extends Entity {

    public static final String objName = "chest";

    public OBJ_Chest(GamePanel gp){
        super(gp);
        type = type_obstacle;
        name = objName;
        image = setup("/Objects/chest", gp.tileSize, gp.tileSize);
        image2 = setup("/Objects/chest_opened", gp.tileSize, gp.tileSize);
        down1 = image;
        collisionOn = true;

        //set solid area
        solidArea.x = 4;
        solidArea.y = 16;
        solidArea.width = 40;
        solidArea.height = 32;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

    }

    public void setLoot(Entity loot){
        this.loot = loot;
    }

    public void interact(){

        gamePanel.gameState = gamePanel.dialogueState;

        //we can open the chest
        if(!opened){
            gamePanel.playSE(3);

            StringBuilder sb = new StringBuilder();
            sb.append("You opened a chest and find a " + loot.name + "!");

            if(!gamePanel.player.canObtainItem(loot)){
                sb.append("/n...but your inventory is full!");
            }else{
                down1 = image2;
                opened = true;
                gamePanel.player.inventory.add(loot);
            }

            dialogues[0][0] = sb.toString();
            startDialogue(this, 0);
        }else{
            dialogues[1][0] = "Chest is empty";
            startDialogue(this, 1);
        }
    }

    public OBJ_Chest createNew(GamePanel gp){
        return new OBJ_Chest(gp);
    }
}
