package Monster;

import Entity.Entity;
import Main.GamePanel;
import Object.OBJ_Coin_Bronze;
import Object.OBJ_Heart;
import Object.OBJ_ManaCrystal;

import java.util.Random;

public class MON_ORC extends Entity {

    public MON_ORC(GamePanel gp){
        super(gp);

        name = "Green Slime";
        defaultSpeed = 1;
        speed = defaultSpeed;
        maxLife = 4;
        life = maxLife;
        type = type_monster;
        attack = 10;
        defense = 1;
        exp = 7;
        knockBackValue = 5;
        motion1_duration = 25;
        motion2_duration = 65;
        solidArea.x = 3;
        solidArea.y = 18;
        solidArea.width = 42;
        solidArea.height = 30;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        attackArea.width = 48;
        attackArea.height = 48;
        getImage();
        getAttackImage();
    }

    public void getImage(){
        up1 = setup("/Monster/orc_up_1", gamePanel.tileSize, gamePanel.tileSize);
        up2 = setup("/Monster/orc_up_2", gamePanel.tileSize, gamePanel.tileSize);
        down1 = setup("/Monster/orc_down_1", gamePanel.tileSize, gamePanel.tileSize);
        down2 = setup("/Monster/orc_down_2", gamePanel.tileSize, gamePanel.tileSize);
        left1 = setup("/Monster/orc_left_1", gamePanel.tileSize, gamePanel.tileSize);
        left2 = setup("/Monster/orc_left_2", gamePanel.tileSize, gamePanel.tileSize);
        right1 = setup("/Monster/orc_right_1", gamePanel.tileSize, gamePanel.tileSize);
        right2 = setup("/Monster/orc_right_2", gamePanel.tileSize, gamePanel.tileSize);
    }

    public void getAttackImage(){
        attackUp1 = setup("/Monster/orc_attack_up_1", gamePanel.tileSize, gamePanel.tileSize * 2);
        attackUp2 = setup("/Monster/orc_attack_up_2", gamePanel.tileSize, gamePanel.tileSize * 2);
        attackRight1 = setup("/Monster/orc_attack_right_1", gamePanel.tileSize * 2, gamePanel.tileSize);
        attackRight2 = setup("/Monster/orc_attack_right_2", gamePanel.tileSize * 2, gamePanel.tileSize);
        attackLeft1 = setup("/Monster/orc_attack_left_1", gamePanel.tileSize * 2, gamePanel.tileSize);
        attackLeft2 = setup("/Monster/orc_attack_left_2", gamePanel.tileSize * 2, gamePanel.tileSize);
        attackDown1 = setup("/Monster/orc_attack_down_1", gamePanel.tileSize, gamePanel.tileSize * 2);
        attackDown2 = setup("/Monster/orc_attack_down_2", gamePanel.tileSize, gamePanel.tileSize * 2);
    }

    public void setAction(){

        //Check proximity for health bar
        barProximityChecker(gamePanel.player);

//        int xDistance = Math.abs(worldX - gamePanel.player.worldX);
//        int yDistance = Math.abs(worldY - gamePanel.player.worldY);
//        int tileDistance = (xDistance + yDistance) / gamePanel.tileSize;

//        if(onPath == false && tileDistance < 5){
//
//            int i  = new Random().nextInt(100) + 1;
//
//            if(i > 50){onPath = true;}
//        }
        //if(onPath && tileDistance > 20){onPath = false;}


        actionLockCounter++;

        if(actionLockCounter >= 120){
            Random random = new Random();
            int i = random.nextInt(100) + 1; //Pick random number from 1-100

            if (i <= 25) {direction = "up";}

            if (i > 25 && i <= 50) {direction = "down";}

            if (i > 50 && i <= 75) {direction = "left";}

            if (i > 75 && i <= 100) {direction = "right";}

            actionLockCounter = 0;
        }

        //check if it attacks
        if(attacking == false){
            checkAttackOrNot(30, gamePanel.tileSize * 4, gamePanel.tileSize);
        }
//        int i = new Random().nextInt(100) + 1;
//        if(i > 99 && projectile.alive == false && shotAvailableCounter == 30){
//            projectile.set(worldX, worldY, direction, true, this);
////            gamePanel.projectileList.add(projectile);
//            for(int j = 0; j < gamePanel.projectile[1].length; j++){
//                if(gamePanel.projectile[gamePanel.currMap][j] == null){
//                    gamePanel.projectile[gamePanel.currMap][j] = projectile;
//                    break;
//                }
//            }
//            shotAvailableCounter = 0;
//        }
    }

    //set action
//    public void setAction() {
//
//        if (onPath) {
////            int goalCol = 12;
////            int goalRow = 9;
//            int goalCol = (gamePanel.player.worldX + gamePanel.player.solidArea.x) / gamePanel.tileSize;
//            int goalRow = (gamePanel.player.worldY + gamePanel.player.solidArea.y) / gamePanel.tileSize;
//
//            searchPath(goalCol, goalRow);
//
//            int i = new Random().nextInt(100) + 1;
//            if (i > 197 && projectile.alive == false && shotAvailableCounter == 30) {
//                projectile.set(worldX, worldY, direction, true, this);
//                //gamePanel.projectileList.add(projectile);
//
//                //check vacancy
//                for(int j = 0; j < gamePanel.projectile[1].length; j++){
//                    if(gamePanel.projectile[gamePanel.currMap][j] == null){gamePanel.projectile[gamePanel.currMap][j] = projectile;break;}
//                }
//
//                shotAvailableCounter = 0;
//            } else {
//                actionLockCounter++;
//
//                if (actionLockCounter >= 120) {
//                    Random random = new Random();
//                    i = random.nextInt(100) + 1; //Pick random number from 1-100
//
//                    if (i <= 25) {
//                        direction = "up";
//                    }
//                    if (i > 25 && i <= 50) {
//                        direction = "down";
//                    }
//                    if (i > 50 && i <= 75) {
//                        direction = "left";
//                    }
//                    if (i > 75 && i <= 100) {
//                        direction = "right";
//                    }
//
//                    actionLockCounter = 0;
//                }
//            }
//        }
//    }


    public void damageReaction(){
        actionLockCounter = 0;
        //direction = gamePanel.player.direction;
        //onPath = true;
    }

    public void checkDrop(){

        int i = new Random().nextInt(100) + 1;

        //set the mosnster drop
        if(i < 50){dropItem(new OBJ_Coin_Bronze(gamePanel));}
        if(i >= 50 && i < 75){dropItem(new OBJ_Heart(gamePanel));}
        if(i >= 75){dropItem(new OBJ_ManaCrystal(gamePanel));}
    }

}
