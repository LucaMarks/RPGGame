package Main;

import Entity.Entity;

public class CollisionChecker {
    GamePanel gamePanel;
    public CollisionChecker(GamePanel gp){
            gamePanel = gp;
    }

    public void checkTile(Entity e){

        int entityLeftWorldX = e.worldX + e.solidArea.x;
        int entityRightWorldX = e.worldX + e.solidArea.x + e.solidArea.width;

        int entityTopWorldY = e.worldY + e.solidArea.y;
        int entityBottomWorldY = e.worldY + e.solidArea.y + e.solidArea.height;


        int entityLeftCol = entityLeftWorldX/gamePanel.tileSize;
        int entityRightCol = entityRightWorldX / gamePanel.tileSize;
        int entityTopRow = entityTopWorldY / gamePanel.tileSize;
        int entityBottomRow = entityBottomWorldY / gamePanel.tileSize;

        int tileNum1, tileNum2;

        //use a temporary direction when it's knocked back
        String direction = e.direction;
        if(e.knockBack){direction = e.knockBackDirection;}

        switch(direction) {
            case "up":
                entityTopRow = (entityTopWorldY - e.speed) / gamePanel.tileSize;
                tileNum1 = gamePanel.tileManager.mapTileNum [gamePanel.currMap][entityLeftCol] [entityTopRow];
                tileNum2 = gamePanel.tileManager.mapTileNum [gamePanel.currMap][entityRightCol] [entityTopRow];
                if(gamePanel.tileManager.tile[tileNum1].collision == true || gamePanel.tileManager.tile[tileNum2].collision == true){
                    e.collisionOn = true;
                }
                break;
            case "down":
                entityBottomRow = (entityBottomWorldY + e.speed) / gamePanel.tileSize;
                tileNum1 = gamePanel.tileManager.mapTileNum [gamePanel.currMap][entityLeftCol] [entityBottomRow];
                tileNum2 = gamePanel.tileManager.mapTileNum [gamePanel.currMap][entityRightCol] [entityBottomRow];
                if(gamePanel.tileManager.tile[tileNum1].collision == true || gamePanel.tileManager.tile[tileNum2].collision == true){
                    e.collisionOn = true;}
                break;
            case "left":
                entityLeftCol = (entityLeftWorldX - e.speed) / gamePanel.tileSize;
                tileNum1 = gamePanel.tileManager.mapTileNum [gamePanel.currMap][entityLeftCol] [entityTopRow];
                tileNum2 = gamePanel.tileManager.mapTileNum [gamePanel.currMap][entityLeftCol] [entityBottomRow];
                if(gamePanel.tileManager.tile[tileNum1].collision == true || gamePanel.tileManager.tile[tileNum2].collision == true){
                    e.collisionOn = true;}
                break;
            case "right":
                entityRightCol = (entityRightWorldX + e.speed) / gamePanel.tileSize;
                tileNum1 = gamePanel.tileManager.mapTileNum [gamePanel.currMap][entityRightCol] [entityTopRow];
                tileNum2 = gamePanel.tileManager.mapTileNum [gamePanel.currMap][entityRightCol] [entityBottomRow];
                if(gamePanel.tileManager.tile[tileNum1].collision == true || gamePanel.tileManager.tile[tileNum2].collision == true){
                    e.collisionOn = true;}
                break;
        }
    }

    public int checkObject(Entity e, boolean player){

        int index = 999;

        //use a temporal direction when it's being knockbacked
        String direction = e.direction;
        if(e.knockBack){direction = e.knockBackDirection;}

        for(int i = 0; i < gamePanel.obj[1].length; i++){

            if(gamePanel.obj[gamePanel.currMap][i] != null){

                //Get entity's solid area position

                e.solidArea.x = e.worldX + e.solidArea.x;
                e.solidArea.y = e.worldY + e.solidArea.y;

                //Get object's solid area position

                gamePanel.obj[gamePanel.currMap][i].solidArea.x = gamePanel.obj[gamePanel.currMap][i].worldX + gamePanel.obj[gamePanel.currMap][i].solidArea.x;
                gamePanel.obj[gamePanel.currMap][i].solidArea.y = gamePanel.obj[gamePanel.currMap][i].worldY + gamePanel.obj[gamePanel.currMap][i].solidArea.y;

                switch(direction) {

                    case "up": e.solidArea.y -= e.speed;break;
                    case "down": e.solidArea.y += e.speed;break;
                    case "left": e.solidArea.x -= e.speed;break;
                    case "right": e.solidArea.x += e.speed;break;
                }
                if(e.solidArea.intersects(gamePanel.obj[gamePanel.currMap][i].solidArea)){
                    if(gamePanel.obj[gamePanel.currMap][i].Collision == true){
                        e.collisionOn = true;
                    }
                    if(player == true){
                        index = i;
                    }
                }

                e.solidArea.x = e.solidAreaDefaultX;
                e.solidArea.y = e.solidAreaDefaultY;
                gamePanel.obj[gamePanel.currMap][i].solidArea.x = gamePanel.obj[gamePanel.currMap][i].solidAreaDefaultX;
                gamePanel.obj[gamePanel.currMap][i].solidArea.y = gamePanel.obj[gamePanel.currMap][i].solidAreaDefaultY;
            }

        }

        return index;
    }

    //Check NPC or monster or tile collision
    public int checkEntity(Entity  e, Entity[][] target){

        int index = 999;

        for(int i = 0; i < target[gamePanel.currMap].length; i++){

            if(target[gamePanel.currMap][i] != null){

                //Get entity's solid area position

                e.solidArea.x = e.worldX + e.solidArea.x;
                e.solidArea.y = e.worldY + e.solidArea.y;

                //Get object's solid area position

                target[gamePanel.currMap][i].solidArea.x = target[gamePanel.currMap][i].worldX + target[gamePanel.currMap][i].solidArea.x;
                target[gamePanel.currMap][i].solidArea.y = target[gamePanel.currMap][i].worldY + target[gamePanel.currMap][i].solidArea.y;

                switch(e.direction) {//solidArea
                    case "up": e.solidArea.y -= e.speed;break;
                    case "down": e.solidArea.y += e.speed;break;
                    case "left": e.solidArea.x -= e.speed;break;
                    case "right": e.solidArea.x += e.speed;break;
                }
                if(e.solidArea.intersects(target[gamePanel.currMap][i].solidArea)){
                    if(target[gamePanel.currMap][i] != e) {
                        e.collisionOn = true;
                        //System.out.println("Collision is on");
                        index = i;
                    }
                }

                //reset the solidAreas
                e.solidArea.x = e.solidAreaDefaultX;
                e.solidArea.y = e.solidAreaDefaultY;
                target[gamePanel.currMap][i].solidArea.x = target[gamePanel.currMap][i].solidAreaDefaultX;
                target[gamePanel.currMap][i].solidArea.y = target[gamePanel.currMap][i].solidAreaDefaultY;
            }
        }
        return index;
    }

    public boolean checkPlayer(Entity e){

        boolean contactPlayer = false;

        //Get entity's solid area position

        e.solidArea.x = e.worldX + e.solidArea.x;
        e.solidArea.y = e.worldY + e.solidArea.y;

        //Get object's solid area position

        gamePanel.player.solidArea.x = gamePanel.player.worldX + gamePanel.player.solidArea.x;
        gamePanel.player.solidArea.y = gamePanel.player.worldY + gamePanel.player.solidArea.y;

        switch(e.direction) {

            case "up": e.solidArea.y -= e.speed; break;
            case "down": e.solidArea.y += e.speed; break;
            case "left": e.solidArea.x -= e.speed; break;
            case "right": e.solidArea.x += e.speed; break;
        }
        if(e.solidArea.intersects(gamePanel.player.solidArea)){
            e.collisionOn = true;
            contactPlayer = true;
        }

        e.solidArea.x = e.solidAreaDefaultX;
        e.solidArea.y = e.solidAreaDefaultY;
        gamePanel.player.solidArea.x = gamePanel.player.solidAreaDefaultX;
        gamePanel.player.solidArea.y = gamePanel.player.solidAreaDefaultY;

        return contactPlayer;
    }
}
