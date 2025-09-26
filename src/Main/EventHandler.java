package Main;

import Entity.Entity;

import java.awt.*;

public class EventHandler {

    GamePanel gp;
    EventRect eventRect[][][];
    Entity eventMaster;

    int prevEventX, prevEventY;
    boolean canTouchEvent = true;
    int tempMap, tempCol, tempRow;

    private int dayState = 0;
    private float filterAlpha = 1;

    public EventHandler(GamePanel gp){
        this.gp = gp;
        eventMaster = new Entity(gp);

        eventRect = new EventRect[gp.maxMap][gp.maxWorldCol][gp.maxWorldRow];

        int map = 0;
        int col = 0;
        int row = 0;
        while(map < gp.maxMap && col < gp.maxWorldCol && row < gp.maxWorldRow){
            eventRect[map][col][row] = new EventRect();
            eventRect[map][col][row].x = 23;
            eventRect[map][col][row].y = 23;
            eventRect[map][col][row].width = 2;
            eventRect[map][col][row].height = 2;

            eventRect[map][col][row].eventRectDefaultX = eventRect[map][col][row].x;
            eventRect[map][col][row].eventRectDefaultY = eventRect[map][col][row].y;

            col++;
            if(col == gp.maxWorldCol){
                col = 0;
                row++;

                if(row == gp.maxWorldRow){
                    row = 0;
                    map++;
                }
            }
        }

        setDialogues();
    }

    public void setDialogues(){
        eventMaster.dialogues[0][0] = "You fell into a pit!";

        eventMaster.dialogues[1][0] = "You drink the water.\nYour life and mana have been restored.\n(Progress has been saved)";
    }

    public void checkEvent(){

        //check if player character is >= 1 tile away from last event
        //Damage pit loc: col 27, row 16

        int xDistance = Math.abs(gp.player.worldX - prevEventX);
        int yDistance = Math.abs(gp.player.worldY - prevEventY);
        int distance = Math.max(xDistance, yDistance);
        if(distance > gp.tileSize){
            canTouchEvent = true;}

        if(canTouchEvent == true){
        if(hit(27, 16, "right", 0)){damagePit(gp.dialogueState);}
        else if(hit(23, 12, "up", 0)){healingPool(gp.dialogueState);}

        else if(hit(22, 7, "any", 0)){teleport(gp.playState);}

        //merchant
        else if(hit(10, 39, "any", 0)){mapTel(1, 12, 13, gp.inside);}
        else if(hit(12, 13, "any", 1)){mapTel(0, 10, 39, gp.outside);}

        else if(hit(12, 9, "up", 1)){speak(gp.npc[1][0]);}

        //tent
        else if(hit(12, 13, "any", 2)){
            mapTel(0, 13, 38, gp.outside);
            gp.envManager.lighting.dayState = gp.envManager.lighting.day;
            gp.envManager.lighting.filterAlpha = 0;
            gp.envManager.lighting.dayCounter = 0;
        }

        //dungeon
        else if(hit( 12, 9, "any", 0)){

            //save light settings for exit later
            dayState = gp.envManager.lighting.dayState;
            filterAlpha = gp.envManager.lighting.filterAlpha;

            mapTel(3, 9, 41, gp.dungeon);

            gp.assetSetter.setObject();//y did i even have this here i think this causes the issue with the door

        }
        else if(hit(9, 41, "any", 3)){

            //add the light settings back
            gp.envManager.lighting.dayState = dayState;
            gp.envManager.lighting.filterAlpha = filterAlpha;

            mapTel(0, 12, 9, gp.outside);
        }
        else if(hit(8, 7, "any", 3)){mapTel(4, 26, 41, gp.dungeon);}
        else if(hit(26, 41, "any", 4)){mapTel(3, 8, 7, gp.dungeon);}
    }
        }

    public boolean hit(int eventCol, int eventRow, String reqDirection, int map){

        boolean hit = false;

        if(map == gp.currMap) {

            gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
            gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;
            eventRect[map][eventCol][eventRow].x = eventCol * gp.tileSize + eventRect[map][eventCol][eventRow].x;
            eventRect[map][eventCol][eventRow].y = eventRow * gp.tileSize + eventRect[map][eventCol][eventRow].y;
            if (gp.player.solidArea.intersects(eventRect[map][eventCol][eventRow]) && eventRect[map][eventCol][eventRow].eventDone == false) {
                if (gp.player.direction.contentEquals(reqDirection) || reqDirection.contentEquals("any")) {
                    hit = true;

                    prevEventX = gp.player.worldX;
                    prevEventY = gp.player.worldY;
                }
            }

            gp.player.solidArea.x = gp.player.solidAreaDefaultX;
            gp.player.solidArea.y = gp.player.solidAreaDefaultY;
            eventRect[map][eventCol][eventRow].x = eventRect[map][eventCol][eventRow].eventRectDefaultX;
            eventRect[map][eventCol][eventRow].y = eventRect[map][eventCol][eventRow].eventRectDefaultY;
        }

        return hit;
    }

    public void damagePit(int gameState){

        gp.gameState = gameState;

        eventMaster.startDialogue(eventMaster, 0);
        gp.player.life -= 1;

        //eventRect[col][row].eventDone = true;
        canTouchEvent = false;
    }

    public void healingPool(int gameState){
        //System.out.println(gp.keyHandler.enterPressed + ", " + gp.keyHandler.spacePressed);
        if(gp.keyHandler.spacePressed || gp.keyHandler.enterPressed){
            System.out.println("space pressed is in correct location!");
            gp.player.attackCanceled = true;
            gp.gameState = gameState;
            eventMaster.startDialogue(eventMaster, 1);
            gp.player.life = gp.player.maxLife;
            gp.player.mana = gp.player.maxMana;
            gp.assetSetter.setMonster();
            gp.saveLoad.save();
        }
    }

    public void teleport(int gameState){
        if(gp.keyHandler.spacePressed){
            gp.gameState = gameState;
            gp.player.worldX = gp.tileSize * 36;
            gp.player.worldY = gp.tileSize * 38;
        }
    }

    public void mapTel(int mapTo, int col, int row, int area){


        //gp.gameState = gp.transitionState;
        gp.nextArea = area;
        tempMap = mapTo;
        tempCol = col;
        tempRow = row;


        gp.currMap = mapTo;
        gp.player.worldX = gp.tileSize * col;
        gp.player.worldY = gp.tileSize * row;

        prevEventX = gp.player.worldX;
        prevEventY = gp.player.worldY;
        canTouchEvent = false;
        gp.playSE(12);

        gp.changeArea();
    }

    public void speak(Entity entity){

        if(gp.keyHandler.enterPressed || gp.keyHandler.spacePressed){
            gp.gameState = gp.dialogueState;
            gp.player.attackCanceled = true;
            entity.speak();
        }
    }
}
