package Monster;

import Entity.Entity;
import Main.GamePanel;

import java.awt.*;

public class MON_SkeletonBoss extends Entity {

    GamePanel gp;
    public static final String monName = "Skeleton Lord";

    public MON_SkeletonBoss(GamePanel gp){
        super(gp);
        this.gp = gp;

        name = monName;
        defaultSpeed = 2;
        speed = defaultSpeed;
        maxLife = 50;
        type = type_monster;
        life = maxLife;
        attack = 10;
        defense = 2;
        exp = 50;
        knockBackValue = 5;
        motion1_duration = 25;
        motion2_duration = 50;

        int size = gp.tileSize * 5;
        solidArea = new Rectangle();
        solidArea.x = 48;
        solidArea.y = 48;
        solidArea.width = size - 48*2;
        solidArea.height = size - 48;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        attackArea.width = 170;
        attackArea.height = 170;

        getImage();
        getAttackImage();
    }

    public void getImage(){

        int i = 5;

        up1 = setup("/Boss/skeletonlord_up_1", gp.tileSize * i,gp.tileSize * i);
        up2 = setup("/Boss/skeletonlord_up_2", gp.tileSize * i, gp.tileSize * i);
        down1 = setup("/Boss/skeletonlord_down_1", gp.tileSize * i, gp.tileSize * i);
        down2 = setup("/Boss/skeletonlord_down_2", gp.tileSize * i, gp.tileSize * i);
        left1 = setup("/Boss/skeletonlord_left_1", gp.tileSize * i, gp.tileSize * i);
        left2 = setup("/Boss/skeletonlord_left_2", gp.tileSize * i, gp.tileSize * i);
        right1 = setup("/Boss/skeletonlord_right_1", gp.tileSize * i, gp.tileSize * i);
        right2 = setup("/Boss/skeletonlord_right_2", gp.tileSize * i, gp.tileSize * i);
    }
    public void getAttackImage(){

        int i = 5;

        attackUp1 = setup("/Boss/skeletonlord_attack_up_1", gp.tileSize * i, gp.tileSize * i * 2);
        attackUp2 = setup("/Boss/skeletonlord_attack_up_2", gp.tileSize * i, gp.tileSize * i * 2);
        attackDown1 = setup("/Boss/skeletonlord_attack_down_1", gp.tileSize * i, gp.tileSize * i * 2);
        attackDown2 = setup("/Boss/skeletonlord_attack_down_2", gp.tileSize * i, gp.tileSize * i * 2);
        attackLeft1 = setup("/Boss/skeletonlord_attack_left_1", gp.tileSize * 2 * i, gp.tileSize * i);
        attackLeft2 = setup("/Boss/skeletonlord_attack_left_2", gp.tileSize * 2 * i, gp.tileSize * i);
        attackRight1 = setup("/Boss/skeletonlord_attack_right_1", gp.tileSize * 2 * i, gp.tileSize * i);
        attackRight2 = setup("/Boss/skeletonlord_attack_right_2", gp.tileSize * 2 * i, gp.tileSize * i);
    }

    public void setAction(){

        //check if it attacks

        if(getTileDistance(gp.player) < 10){

            dirPlayer(60);
        }else{
            getRandomDir(120);
        }

        if(!attacking){
            checkAttackOrNot(60, gp.tileSize * 7, gp.tileSize * 5);
        }

    }

    public void damageReaction(){
        actionLockCounter = 0;
    }
}
