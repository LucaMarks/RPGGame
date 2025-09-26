package Entity;

import Main.GamePanel;
import Object.OBJ_Potion_Red;
import Object.OBJ_Shield_Blue;
import Object.OBJ_Key;
import Object.OBJ_Boots;

import java.awt.*;

public class Merchant extends Entity{

    public Merchant(GamePanel gp) {
        super(gp);
        direction = "down";

        name = "Merchant";

        solidArea = new Rectangle();
        solidArea.x = 8;
        solidArea.y = 16;

        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 32;
        solidArea.height = 32;

        getImage();
        setDialogue();
        setItems();
    }

    public void getImage(){

        up1 = setup("/NPC/merchant_down_1", gamePanel.tileSize, gamePanel.tileSize);
        up2 = setup("/NPC/merchant_down_2", gamePanel.tileSize, gamePanel.tileSize);
        down1 = setup("/NPC/merchant_down_1", gamePanel.tileSize, gamePanel.tileSize);
        down2 = setup("/NPC/merchant_down_2", gamePanel.tileSize, gamePanel.tileSize);
        left1 = setup("/NPC/merchant_down_1", gamePanel.tileSize, gamePanel.tileSize);
        left2 = setup("/NPC/merchant_down_2", gamePanel.tileSize, gamePanel.tileSize);
        right1 = setup("/NPC/merchant_down_1", gamePanel.tileSize, gamePanel.tileSize);
        right2 = setup("/NPC/merchant_down_2", gamePanel.tileSize, gamePanel.tileSize);

    }

    public void setDialogue(){
        dialogues[0][0] = "The traveler has arrived!";
        dialogues[0][1] = "I have some goodies to trade\n have a look";

        dialogues[1][0] = "Come again!";
        dialogues[2][0] = "You need more coin to buy that!";
        dialogues[3][0] = "Your inventory is full!";
        dialogues[4][0] = "You cannot sell an equipped item!";
    }

    public void setItems(){
        inventory.add(new OBJ_Potion_Red(gamePanel));
        inventory.add(new OBJ_Shield_Blue(gamePanel));
        inventory.add(new OBJ_Key(gamePanel));
        inventory.add(new OBJ_Boots(gamePanel));
    }

    public void speak(){

        facePlayer();
        gamePanel.gameState = gamePanel.tradeState;
        //System.out.println("Merchant.speak() - Setting npc reference: " + this.name);
        gamePanel.ui.npc = this;
        //System.out.println("Merchant.speak() - npc reference set: " + gamePanel.ui.npc.name);
        gamePanel.gameState = gamePanel.tradeState;
        //System.out.println("Merchant.speak() - Game state changed to: " + gamePanel.gameState);
        //reset trade state variables
        gamePanel.ui.commandNum = 0;
        gamePanel.ui.subState = 0;
    }
}
