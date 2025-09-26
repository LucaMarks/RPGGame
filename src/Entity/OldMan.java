package Entity;

import Main.GamePanel;
import Main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

public class OldMan extends Entity{

    public OldMan(GamePanel gp){
        super(gp);


        direction = "down";
        speed = 1;

        dialogueSet = -1;

        getImage();
        setDialogue();
    }

    public void setAction(){

        if(onPath){
            int goalCol = 12;
            int goalRow = 9;
//            int goalCol = (gamePanel.player.worldX + gamePanel.player.solidArea.x) / gamePanel.tileSize;
//            int goalRow = (gamePanel.player.worldY + gamePanel.player.solidArea.y) / gamePanel.tileSize;

            searchPath(goalCol, goalRow);
        }
        else {
            actionLockCounter++;

            if (actionLockCounter >= 120) {
                Random random = new Random();
                int i = random.nextInt(100) + 1; //Pick random number from 1-100

                if (i <= 25) {
                    direction = "up";
                }
                if (i > 25 && i <= 50) {
                    direction = "down";
                }
                if (i > 50 && i <= 75) {
                    direction = "left";
                }
                if (i > 75 && i <= 100) {
                    direction = "right";
                }

                actionLockCounter = 0;
            }
        }
    }

    public void getImage(){

        up1 = setup("/NPC/oldman_up_1", gamePanel.tileSize, gamePanel.tileSize);
        up2 = setup("/NPC/oldman_up_2", gamePanel.tileSize, gamePanel.tileSize);
        down1 = setup("/NPC/oldman_down_1", gamePanel.tileSize, gamePanel.tileSize);
        down2 = setup("/NPC/oldman_down_2", gamePanel.tileSize, gamePanel.tileSize);
        left1 = setup("/NPC/oldman_left_1", gamePanel.tileSize, gamePanel.tileSize);
        left2 = setup("/NPC/oldman_left_2", gamePanel.tileSize, gamePanel.tileSize);
        right1 = setup("/NPC/oldman_right_1", gamePanel.tileSize, gamePanel.tileSize);
        right2 = setup("/NPC/oldman_right_2", gamePanel.tileSize, gamePanel.tileSize);

        if(right1 == null){
            System.out.println("Right1 is null");
        }
        if (right2 == null) {
            System.out.println("Right2 is null");
        }else{
            System.out.println("Right is true");
        }
    }

    public void setDialogue(){
        dialogues[0][0] = "So, you must be the famous traveler\nI was wondering when you'd show up here";
        dialogues[0][1] = "You've come to this island to \nfind some treasure.";
        dialogues[0][2] = "I have an exciting adventure \nfor you";
        dialogues[0][3] = "I have been on this island for \na long time";
        dialogues[0][4] = "I can give you some hints to help \nyou on your journey";

        dialogues[1][0] = "If you need energy, rest at the pond.";
        dialogues[1][1] = "Monsters will also regain their strength";

        dialogues[2][0] = "I wonder where that door leads...";
    }

    //will also work without this rn
    public void speak(){
        //super.speak();
        facePlayer();
        startDialogue(this, dialogueSet);

        dialogueSet++;

        if(dialogues[dialogueSet][0] == null){dialogueSet = 0;
        //or dialogueSet--;
        }
        //onPath = true;
    }

}
