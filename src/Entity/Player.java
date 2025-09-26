package Entity;

import Main.GamePanel;
import Main.KeyHandler;
import Main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import Object.OBJ_Sword_Normal;
import Object.OBJ_Wood_Shield;
import Object.OBJ_Key;
import Object.OBJ_Boots;
import Object.OBJ_Heart;
import Object.OBJ_Chest;
import Object.OBJ_Fireball;
import Object.OBJ_Rock;

public class Player extends Entity{


    KeyHandler keyHandler;

    public final int screenX;
    public final int screenY;
    //public int hasKey = 0;
    int standCounter = 0;
    int keyCooldown = 0;
    public boolean attackCanceled = false;
    public boolean lightUpdated = false;
    public boolean map2Bool = false;

    public Player(GamePanel gp, KeyHandler kh){

        super(gp);
        keyHandler = kh;
        setDefaultValues();
        screenX = gamePanel.screenWidth/2 - (gamePanel.tileSize / 2);
        screenY = gamePanel.screenHeight/2 - (gamePanel.tileSize / 2);

        solidArea = new Rectangle(8, 16, 20, 20);

        //Same thing but we alr created a new instance of rectangle is super classhuh

        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        coin = 500;

    }

    public void setDefaultValues(){
        worldX = gamePanel.tileSize * 23;
        worldY = gamePanel.tileSize * 21;
//        worldX =gamePanel.tileSize * 12;
//        worldY = gamePanel.tileSize * 13;
        defaultSpeed = 4;
        speed = defaultSpeed;
        direction = "right";

        //Player Status
        level = 1;
        maxLife = 8; //8
        life = maxLife;
        maxMana = 4;
        mana = maxMana;
        ammo = 10;
        strength = 1; //The more strength -> more damage given
        dexterity = 1; //more dexterity -> less damage received.
        exp = 0;
        nextLevelExp = 5;
        coin = 150;
        currentWeapon = new OBJ_Sword_Normal(gamePanel);
        currentShield = new OBJ_Wood_Shield(gamePanel);
        projectile = new OBJ_Fireball(gamePanel);
        currLight = null;
        //projectile = new OBJ_Rock(gamePanel);
        attack = getAttack(); //based on strength & weapon
        defense  = getDefense(); //based on Dexterity & shield

        setItems();
        getPlayerImage();
        getPlayerAttackImage();
        getGuardImage();
        setDialogues();
    }

    public void setDialogues(){

        dialogues[0][0] = "Level up! Level " + level + "\nYou feel stronger!";
        dialogues[1][0] = "Level up! Level" + level;

        dialogues[2][0] = "You can only sleep at night";

    }

    public void restoreDefaultValues(){

        worldX = gamePanel.tileSize * 23;
        worldY = gamePanel.tileSize * 21;

        life = maxLife;
        mana = maxMana;
        speed = defaultSpeed;
        ammo = 10;
//        strength = 1; //The more strength -> more damage given, these 2 here are weird
//        dexterity = 1; //more dexterity -> less damage received.
        //set exp to base for current level
        exp = nextLevelExp - (nextLevelExp / 2);
        //nextLevelExp = 5;
        coin = 0;
        //currentWeapon = new OBJ_Sword_Normal(gamePanel);
        //currentShield = new OBJ_Wood_Shield(gamePanel);
        projectile = new OBJ_Fireball(gamePanel);
        //projectile = new OBJ_Rock(gamePanel);
        attack = getAttack(); //based on strength & weapon
        defense  = getDefense(); //based on Dexterity & shield
        transparent = false;
        attacking = false;
        guarding = false;
        knockBack = false;
        lightUpdated = true;
        gamePanel.changeArea();

        //Clear Inventory
        //inventory.clear();

    }

    public void setItems(){

        inventory.add(currentWeapon);
        inventory.add(currentShield);
        Entity key = new OBJ_Key(gamePanel);
        key.name = "Basic Key";//bro why tf did we add this change ahh wtfd
        inventory.add(key);
        inventory.add(new OBJ_Boots(gamePanel));
    }

    public int getCurrWeaponSlot(){
        int currWeaponSlot = 0;
        for(int i = 0; i < inventory.size(); i++){

            if(inventory.get(i) == currentWeapon){currWeaponSlot = i;}
        }
        return currWeaponSlot;
    }
    public int getCurrShieldSlot(){
        int currShieldSlot = 0;

        for(int i = 0; i < inventory.size(); i++){

            if(inventory.get(i) == currentShield){currShieldSlot = i;}
        }
        return currShieldSlot;
    }

    public void getPlayerImage(){
            up1 = setup("/Player/boy_up_1", gamePanel.tileSize, gamePanel.tileSize);
            up2 = setup("/Player/boy_up_2", gamePanel.tileSize, gamePanel.tileSize);
            down1 = setup("/Player/boy_down_1", gamePanel.tileSize, gamePanel.tileSize);
            down2 = setup("/Player/boy_down_2", gamePanel.tileSize, gamePanel.tileSize);
            left1 = setup("/Player/boy_left_1", gamePanel.tileSize, gamePanel.tileSize);
            left2 = setup("/Player/boy_left_2", gamePanel.tileSize, gamePanel.tileSize);
            right1 = setup("/Player/boy_right_1", gamePanel.tileSize, gamePanel.tileSize);
            right2 = setup("/Player/boy_right_2", gamePanel.tileSize, gamePanel.tileSize);


    }

    public void getPlayerAttackImage(){

        if(currentWeapon.type == type_sword) {
            attackUp1 = setup("/Player/boy_attack_up_1", gamePanel.tileSize, gamePanel.tileSize * 2);
            attackUp2 = setup("/Player/boy_attack_up_2", gamePanel.tileSize, gamePanel.tileSize * 2);
            attackDown1 = setup("/Player/boy_attack_down_1", gamePanel.tileSize, gamePanel.tileSize * 2);
            attackDown2 = setup("/Player/boy_attack_down_2", gamePanel.tileSize, gamePanel.tileSize * 2);
            attackRight1 = setup("/Player/boy_attack_right_1", gamePanel.tileSize * 2, gamePanel.tileSize);
            attackRight2 = setup("/Player/boy_attack_right_2", gamePanel.tileSize * 2, gamePanel.tileSize);
            attackLeft1 = setup("/Player/boy_attack_left_1", gamePanel.tileSize * 2, gamePanel.tileSize);
            attackLeft2 = setup("/Player/boy_attack_left_2", gamePanel.tileSize * 2, gamePanel.tileSize);
        }
        if(currentWeapon.type == type_axe){
            attackUp1 = setup("/Player/boy_axe_up_1", gamePanel.tileSize, gamePanel.tileSize * 2);
            attackUp2 = setup("/Player/boy_axe_up_2", gamePanel.tileSize, gamePanel.tileSize * 2);
            attackRight1 = setup("/Player/boy_axe_right_1", gamePanel.tileSize * 2, gamePanel.tileSize);
            attackRight2 = setup("/Player/boy_axe_right_2", gamePanel.tileSize * 2, gamePanel.tileSize);
            attackLeft1 = setup("/Player/boy_axe_left_1", gamePanel.tileSize * 2, gamePanel.tileSize);
            attackLeft2 = setup("/Player/boy_axe_left_2", gamePanel.tileSize * 2, gamePanel.tileSize);
            attackDown1 = setup("/Player/boy_axe_down_1", gamePanel.tileSize, gamePanel.tileSize * 2);
            attackDown2 = setup("/Player/boy_axe_down_2", gamePanel.tileSize, gamePanel.tileSize * 2);
        }
        if(currentWeapon.type == type_pickaxe){
            attackUp1 = setup("/Player/boy_pick_up_1", gamePanel.tileSize, gamePanel.tileSize * 2);
            attackUp2 = setup("/Player/boy_pick_up_2", gamePanel.tileSize, gamePanel.tileSize * 2);
            attackRight1 = setup("/Player/boy_pick_right_1", gamePanel.tileSize * 2, gamePanel.tileSize);
            attackRight2 = setup("/Player/boy_pick_right_2", gamePanel.tileSize * 2, gamePanel.tileSize);
            attackLeft1 = setup("/Player/boy_pick_left_1", gamePanel.tileSize * 2, gamePanel.tileSize);
            attackLeft2 = setup("/Player/boy_pick_left_2", gamePanel.tileSize * 2, gamePanel.tileSize);
            attackDown1 = setup("/Player/boy_pick_down_1", gamePanel.tileSize, gamePanel.tileSize * 2);
            attackDown2 = setup("/Player/boy_pick_down_2", gamePanel.tileSize, gamePanel.tileSize * 2);
        }
    }

    public void getGuardImage(){

        guardUp = setup("/Player/boy_guard_up", gamePanel.tileSize, gamePanel.tileSize);
        guardRight = setup("/Player/boy_guard_right", gamePanel.tileSize, gamePanel.tileSize);
        guardLeft = setup("/Player/boy_guard_left", gamePanel.tileSize, gamePanel.tileSize);
        guardDown = setup("/Player/boy_guard_down", gamePanel.tileSize, gamePanel.tileSize);
    }

    public void update(){

        if(knockBack){

            collisionOn = false;
            gamePanel.collisionChecker.checkTile(this);//check tile collision
            gamePanel.collisionChecker.checkObject(this, true);//Check object Collision
            gamePanel.collisionChecker.checkEntity(this, gamePanel.npc);//Check NPC collision
            gamePanel.collisionChecker.checkEntity(this, gamePanel.monster);//Check monster collision
            gamePanel.collisionChecker.checkEntity(this, gamePanel.iTile);//Check interactive tile collision

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
        else
        if(attacking){attacking();}

        else if(keyHandler.enterPressed){
            guarding = true;
            guardCounter++;
        }

        else
        if(keyHandler.upPressed || keyHandler.downPressed || keyHandler.leftPressed || keyHandler.rightPressed || keyHandler.enterPressed || keyHandler.spacePressed){

            if(keyHandler.upPressed){direction = "up";}
            if(keyHandler.downPressed){direction = "down";}
            if(keyHandler.leftPressed){direction = "left";}
            if(keyHandler.rightPressed){direction = "right";}

            //check tile collision
            collisionOn = false;
            gamePanel.collisionChecker.checkTile(this);

            //Check object Collision
            int objIndex = gamePanel.collisionChecker.checkObject(this, true);
            pickUpObject(objIndex);

            //Check NPC collision
            int npcIndex = gamePanel.collisionChecker.checkEntity(this, gamePanel.npc);
            interactNPC(npcIndex);

            //Check monster collision
            int monsterIndex = gamePanel.collisionChecker.checkEntity(this, gamePanel.monster);
            contactMonster(monsterIndex);

            //Check interactive tile collision
            int iTileIndex = gamePanel.collisionChecker.checkEntity(this, gamePanel.iTile);
            //attackInteractiveTile(iTileIndex); dont put this here since it will by pass attacking

            //Check event
            gamePanel.eH.checkEvent();
            //keyCooldown++;

            //if collision is false, player can move
            if(collisionOn == false && keyHandler.enterPressed == false ){

                switch(direction){
                    case "up": worldY -= speed;break;
                    case "down": worldY += speed;break;
                    case "left": worldX -= speed;break;
                    case "right": worldX += speed;break;
                }
            }
            if(keyHandler.spacePressed && attackCanceled == false){
                //gamePanel.playSE(7);
                attacking = true;
                spriteCounter = 0;
            }

            attackCanceled = false;
            gamePanel.keyHandler.spacePressed = false;
            gamePanel.keyHandler.enterPressed = false;
            guarding = false;
            guardCounter = 0;

            spriteCounter++;
            if(spriteCounter > 10){
            if(spriteNumber == 1){
                    spriteNumber = 2;}
                else if(spriteNumber == 2){
                    spriteNumber = 1;
                }
                spriteCounter = 0;
            }
        } else{
            //Create a buffer b4 the character stops moving
            standCounter++;
            if(standCounter == 20){
                spriteNumber = 1;
                standCounter = 0;
            }
            guarding = false;
            guardCounter = 0;

        }

        //cant keep track of the object. It appears Projectile is never instantiated, however it is a parent class to OBJ_Fireball
        //The confusion lies where Entity (essentially player) does not have access to projectile. Nah it makes sense actually jus gotta scroll up a little dummy
        if(gamePanel.keyHandler.shotKeyPressed && projectile.alive == false && shotAvailableCounter == 30 && projectile.hasResource(this)){

            //Set default coords, dir, & user
            projectile.set(worldX, worldY, direction, true, this);

            //Subtrace useCost
            projectile.subtractResource(this);

            //add projectile to arrayList -> not an arrayList anymore
            //check vacancy
            for(int i = 0; i < gamePanel.projectile[1].length; i++){
                if(gamePanel.projectile[gamePanel.currMap][i] == null){gamePanel.projectile[gamePanel.currMap][i] = projectile;break;}
            }

            //play sound effect
            gamePanel.playSE(10);

            shotAvailableCounter = 0;
        }

        //Outside of key logic
        if(invincible == true){
            invincibleCounter++;
            if(invincibleCounter > 60){
                invincible = false;
                transparent = false;
                invincibleCounter = 0;
            }
        }

        //Cooldown of fireball
        if(shotAvailableCounter < 30){shotAvailableCounter++;}

        //Rest life if player is overhealed
        if(life > maxLife){life = maxLife;}

        //Reset mana in a similar way
        if(mana > maxMana){mana = maxMana;}

        //chekc if plr still has life
        if(life <= 0){gamePanel.gameState = gamePanel.gameOverState;gamePanel.ui.commandNum = -1;}

        if(gamePanel.currMap == 2 && !map2Bool){
            worldX = 12 * gamePanel.tileSize;
            worldY = 13 * gamePanel.tileSize;
            map2Bool = true;
        }
    }

    public void pickUpObject(int index){
        if (index != 999){
            String text = null;
            //Pickup-only items
            if(gamePanel.obj[gamePanel.currMap][index].type == type_pickupOnly){

                gamePanel.obj[gamePanel.currMap][index].use(this);
                gamePanel.obj[gamePanel.currMap][index] = null;
            }

            //Obstacle
            else if(gamePanel.obj[gamePanel.currMap][index].type == type_obstacle){

                if (keyHandler.enterPressed || keyHandler.spacePressed) {
                    attackCanceled = true;
                    gamePanel.obj[gamePanel.currMap][index].interact();
                }
            }

            //Inventory items
             else if(canObtainItem(gamePanel.obj[gamePanel.currMap][index])) {
                gamePanel.playSE(1);
                text = "+1" + gamePanel.obj[gamePanel.currMap][index].name;
                inventory.add(gamePanel.obj[gamePanel.currMap][index]);
            }else{
                text = "Inventory full!";
            }
            gamePanel.ui.addMessage(text);
            if(gamePanel.obj[gamePanel.currMap][index] != null && gamePanel.obj[gamePanel.currMap][index].type != type_obstacle){gamePanel.obj[gamePanel.currMap][index] = null;}
        }
    }

    public void interactNPC(int i){

        if(i != 999){

            if(gamePanel.keyHandler.spacePressed){
                //System.out.println("You are hitting an NPC");
                //gamePanel.npc[i].collisionOn = true;  doesn't work this way, seems simpler tho
                //Player is in contact with NPC
                    attackCanceled = true;
                    //gamePanel.gameState = gamePanel.dialogueState;
                    gamePanel.npc[gamePanel.currMap][i].speak();
            }
            gamePanel.npc[gamePanel.currMap][i].move(direction);
        }
    }

    public void contactMonster(int i){
        if(i != 999 ){
            if(!invincible && gamePanel.monster[gamePanel.currMap][i].dying == false){
            gamePanel.playSE(6);

            int damage = gamePanel.monster[gamePanel.currMap][i].attack - defense;
            if(damage < 0){damage = 0;}
            life -= damage;
            invincible = true;
            transparent = true;
            }
        }
    }

    public void damageMonster(int monsterI, int attack, int knockBackValue, Entity attacker){

        if(monsterI != 999){

            if(gamePanel.monster[gamePanel.currMap][monsterI].invincible == false){

                gamePanel.playSE(5);
                if (knockBackValue > 0) {
                    knockBack(gamePanel.monster[gamePanel.currMap][monsterI], attacker);}

                if(gamePanel.monster[gamePanel.currMap][monsterI].offBalance){
                    attack *= 5;
                }

                int damage = attack - gamePanel.monster[gamePanel.currMap][monsterI].defense;
                if(damage < 0){damage = 0;}

                gamePanel.monster[gamePanel.currMap][monsterI].life -= damage;
                gamePanel.monster[gamePanel.currMap][monsterI].invincible = true;
                gamePanel.monster[gamePanel.currMap][monsterI].damageReaction();

                gamePanel.ui.addMessage(damage + "");

                if(gamePanel.monster[gamePanel.currMap][monsterI].life <= 0){
                    gamePanel.monster[gamePanel.currMap][monsterI].dying = true;
                    gamePanel.ui.addMessage(gamePanel.monster[gamePanel.currMap][monsterI].name + " Killed!");
                    gamePanel.ui.addMessage("+" + gamePanel.monster[gamePanel.currMap][monsterI].exp);
                    exp += gamePanel.monster[gamePanel.currMap][monsterI].exp;
                    checkLevel();
                }
            }
        }
    }

    public void damageProjectile(int index){
        if(index != 999){
            Entity projectile = gamePanel.projectile[gamePanel.currMap][index];
            projectile.alive = false;
            generateParticle(projectile, projectile);
        }
    }

    public void attackInteractiveTile(int i){
        //Issue is not hereSystem.out.println("Made it here");
        //not the issueif(i != 999){System.out.println("Made it here");}                this the issue here. Where r we setting invincible to true? ohh huh where do we set it back to false
        //if(i != 999 && gamePanel.iTile[gamePanel.currMap][i].destructable && gamePanel.iTile[gamePanel.currMap][i].invincible == false){System.out.println("Made it here");}
        if(i != 999 && gamePanel.iTile[gamePanel.currMap][i].destructable && gamePanel.iTile[gamePanel.currMap][i].invincible == false && gamePanel.iTile[gamePanel.currMap][i].isCorrectItem(this)){
                //System.out.println("Made it here"); Make it here once for out issue but that's it
                //Can cut tree down atp
                gamePanel.playSE(11);
                gamePanel.iTile[gamePanel.currMap][i].life --;
                gamePanel.iTile[gamePanel.currMap][i].invincible = true;

                System.out.println(gamePanel.iTile[gamePanel.currMap][i].life + " " + gamePanel.iTile[gamePanel.currMap][i].name);

                //Gen particle
                generateParticle(gamePanel.iTile[gamePanel.currMap][i], gamePanel.iTile[gamePanel.currMap][i]);

                if(gamePanel.iTile[gamePanel.currMap][i].life == 0){
                    gamePanel.iTile[gamePanel.currMap][i].checkDrop();
                    gamePanel.iTile[gamePanel.currMap][i] = gamePanel.iTile[gamePanel.currMap][i].getDestroyedForm();
                }

        }
        if(i != 999 && gamePanel.iTile[gamePanel.currMap][i] != null && gamePanel.iTile[gamePanel.currMap][i].name == "Tent"){
            //can tel plr
            System.out.println("Teleporting...");//28 26
            if(gamePanel.envManager.lighting.dayState == gamePanel.envManager.lighting.night) {gamePanel.eH.mapTel(2, 12, 13, gamePanel.inside);}

            else{
//                gamePanel.ui.currentDialogue = "You can only sleep at night";
//                gamePanel.ui.drawDialogueScreen();
                  gamePanel.gameState = gamePanel.dialogueState;
                  startDialogue(this, 2);

                  //cancel the current attack
                 attacking = false;
                 spriteCounter = 0;
            }
        }
    }

    public void selectItem() {
        int itemIndex = gamePanel.ui.getItemIndex(gamePanel.ui.playerSlotCol, gamePanel.ui.playerSlotRow);

        if (itemIndex < inventory.size()) {
            Entity selectedItem = inventory.get(itemIndex);

            switch (selectedItem.type) {
                case type_sword, type_axe, type_pickaxe:
                    currentWeapon = selectedItem;
                    attack = getAttack();
                    getPlayerAttackImage();
                    break;
                case type_shield:
                    currentShield = selectedItem;
                    defense = getDefense();
                    break;
                case type_consumable:
                    if (selectedItem.use(this)) {
                        if (selectedItem.amount > 1) {
                            selectedItem.amount -= 1;
                        } else {
                            inventory.remove(itemIndex);
                        }
                    }
                    break;
                case type_light:
                    if(currLight == selectedItem){currLight = null;}
                    else{currLight = selectedItem;}
                    lightUpdated = true;
                    break;
            }
        }
    }

    public int searchInventoryItem(String itemName){

        int itemIndex = 999;

        for(int i = 0; i < inventory.size(); i++){
            if(inventory.get(i).name.equals(itemName)){
                itemIndex = i;
                break;
            }
        }

        return itemIndex;
    }

    public boolean canObtainItem(Entity item){

        boolean canObtain = false;

        Entity newItem = item.createNew(gamePanel);

        //Check if item is stackable
        if(newItem.stackable){

            int index = searchInventoryItem(newItem.name);

            if(index != 999){
                inventory.get(index).amount++;
                canObtain = true;
            }else{//new item so check vacancy
                if(inventory.size() != maxInventorySize){
                    //get rid of this for now
                    //inventory.add(newItem);
                    canObtain = true;
                }
            }
        }
        else{//Not stackable
            if(inventory.size() != maxInventorySize){
                //and here too
                //inventory.add(newItem);
                canObtain = true;
            }
        }
        return canObtain;
    }

    public void draw(Graphics2D g2){

        //g2.setColor(Color.white);
        //g2.fillRect(x, y, gamePanel.tileSize, gamePanel.tileSize);

        BufferedImage image = null;
        int tempScreenX = screenX;
        int tempScreenY = screenY;

        switch(direction){
            case "up":
                if(attacking == false){
                    if(spriteNumber == 1){image = up1;}
                    if(spriteNumber == 2){image = up2;}}
                if(attacking){
                    tempScreenY -= gamePanel.tileSize;
                    if(spriteNumber == 1){image = attackUp1;}
                    if(spriteNumber == 2){image = attackUp2;}}
                if(guarding){image = guardUp;}
                break;
            case "down":
                if(attacking == false){
                    if(spriteNumber == 1){image = down1;}
                    if(spriteNumber == 2){image = down2;}}
                if(attacking){
                    if(spriteNumber == 1){image = attackDown1;}
                    if(spriteNumber == 2){image = attackDown2;}}
                    if(guarding){image = guardDown;}
                break;
            case "left":
                if(attacking == false){
                    if(spriteNumber == 1){image = left1;}
                    if(spriteNumber == 2){image = left2;}}
                if(attacking){
                    tempScreenX -= gamePanel.tileSize;
                    if(spriteNumber == 1){image = attackLeft1;}
                    if(spriteNumber == 2){image = attackLeft2;}}
                    if(guarding){image = guardLeft;}
                break;
            case "right":
                if(attacking == false){
                    if(spriteNumber == 1){image = right1;}
                    if(spriteNumber == 2) {image = right2;}}
                if(attacking){
                    if(spriteNumber == 1){image = attackRight1;}
                    if(spriteNumber == 2){image = attackRight2;}}
                if(guarding){image = guardRight;}
                break;
        }
        if(transparent){
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.6f));}

        g2.drawImage(image, tempScreenX, tempScreenY,null);
        //down1 != null, image == null

        //Reset Opacity
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

        //g2.drawRect(screenX + solidArea.x, screenY + solidArea.y, solidArea.width, solidArea.height);

        //Debug
        /*g2.setFont(new Font("Arial", Font.PLAIN, 26));
        g2.setColor(Color.white);
        g2.drawString("Invincible: " + invincibleCounter, 10, 400);*/

    }


}
