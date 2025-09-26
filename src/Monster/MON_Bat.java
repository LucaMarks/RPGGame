package Monster;

import Entity.Entity;
import Main.GamePanel;

import java.awt.*;
import java.util.Random;

public class MON_Bat extends Entity {

    GamePanel gp;

    public MON_Bat(GamePanel gp){
        super(gp);
        this.gp = gp;

        name = "Bat";
        defaultSpeed = 5;
        speed = defaultSpeed;
        maxLife = 3;
        life = maxLife;
        type = type_monster;
        attack = 3;
        defense = 1;
        exp = 1;

        solidArea = new Rectangle();
        solidArea.x = 3;
        solidArea.y = 8;
        solidArea.width = 20;
        solidArea.height = 15;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        getImage();
    }

    public void getImage(){
        up1 = setup("/Monster/bat_down_1", gamePanel.tileSize, gamePanel.tileSize);
        up2 = setup("/Monster/bat_down_2", gamePanel.tileSize, gamePanel.tileSize);
        down1 = setup("/Monster/bat_down_1", gamePanel.tileSize, gamePanel.tileSize);
        down2 = setup("/Monster/bat_down_2", gamePanel.tileSize, gamePanel.tileSize);
        left1 = setup("/Monster/bat_down_1", gamePanel.tileSize, gamePanel.tileSize);
        left2 = setup("/Monster/bat_down_2", gamePanel.tileSize, gamePanel.tileSize);
        right1 = setup("/Monster/bat_down_1", gamePanel.tileSize, gamePanel.tileSize);
        right2 = setup("/Monster/bat_down_2", gamePanel.tileSize, gamePanel.tileSize);
    }

    public void setAction(){
        barProximityChecker(gamePanel.player);

        actionLockCounter++;

        if(actionLockCounter >= 60){
            Random random = new Random();
            int i = random.nextInt(100) + 1; //Pick random number from 1-100

            if (i <= 25) {direction = "up";}

            if (i > 25 && i <= 50) {direction = "down";}

            if (i > 50 && i <= 75) {direction = "left";}

            if (i > 75 && i <= 100) {direction = "right";}

            actionLockCounter = 0;
        }


    }

    public void damageReaction(){
        actionLockCounter = 0;
    }
}
