package Entity;

import InteractiveTile.InteractiveTile;
import Main.GamePanel;
import InteractiveTile.MetalPlate;
import Object.OBJ_Iron_Door;

import java.awt.*;
import java.util.ArrayList;

public class BigRock extends Entity{

    GamePanel gp;
    public final static String npcName = "Big Rock";

    public BigRock(GamePanel gp){
        super(gp);
        this.gp = gp;
        name = npcName;

        solidArea = new Rectangle();
        solidArea.x = 8;
        solidArea.y = 16;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 44;
        solidArea.height = 40;

        speed = 1;

        dialogueSet = -1;

        getImage();
        setDialogues();

    }

    public void getImage(){

        up1 = setup("/NPC/bigrock", gp.tileSize, gp.tileSize);
        up2 = setup("/NPC/bigrock", gp.tileSize, gp.tileSize);
        down1 = setup("/NPC/bigrock", gp.tileSize, gp.tileSize);
        down2 = setup("/NPC/bigrock", gp.tileSize, gp.tileSize);
        left1 = setup("/NPC/bigrock", gp.tileSize, gp.tileSize);
        left2 = setup("/NPC/bigrock", gp.tileSize, gp.tileSize);
        right1 = setup("/NPC/bigrock", gp.tileSize, gp.tileSize);
        right2 = setup("/NPC/bigrock", gp.tileSize, gp.tileSize);

    }

    public void move(String direction){

        this.direction = direction;
        checkCollision();
        if(!collisionOn){
            switch(direction){
                case "up": worldY -= speed;break;
                case "down": worldY += speed;break;
                case "left": worldX -= speed;break;
                case "right": worldX += speed; break;
            }
        }

        detectPlate();
    }

    public void detectPlate(){

        ArrayList<InteractiveTile> plateList = new ArrayList<>();
        ArrayList<Entity> rockList = new ArrayList<>();

        //add to plateList
        for(int i = 0; i < gp.iTile[1].length; i++){

            if(gp.iTile[gp.currMap][i] != null && gp.iTile[gp.currMap][i].name.equals(MetalPlate.itName)){
                plateList.add(gp.iTile[gp.currMap][i]);
            }
        }

        //add to rockList
        for(int i = 0; i < gp.npc[1].length; i++){
            if(gp.npc[gp.currMap][i] != null && gp.npc[gp.currMap][i].name.equals(BigRock.npcName)){
                rockList.add(gp.npc[gp.currMap][i]);
            }
        }


        //scan the plateList
        for(int i = 0; i < plateList.size(); i++){

            int distanceX = Math.abs(this.worldX - plateList.get(i).worldX);
            int distanceY = Math.abs(this.worldY - plateList.get(i).worldY);
//            int distance = (int) Math.abs(Math.sqrt((distanceX * distanceX) + (distanceY * distanceY)));
            int distance = Math.max(distanceX, distanceY);
            System.out.println(plateList.indexOf(plateList.get(i)) + "->" + distance);
            if(distance <= 9){
                if(linkedEntity == null) {
                    linkedEntity = plateList.get(i);
                    gp.playSE(3);
                }
            }else{
                if(linkedEntity == plateList.get(i)) {
                    linkedEntity = null;
                }
            }
        }

        //scan the rock list -> this part should be the same for all 3 instances of this class
        int count = 0;
        for(int i = 0; i < rockList.size(); i++){

            //count the rock on the plate
            if(rockList.get(i).linkedEntity != null){
                count++;
            }

            //if all rocks are on the plates -> open the iron door
            if(count == rockList.size()){

                //find the iron door in the OBJ list and open it
                for(int j = 0; j < gp.obj[1].length; j++){

                    if(gp.obj[gp.currMap][j] != null && gp.obj[gp.currMap][j].name.equals(OBJ_Iron_Door.objName) && gp.obj[gp.currMap][j].openOnRockPuzzle){
                        gp.obj[gp.currMap][j] = null;gp.playSE(18);
                    }
                }
            }
        }
    }

    public void setDialogues(){
        dialogues[0][0] = "A giant Rock!";
    }

    //@Override
    public void update(){
        spriteCounter++;
        if(spriteCounter > 24){
            if(spriteNumber == 1){spriteNumber = 2;}
            else if(spriteNumber == 2){spriteNumber = 1;}
            spriteCounter = 0;
        }

        // Keep invincibility logic if needed
        if(invincible == true){
            invincibleCounter++;
            if(invincibleCounter > 40){
                invincible = false;
                invincibleCounter = 0;
            }
        }
    }

    public void setAction(){}

    public void speak(){

        startDialogue(this, dialogueSet);

        dialogueSet++;

        if(dialogues[dialogueSet][0] == null){
            dialogueSet = 0;
        }
    }

}
