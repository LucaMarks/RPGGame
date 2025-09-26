package Entity;

import AI.PathFinder;
import Main.GamePanel;
import Main.Main;
import Main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Random;

public class Entity {

    public GamePanel gamePanel;

    public BufferedImage image, image2, image3;
    public BufferedImage attackUp1, attackUp2, attackDown1, attackDown2, attackRight1, attackRight2, attackLeft1, attackLeft2,
    guardUp, guardDown, guardLeft, guardRight;
    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;

    public int useCost;
    public String name;
    public boolean Collision = false;
    public boolean transparent = false;
    public int worldX, worldY;
    public int speed;
    public boolean alive = true;
    public boolean dying = false;
    int dyingCounter = 0;
    public int maxMana;
    public int mana;
    public int ammo;
    public int price;
    public int knockBackValue;

    boolean hpBarOn = false;
    int hpBarCounter = 0;
    public boolean onPath = false;

    public String direction = "down";

    public int spriteCounter = 0;
    public int spriteNumber = 1;
    public Rectangle solidArea = new Rectangle(0, 0, 48, 48);
    public int solidAreaDefaultX, solidAreaDefaultY;
    public Rectangle attackArea = new Rectangle(0, 0, 0, 0);
    public Projectile projectile;
    public int shotAvailableCounter = 0;
    public boolean knockBack = false;
    public int knockBackCounter = 0;
    public boolean guarding = false;
    public int guardCounter = 0;
    int offBalanceCounter = 0;
    public boolean offBalance = false;
    public boolean openOnRockPuzzle = false;

    public ArrayList<Entity> inventory = new ArrayList<>();
    public final int maxInventorySize = 20;
    //public ArrayList<Entity> allObjects = new ArrayList<>(); sooo we're in copper

    public boolean collisionOn = false;
    public int actionLockCounter = 0;

    public boolean invincible = false;
    public int invincibleCounter = 0;
    public boolean attacking = false;
    public Entity attacker;
    public String knockBackDirection;
    public Entity loot;
    public boolean opened = false;
    public Entity linkedEntity;


    public String dialogues[][] = new String[20][20];
    public int dialogueIndex = 0;
    public int dialogueSet = 0;

    //Type
    public int type; // 0 = player, 1 = npc, 2 = monster ->
    public final int type_player = 0;
    public final int type_npc = 1;
    public final int type_monster = 2;
    public final int type_sword = 3;
    public final int type_axe = 4;
    public final int type_shield = 5;
    public final int type_consumable = 6;
    public final int type_pickupOnly = 7;
    public final int type_interactive_tile = 8;
    public final int type_obstacle = 9;
    public final int type_light = 10;
    public final int type_pickaxe = 11;

    //Character Status
    public int defaultSpeed;
    public int maxLife;
    public int life;
    public int level;
    public int strength;
    public int dexterity;
    public int attack;
    public int defense;
    public int exp;
    public int nextLevelExp;
    public int coin;
    public Entity currentWeapon;
    public Entity currentShield;
    public Entity helmet;
    public int value; //Abstract variable for multipurpose use
    public Entity currLight;

    //Item attributes
    public int attackValue;
    public int defenseValue;
    public String description = "";
    public boolean stackable = false;
    public int amount = 1;
    public int lightRadius;
    public int motion1_duration;
    public int motion2_duration;

    public Entity(GamePanel gp){
        gamePanel = gp;
        name = "Entity";
    }

    public int getLeftX(){return worldX + solidArea.x;}
    public int getRightX(){return worldX + solidArea.x + solidArea.width;}
    public int getTopY(){return worldY + solidArea.y;}
    public int getBottomY(){return worldY + solidArea.y + solidArea.height;}

    public int getCol(){return (worldX + solidArea.x) / gamePanel.tileSize;}
    public int getRow(){return (worldY + solidArea.y) / gamePanel.tileSize;}

    public int getCenterX(){
        int centerX = worldX + up1.getWidth() / 2;
        return centerX;
    }
    public int getCenterY(){
        int centerY = worldY + up1.getHeight() / 2;
        return centerY;
    }

    //not gonna use these for pathfinding ngl
    public int getXDistance(Entity target){return Math.abs(getCenterX() - target.getCenterX());}
    public int getYDistance(Entity target){return Math.abs(getCenterY() - target.getCenterY());}
    public int getTileDistance(Entity target){return (getXDistance(target) + getYDistance(target)) / gamePanel.tileSize;}
    public int getGoalCol(Entity target){return (target.worldX + target.solidArea.x) / gamePanel.tileSize;}
    public int getGoalRow(Entity target){return (target.worldY + target.solidArea.y) / gamePanel.tileSize;}
    public void getRandomDir(int counter){
        //actionLockCounter
        if(actionLockCounter >= counter){
            Random random = new Random();
            int i = random.nextInt(100) + 1; //Pick random number from 1-100

            if (i <= 25) {direction = "up";}

            if (i > 25 && i <= 50) {direction = "down";}

            if (i > 50 && i <= 75) {direction = "left";}

            if (i > 75 && i <= 100) {direction = "right";}

            actionLockCounter = 0;
        }

    }
    public void resetCounters(){
        spriteCounter = 0;
        actionLockCounter = 0;
        invincibleCounter = 0;
        shotAvailableCounter = 0;
        dyingCounter = 0;
        hpBarCounter = 0;
        knockBackCounter = 0;
        guardCounter = 0;
        offBalanceCounter = 0;
    }
    public void setLoot(Entity loot){}
    public void setAction(){}

    public void damageReaction(){}

    public int getAttack(){

        if(type == type_player){
            motion1_duration = currentWeapon.motion1_duration;
            motion2_duration = currentWeapon.motion2_duration;
        }

        attackArea = currentWeapon.attackArea;
        return strength * currentWeapon.attackValue;
    }
    public int getDefense(){return dexterity * currentShield.defenseValue;}

    public Entity createNew(GamePanel gp){return null;}
    public void speak(){}

    public void facePlayer(){
        switch(gamePanel.player.direction){
            case "up": direction = "down";break;
            case "down": direction = "up";break;
            case "right": direction = "left";break;
            case "left": direction = "right";break;
        }

    }

    public void startDialogue(Entity e, int setNum){

        gamePanel.gameState = gamePanel.dialogueState;
        gamePanel.ui.npc = e;
        dialogueSet = setNum;
    }

    public void interact(){}

    public void checkDrop(){}

    public void dropItem(Entity droppedItem){

        for(int i = 0; i < gamePanel.obj[1].length; i++){
            if(gamePanel.obj[gamePanel.currMap][i] == null){
                gamePanel.obj[gamePanel.currMap][i] = droppedItem;
                gamePanel.obj[gamePanel.currMap][i].worldX = worldX; //enity worldX
                gamePanel.obj[gamePanel.currMap][i].worldY = worldY; //entity worldY
                break;
            }
        }
    }

    public void checkCollision(){

        collisionOn = false;
        gamePanel.collisionChecker.checkTile(this);
        gamePanel.collisionChecker.checkObject(this, false);
        gamePanel.collisionChecker.checkEntity(this, gamePanel.npc);
        gamePanel.collisionChecker.checkEntity(this, gamePanel.monster);
        gamePanel.collisionChecker.checkEntity(this, gamePanel.iTile);
        boolean contactPlayer = gamePanel.collisionChecker.checkPlayer(this);

        if(this.type == type_monster && contactPlayer){damagePlayer(attack);}

    }

    public void checkAttackOrNot(int rate, int straight, int horizontal){

        boolean targetInRange = false;
        int xDis = getXDistance(gamePanel.player);
        int yDis = getYDistance(gamePanel.player);

        switch(direction){
            case "up":if(gamePanel.player.getCenterY() < getCenterY() && yDis < straight && xDis < horizontal){
                targetInRange = true;}
                break;
            case "left":if(gamePanel.player.getCenterX() < getCenterX() && yDis < straight && yDis < horizontal){
                targetInRange = true;}
                break;
            case "down":if(gamePanel.player.getCenterY() > getCenterY() && yDis < straight && xDis < horizontal){
                targetInRange = true;}
                break;
            case "right":if(gamePanel.player.getCenterX() > getCenterY() && yDis < straight && yDis < horizontal){
                targetInRange = true;}
                break;
        }

        if(targetInRange){
            //check if it initeates an attack
            int i = new Random().nextInt(rate);

            if(i == 0){
                attacking = true;
                spriteCounter = 0;
                spriteNumber = 1;
                shotAvailableCounter = 0;
            }

        }
    }

    public void update(){

        if(knockBack){

            checkCollision();
            if(collisionOn){
                knockBackCounter = 0;
                knockBack = false;
                speed = defaultSpeed;
            }else
            if(collisionOn == false){
                switch(knockBackDirection){
                    case "up": worldY -= speed;break;
                    case "down": worldY += speed;break;
                    case "left": worldX -= speed;break;
                    case "right": worldX += speed;break;
                }
            }
            knockBackCounter++;
            if(knockBackCounter == 10){
                knockBackCounter = 0;
                knockBack = false;
                speed = defaultSpeed;
            }
            }
        else if(attacking){attacking();}
        else {

            setAction();
            checkCollision();

            //if collision is false, Entity can move
            if (collisionOn == false) {

                switch (direction) {
                    case "up": worldY -= speed;break;
                    case "down": worldY += speed;break;
                    case "left": worldX -= speed;break;
                    case "right": worldX += speed;break;
                }
            }

            spriteCounter++;
            if(spriteCounter > 24){
                if(spriteNumber == 1){spriteNumber = 2;}

                else if(spriteNumber == 2){spriteNumber = 1;}
                spriteCounter = 0;
            }

        }

        //
        if(invincible == true){
            invincibleCounter++;
            if(invincibleCounter > 40){
                invincible = false;
                invincibleCounter = 0;
            }
        }

        if(shotAvailableCounter < 30){shotAvailableCounter++;}

        if(offBalance){
            offBalanceCounter++;
            if(offBalanceCounter > 50){
                offBalance = false;
                offBalanceCounter = 0;
            }
        }
//what the actual fuck
//        if(direction != null && name == BigRock.npcName){
//            switch(direction){
//                case "up": worldY += speed;break;
//                case "down": worldY -= speed;break;
//                case "left": worldX += speed;break;
//                case "right": worldX -= speed;break;
//            }
//        }

    }

    //dunno what to say. There r easier ways but ts works good enough
    public void barProximityChecker(Player p){
        if(type == 2){

            if( (Math.sqrt(Math.pow(p.worldX - worldX, 2) + Math.pow(p.worldY - worldY, 2)) < gamePanel.tileSize * 4) && hpBarCounter < 275){hpBarOn = true;}
            else{
                hpBarOn = false;
                if(Math.sqrt(Math.pow(p.worldX - worldX, 2) + Math.pow(p.worldY - worldY, 2)) > gamePanel.tileSize * 4){hpBarCounter = 0;}
            }

        }

    }

    public void checkLevel(){
        if(exp >= nextLevelExp){
            level += 1;
            nextLevelExp = nextLevelExp * 2;
            maxLife += 2;
            life += 2;
            dexterity++;
            strength++;
            attack = getAttack();
            defense = getDefense();

            gamePanel.playSE(8);


            if(level == 2){
                gamePanel.gameState = gamePanel.dialogueState;
                startDialogue(this, 0);

            } else{startDialogue(this, 1);}

        }
    }

    public String getOppositeDirection(String currDir){

        String oppDir = "";
        switch(currDir){
            case "up":oppDir = "down";break;
            case "down":oppDir = "up";break;
            case "left":oppDir = "right";break;
            case "right":oppDir = "left";break;
        }
        return oppDir;
    }

    public void dirPlayer(int interval){

        actionLockCounter++;

        if(actionLockCounter > interval){

            if(getXDistance(gamePanel.player) > getYDistance(gamePanel.player)){
                //move left or right
                if(gamePanel.player.getCenterX() < getCenterX()){
                    direction = "left";
                }else{
                    direction = "right";
                }
            }else if(getXDistance(gamePanel.player) <  getYDistance(gamePanel.player)){
                //move up or down
                if(gamePanel.player.getCenterY() < getCenterX()){
                    direction = "up";
                }else{
                    direction = "down";
                }
            }
            actionLockCounter = 0;
        }
    }

    public void attacking(){

        spriteCounter++;

        if(spriteCounter <= motion1_duration){
            spriteNumber = 1;
        }
        if(spriteCounter > motion1_duration && spriteCounter <= motion2_duration){
            spriteNumber = 2;

            //save current worldX, worldY, and solidArea
            int currentWorldX = worldX;
            int currentWorldY = worldY;
            int solidAreaWidth = solidArea.width;
            int solidAreaHeight = solidArea.height;

            //Adjust player's worldX/Y for the attackArea
            switch(direction){
                case "up": worldY -= attackArea.height; break;
                case "down": worldY += attackArea.height; break;
                case "left": worldX -= attackArea.width; break;
                case "right": worldX += attackArea.width; break;
            }

            //Attack area becomes solid area
            solidArea.width = attackArea.width;
            solidArea.height = attackArea.height;

            if(type == type_monster){

                if(gamePanel.collisionChecker.checkPlayer(this)){damagePlayer(attack);}
            }
            else {//type is plr

                //Check monster collision with updated worldX, worldY, and solidArea
                int monsterIndex = gamePanel.collisionChecker.checkEntity(this, gamePanel.monster);
                gamePanel.player.damageMonster(monsterIndex, attack, currentWeapon.knockBackValue, this);

                int iTileIndex = gamePanel.collisionChecker.checkEntity(this, gamePanel.iTile);
                //System.out.println("Made it here");The issue is not here
                gamePanel.player.attackInteractiveTile(iTileIndex);

                int projectileIndex = gamePanel.collisionChecker.checkEntity(this, gamePanel.projectile);
                gamePanel.player.damageProjectile(projectileIndex);
            }

            //Restore original data after checking collisions
            worldX = currentWorldX;
            worldY = currentWorldY;
            solidArea.width = solidAreaWidth;
            solidArea.height = solidAreaHeight;

        }
        if(spriteCounter > motion2_duration){
            spriteNumber = 1 ;
            spriteCounter = 0;
            attacking = false;
        }
    }

    public void damagePlayer(int attack){

        if(!gamePanel.player.invincible){
            //give damage to player as monster contacted player

            int damage = attack - gamePanel.player.defense;

            //get opposite direction of attacker
            String canGuardDir = getOppositeDirection(direction);

            //check if plr is facing attacker
            if(gamePanel.player.guarding && gamePanel.player.direction.equals(canGuardDir)){

                //Parry
                if(gamePanel.player.guardCounter < 10){
                    damage = 0;
                    gamePanel.playSE(14);
                    knockBack(this, gamePanel.player);
                    offBalance = true;
                    spriteCounter -= 60;
                }
                //normal guard
                else{
                    damage /= 3;
                    gamePanel.playSE(13);
                }
            }else{gamePanel.playSE(6);}//not guarding

            if(damage < 0){damage = 0;}

            knockBack(gamePanel.player, this);
            gamePanel.player.life -= damage;
            gamePanel.player.invincible = true;

            if(damage > 0){
                transparent = true;
                knockBack(gamePanel.player, this);
            }
        }
    }

    public void knockBack(Entity e, Entity attacker){
    //e is target

        if(currentWeapon != null && currentWeapon.knockBackValue > 0) {
//            e.direction = direction;
            this.attacker = attacker;
            e.knockBackDirection = attacker.direction;
            e.speed += currentWeapon.knockBackValue;
            e.knockBack = true;
        }
        else{
            this.attacker = attacker;
            e.knockBackDirection = attacker.direction;
            e.speed += attacker.knockBackValue;
            e.knockBack = true;
        }
    }

    public void generateParticle(Entity generator, Entity target){

        Color color = generator.getParticleColor();
        int size = generator.getParticleSize();
        int speed = generator.getParticleSpeed();
        int maxLife = generator.getParticleMaxLife();

        Particle p1 = new Particle(gamePanel, target, color, size, speed, maxLife, -2, -1);
        Particle p2 = new Particle(gamePanel, target, color, size, speed, maxLife, 2, -1);
        Particle p3 = new Particle(gamePanel, target, color, size, speed, maxLife, -2, 1);
        Particle p4 = new Particle(gamePanel, target, color, size, speed, maxLife, 2, 1);
        gamePanel.particleList.add(p1);
        gamePanel.particleList.add(p2);
        gamePanel.particleList.add(p3);
        gamePanel.particleList.add(p4);
    }

    public void draw(Graphics2D g2){

        BufferedImage image = null;

        int screenX = worldX - gamePanel.player.worldX + gamePanel.player.screenX;
        int screenY = worldY - gamePanel.player.worldY + gamePanel.player.screenY;

        if(        worldX + gamePanel.tileSize  * 5> gamePanel.player.worldX - gamePanel.player.screenX
                && worldX - gamePanel.tileSize < gamePanel.player.worldX + gamePanel.player.screenX
                && worldY + gamePanel.tileSize * 5> gamePanel.player.worldY - gamePanel.player.screenY
                && worldY - gamePanel.tileSize < gamePanel.player.worldY + gamePanel.player.screenY) {

            //System.out.println("Old man is in frame currently");

            int tempScreenX = screenX;
            int tempScreenY = screenY;

            switch(direction){
                case "up":
                    if(attacking == false){
                        if(spriteNumber == 1){image = up1;}
                        if(spriteNumber == 2){image = up2;}}
                    if(attacking){
                        tempScreenY -= up1.getHeight();
                        if(spriteNumber == 1){image = attackUp1;}
                        if(spriteNumber == 2){image = attackUp2;}}
                    break;
                case "down":
                    if(attacking == false){
                        if(spriteNumber == 1){image = down1;}
                        if(spriteNumber == 2){image = down2;}}
                    if(attacking){
                        if(spriteNumber == 1){image = attackDown1;}
                        if(spriteNumber == 2){image = attackDown2;}}
                    break;
                case "left":
                    if(attacking == false){
                        if(spriteNumber == 1){image = left1;}
                        if(spriteNumber == 2){image = left2;}}
                    if(attacking){
                        tempScreenX -= left1.getWidth();
                        if(spriteNumber == 1){image = attackLeft1;}
                        if(spriteNumber == 2){image = attackLeft2;}}
                    break;
                case "right":
                    if(attacking == false){
                        if(spriteNumber == 1){image = right1;}
                        if(spriteNumber == 2) {image = right2;}}
                    if(attacking){
                        if(spriteNumber == 1){image = attackRight1;}
                        if(spriteNumber == 2){image = attackRight2;}}
                    break;
            }
            //Monster HP bar
            if(type == 2 && hpBarOn) {
                double oneScale = (double) gamePanel.tileSize / maxLife;
                double hpBarValue = oneScale * life;

                g2.setColor(new Color(30, 40, 40));
                g2.fillRect(screenX - 1, screenY - 16, gamePanel.tileSize + 2, 12);
                g2.setColor(new Color(253, 5, 30));
                g2.fillRect(screenX, screenY - 15, (int) hpBarValue, 10);

                hpBarCounter++;
                if(hpBarCounter > 275){
                    hpBarOn = false;
                }
            }

            if(invincible){
                if(type != type_interactive_tile){
                    g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.7f)); hpBarOn = true; hpBarCounter = 0;}
            }

            if(dying){
                dyingAnimation(g2);
            }

            //System.out.println("Drawing image");
            //if(image == null){System.out.println("image is null");}
            g2.drawImage(image, tempScreenX, tempScreenY, null);

            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        }

    }

    public void dyingAnimation(Graphics2D g2){

        dyingCounter++;

        switch(dyingCounter){
            case 1, 2, 3, 4, 5, 11, 12, 13, 14, 15, 21, 22, 23, 24, 25, 31, 32, 33, 34, 35:
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0f)); break;
            case 6, 7, 8, 9, 10, 16, 17, 18, 19, 20, 26, 27, 28, 29, 30, 36, 37, 38, 39, 40:
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f)); break;
        }

        if(dyingCounter > 40){
            alive = false;
        }


    }

    public BufferedImage setup(String name, int width, int height){
        UtilityTool uTool = new UtilityTool();
        BufferedImage image = null;

        String path = name + ".png";
        System.out.println("loading " + path);

        try{

            image = ImageIO.read(getClass().getResourceAsStream(name + ".png"));
            image = uTool.scaleImage(image, width, height);


        }catch(IOException e){
            e.printStackTrace();}
        return image;
    }

    public boolean use(Entity e){return false;}

    //this meth od not useful no more but mistake made here is so craxzy
    public BufferedImage scaleImage(String name){
        UtilityTool uTool = new UtilityTool();
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/objects/" + name + ".png"));
            image = uTool.scaleImage(image, gamePanel.tileSize, gamePanel.tileSize);
            System.out.println("Loading: /objects/" + name + ".png");

        }catch(IOException e) {
            e.printStackTrace();}
        return image;
    }


    public void move(String direction){}

    public Color getParticleColor(){return null;}
    public int getParticleSize(){return 0;}
    public int getParticleSpeed(){return 0;}
    public int getParticleMaxLife(){return 0;}

    public void searchPath(int goalCol, int goalRow){

        int startCol = (worldX + solidArea.x) / gamePanel.tileSize;
        int startRow = (worldY + solidArea.y) / gamePanel.tileSize;

        gamePanel.pathFinder.setNodes(startCol, startRow, goalCol, goalRow);

        //We have found a path
        if(gamePanel.pathFinder.search()){

            int nextX = gamePanel.pathFinder.pathList.get(0).col * gamePanel.tileSize;
            int nextY = gamePanel.pathFinder.pathList.get(0).row * gamePanel.tileSize;
            //Entity's solidArea position
            int enLeftX = worldX + solidArea.x;
            int enRightX = worldX + solidArea.x + solidArea.width;
            int enTopY = worldY + solidArea.y;
            int enBottomY = worldY + solidArea.y + solidArea.height;

            if(enTopY > nextY && enLeftX >= nextX && enRightX < nextX + gamePanel.tileSize){direction = "up";}
            else
            if(enTopY < nextY && enLeftX >= nextX && enRightX < nextX + gamePanel.tileSize){direction = "down";}
            else
            if(enTopY >= nextY && enBottomY < nextY + gamePanel.tileSize){
                //either left or right
                if(enLeftX > nextX){direction = "left";}
                if(enLeftX < nextX){direction = "right";}
            }
            else
            if(enTopY > nextY && enLeftX > nextX){
                //Either up or left
                direction = "up";
                checkCollision();
                if(collisionOn){direction = "left";}
            }
            else
            if(enTopY > nextY && enLeftX < nextX){
                // Either up or right
                direction = "up";
                checkCollision();
                if(collisionOn){direction = "right";}
            }
            else
            if(enTopY < nextY && enLeftX > nextX){
                //Either down or left
                direction = "down";
                checkCollision();
                if(collisionOn){direction = "left";}
            }
            else
            if(enTopY < nextY && enLeftX < nextX){
                //Down r right
                direction = "down";
                checkCollision();
                if(collisionOn){direction = "right";}
            }

            int nextCol = gamePanel.pathFinder.pathList.get(0).col;
            int nextRow = gamePanel.pathFinder.pathList.get(0).row;

            if(nextCol == goalCol && nextRow == goalRow){
                onPath = false;
            }
        }
    }

    public int getDetected(Entity user, Entity target[][], String targetName){
        int index = 999;

        //check surrounding object
        int nextWorldX = user.getLeftX();
        int nextWorldY = user.getTopY();

        switch(user.direction){
            case "up": nextWorldY -= gamePanel.player.speed;break;//user.getTopY() - 1
            case "down": nextWorldY = user.getBottomY() + gamePanel.player.speed;break;
            case "left": nextWorldX -= gamePanel.player.speed;break;//user.getLeftX - 1;
            case "right": nextWorldX = user.getRightX() + gamePanel.player.speed;break;
        }

        int col = nextWorldX / gamePanel.tileSize;
        int row = nextWorldY / gamePanel.tileSize;

        for(int i = 0; i < target[1].length; i++){
            if(target[gamePanel.currMap][i] != null){
                Entity curTar[] = target[gamePanel.currMap];
                if(curTar[i].getCol() == col && curTar[i].getRow() == row && curTar[i].name.equals(targetName)){
                    index = i;
                    break;
                }
            }
        }
        return index;
    }
}
