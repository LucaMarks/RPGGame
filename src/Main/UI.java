package Main;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import Entity.Entity;
import Object.OBJ_Key;
import Object.SuperObject;
import Object.OBJ_Heart;
import Object.OBJ_ManaCrystal;
import Object.OBJ_Coin_Bronze;

public class UI {

    GamePanel gp;
    Graphics2D g2;
    Font arial_40, arial_80B, arial_80;
    public boolean messageOn = false;
    /*public String message = "";
    int messageCounter = 0;*/
    ArrayList<String> message = new ArrayList<>();
    ArrayList<Integer> messageCounter = new ArrayList<>();
    BufferedImage heart_full, heart_half, heart_blank, crystal_full, crystal_blank, coin;
    public boolean gameFinished = false;
    double playTime = 0;
    double playTimeR;
    public String currentDialogue;
    public int commandNum = 0;
    public int titleScreenState = 0; //0: First screen, 1:
    public int playerSlotCol = 0;
    public int playerSlotRow = 0;
    public int npcSlotCol = 0;
    public int npcSlotRow = 0;

    public boolean tradeBuyCooldown = false;
    public int tradeBuyCounter = 0;
    public boolean tradeSellCooldown = false;
    public int tradeSellCounter = 0;

    public int subState = 0;
    int counter = 0;
    public Entity npc;
    int charIndex = 0;
    String combinedText = "";


    public UI(GamePanel gp){
        this.gp = gp;
        arial_40 = new Font("Arial", Font.PLAIN, 40);
        arial_80B = new Font("Arial", Font.BOLD, 80);
        arial_80 = new Font("Arial", Font.PLAIN, 80);

        if(gp.allObjects == null){
            gp.allObjects = new ArrayList<>();
        }

        //Create HUD Objects
        Entity heart = new OBJ_Heart(gp);
        heart_blank = heart.image;
        heart_full = heart.image2;
        heart_half = heart.image3;

        Entity crystal = new OBJ_ManaCrystal(gp);
        crystal_full = crystal.image;
        crystal_blank = crystal.image2;

        Entity bronzeCoin = new OBJ_Coin_Bronze(gp);
        coin =  bronzeCoin.down1;
    }

    public void addMessage(String text){

//        message = text;
//        messageOn = true;

          message.add(text);
          messageCounter.add(0);
    }

    public void draw(Graphics2D g2) {
        this.g2 = g2;

        g2.setFont(arial_40);
        g2.setColor(Color.white);

        //Title State
        if (gp.gameState == gp.titleState) {drawTitle();}

        //play state
        if (gp.gameState == gp.playState) {
            drawLives();
            drawMessage();
        }

        //pause state
        if (gp.gameState == gp.pauseState) {
            drawLives();
            drawPauseScreen();
        }
        //dialogueState
        if (gp.gameState == gp.dialogueState) {
            drawLives();
            drawDialogueScreen();
        }

        //Character state
        if (gp.gameState == gp.characterState) {
            drawCharacterState();
            drawInventory(gp.player, true);
        }

        //Options State
        if (gp.gameState == gp.optionsState) {
            drawOptionsScreen();
        }

        //Game over state
        if (gp.gameState == gp.gameOverState) {
            drawGameOver();
        }

        //Transition State
        if (gp.gameState == gp.transitionState) {
            drawTransition();
        }

        //Trade state
        if (gp.gameState == gp.tradeState) {drawTradeScreen();}

    }

    public void drawLives(){

        int x = gp.tileSize / 2;
        int y = gp.tileSize / 2;

        int i = 0;

        //Draw blank hearts
        while(i < gp.player.maxLife / 2){
            g2.drawImage(heart_blank, x, y, null);
            i++;
            x += gp.tileSize;
        }

        x = gp.tileSize / 2;
        y = gp.tileSize / 2;
        i = 0;

        //Draw current life
        while(i < gp.player.life){
            g2.drawImage(heart_half, x, y, null);
            i++;

            if(i < gp.player.life){
                g2.drawImage(heart_full, x, y, null);}
            i++;
            x += gp.tileSize;
        }

        //Draw blank mana
        x = (gp.tileSize / 2) - 5;
        y = (int) (gp.tileSize * 1.5);
        i = 0;
        while(i < gp.player.maxMana){
            g2.drawImage(crystal_blank, x, y, null);
            i++;
            x += 35;}

        //Draw full crystals
        x = (gp.tileSize / 2) - 5;
        i = 0;
        while(i < gp.player.mana){
            g2.drawImage(crystal_full, x, y, null);
            i++;
            x += 35;}

    }

    public void drawMessage(){

        int messageX = gp.tileSize;
        int messageY = gp.tileSize * 4;

        g2.setFont(new Font("Arial", 2, 25));
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 32F));

        for(int i = 0; i < message.size(); i++){
            if(message.get(i) != null){

                g2.setColor(Color.BLACK);
                g2.drawString(message.get(i), messageX + 2, messageY + 2);
                g2.setColor(Color.WHITE);
                g2.drawString(message.get(i), messageX, messageY);

                int counter = messageCounter.get(i) + 1; // messageCounter++
                messageCounter.set(i, counter);//set the counter to the array
                messageY += 50;

                if(messageCounter.get(i) > 180){
                    message.remove(i);
                    messageCounter.remove(i);
                }
            }
        }
    }

    public void drawDialogueScreen(){

        //Window
        int x = gp.tileSize * 3;
        int y = gp.tileSize / 2;
        int width = gp.screenWidth - (gp.tileSize * 6);
        int height = gp.tileSize * 3;
        drawSubWindow(x, y, width, height);

        x += gp.tileSize;
        y += gp.tileSize;

        if(npc.dialogues[npc.dialogueSet][npc.dialogueIndex] != null){

//            currentDialogue = npc.dialogues[npc.dialogueSet][npc.dialogueIndex];  display text all at once.
            char characters[] = npc.dialogues[npc.dialogueSet][npc.dialogueIndex].toCharArray();

            if(charIndex < characters.length){
                String s = String.valueOf(characters[charIndex]);
                combinedText = combinedText + s;
                currentDialogue = combinedText;

                charIndex++;
            }

            if ((gp.keyHandler.enterPressed || gp.keyHandler.spacePressed)) {
                if (gp.gameState == gp.dialogueState) {
                    npc.dialogueIndex++;
                }
                gp.keyHandler.enterPressed = false;
                gp.keyHandler.spacePressed = false;
                charIndex = 0;
                combinedText = "";
            }

            //is there a better way to write this? yes look up
//            if(gp.keyHandler.enterPressed && gp.gameState == gp.dialogueState){
//                npc.dialogueIndex++;
//                gp.keyHandler.enterPressed = false;
//            }
//            if(gp.keyHandler.spacePressed && gp.gameState == gp.dialogueState){
//                npc.dialogueIndex++;
//                gp.keyHandler.spacePressed = false;
//            }
//            if(gp.keyHandler.enterPressed || gp.keyHandler.spacePressed){charIndex = 0;combinedText = "";}

        }else{//no text is in the array
            npc.dialogueIndex = 0;

            if(gp.gameState == gp.dialogueState){gp.gameState = gp.playState;}
        }


        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 28F));
        g2.setColor(Color.white);

        for(String line: currentDialogue.split("\n")){
            g2.drawString(line, x, y);
            y += 40;
        }
    }

    public void drawCharacterState(){

        //Create a frame
        final int frameX = gp.tileSize * 2;
        final int frameY = gp.tileSize;
        final int frameWidth = gp.tileSize * 5;
        final int frameHeight = gp.tileSize * 10;

        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        //text
        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Agency FB", 2, 25));

        int textX = frameX + 20;
        int valueX;
        int textY = frameY + gp.tileSize;

        final int lineHeight = 30;
        int tailX = (frameX + frameWidth) - 30;

        //Names & Values
        g2.drawString("Level", textX, textY);
        String value = String.valueOf(gp.player.level);valueX = getXForAlignedToRight(value, tailX);
        g2.drawString(value, valueX, textY);textY += lineHeight;

        g2.drawString("Life", textX, textY);
        value = String.valueOf(gp.player.life + "/" + gp.player.maxLife);valueX = getXForAlignedToRight(value, tailX);
        g2.drawString(value, valueX, textY);textY += lineHeight;

        g2.drawString("Strength", textX, textY);
        value = String.valueOf(gp.player.strength);valueX = getXForAlignedToRight(value, tailX);
        g2.drawString(value, valueX, textY); textY += lineHeight;

        g2.drawString("Dexterity", textX, textY);
        value = String.valueOf(gp.player.dexterity);valueX = getXForAlignedToRight(value, tailX);
        g2.drawString(value, valueX, textY);textY += lineHeight;

        g2.drawString("Attack", textX, textY);
        value = String.valueOf(gp.player.attack);valueX = getXForAlignedToRight(value, tailX);
        g2.drawString(value, valueX, textY);textY += lineHeight;

        g2.drawString("Defense", textX, textY);
        value = String.valueOf(gp.player.defense);valueX = getXForAlignedToRight(value, tailX);
        g2.drawString(value, valueX, textY);textY += lineHeight;

        g2.drawString("Exp", textX, textY);
        value = String.valueOf(gp.player.exp);valueX = getXForAlignedToRight(value, tailX);
        g2.drawString(value, valueX, textY);textY +=  lineHeight;

        g2.drawString("Next Level", textX, textY);
        value = String.valueOf(gp.player.nextLevelExp);valueX = getXForAlignedToRight(value, tailX);
        g2.drawString(value, valueX, textY);textY += lineHeight;

        g2.drawString("Coin", textX, textY);
        value = String.valueOf(gp.player.coin);valueX = getXForAlignedToRight(value, tailX);
        g2.drawString(value, valueX, textY);textY += lineHeight;

        g2.drawString("Mana", textX, textY);
        value = String.valueOf(gp.player.mana + "/" + gp.player.maxMana);valueX = getXForAlignedToRight(value, tailX);
        g2.drawString(value, valueX, textY);textY += lineHeight;

        g2.drawString("Weapon", textX, textY);
        value = String.valueOf(gp.player.currentWeapon.name);valueX = getXForAlignedToRight(value, tailX);
        g2.drawString(value, valueX, textY);textY += lineHeight;

        g2.drawString("Shield", textX, textY);
        value = String.valueOf(gp.player.currentShield.name);valueX = getXForAlignedToRight(value, tailX);
        g2.drawString(value, valueX, textY);textY += lineHeight;



        //To draw Image for curWeapon & curShield for each:
        //g2.drawImage(gp.player.currentWeapon.down1, tailX - gp.tileSize, textY - 15, null);
        //textY += gp.tileSize
    }

    public void drawGameOver(){

        g2.setColor(new Color(0, 0, 0, 150));
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

        int x;
        int y;
        String text;
        g2.setFont(new Font("Agency FB", 2, 25));
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 110f));

        text = "Game Over...";
        //Shadow
        g2.setColor(Color.black);
        x = centeredString(text);
        y = gp.tileSize * 4;
        g2.drawString(text, x, y);
        //text
        g2.setColor(Color.WHITE);
        g2.drawString(text, x-4, y-4);

        //Retry option
        g2.setFont(g2.getFont().deriveFont(50f));
        text = "Retry";
        x = centeredString(text);
        y += gp.tileSize * 4;
        g2.drawString(text, x, y);

        if(commandNum == 0){g2.drawString("->", x - 40, y);}

        //Title screen option
        text = "Quit";
        x = centeredString(text);
        y += gp.tileSize;
        g2.drawString(text, x, y);

        if(commandNum == 1){g2.drawString("->", x - 40, y);}
    }

    public void drawOptionsScreen(){
        g2.setColor(Color.white);
        g2.setFont(new Font("Agency FB", 2, 25));
        g2.setFont(g2.getFont().deriveFont(32F));

        //Create sub window
        int frameWidth = gp.tileSize * 8;
        int frameHeight = gp.tileSize * 10;
        int frameX = (gp.tileSize * gp.maxScreenCol / 2) - (frameWidth / 2);
        int frameY = gp.tileSize;
        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        switch(subState){
            case 0: options_top(frameX, frameY);break;
            case 1: break;
            case 2: options_control(frameX, frameY);break;
            case 3: options_endGameConf(frameX, frameY); break;
        }
        gp.keyHandler.enterPressed = false;
        gp.keyHandler.spacePressed = false;
    }

    public void options_top(int frameX, int frameY){

        int textX;
        int textY;
        g2.setColor(Color.WHITE);
        //title
        String text = "Options";
        textX = centeredString(text);
        textY = frameY + gp.tileSize;
        g2.drawString(text, textX, textY);

        //Full screen on/off
        textX = frameX + gp.tileSize;
        textY += gp.tileSize * 2;
        g2.drawString("Full Screen", textX, textY);
        if(commandNum == 0){
            g2.drawString("->", textX - 25, textY);
            if(gp.keyHandler.enterPressed || gp.keyHandler.spacePressed){

                if(gp.fullScreenOn == false){gp.fullScreenOn = true;}
                else if(gp.fullScreenOn){gp.fullScreenOn = false;
                }
                //subState = 1;
            }
        }

        //Music
        textY += gp.tileSize ;
        g2.drawString("Music", textX, textY);
        if(commandNum == 1){g2.drawString("->", textX - 25, textY);}

        //SE
        textY += gp.tileSize;
        g2.drawString("Sound FX", textX, textY);
        if(commandNum == 2){g2.drawString("->", textX - 25, textY);}

        //Controls
        textY += gp.tileSize;
        g2.drawString("Controls", textX, textY);
        if(commandNum == 3){
            g2.drawString("->", textX - 25, textY);
            if(gp.keyHandler.enterPressed || gp.keyHandler.spacePressed){subState = 2;commandNum = 0;}
        }

        //End Game
        textY += gp.tileSize;
        g2.drawString("QUIT", textX, textY);
        if(commandNum == 4){g2.drawString("->", textX - 25, textY);
            if(gp.keyHandler.enterPressed || gp.keyHandler.spacePressed){subState = 3; commandNum = 0;}
        }

        //Back
        textY += gp.tileSize * 2;
        g2.drawString("Back", textX, textY);
        if(commandNum == 5){g2.drawString("->", textX - 25, textY);
            if(gp.keyHandler.spacePressed || gp.keyHandler.enterPressed){gp.gameState = gp.playState;commandNum = 0;}
        }

        g2.setStroke(new BasicStroke(3));

            //FS check box
        textX = frameX + (int) (gp.tileSize * 4.5);
        textY = frameY + (gp.tileSize * 2) + (gp.tileSize / 2);
        g2.drawRect(textX, textY, gp.tileSize / 2, gp.tileSize / 2);
        if(gp.fullScreenOn){
            //gp.updateDimensions(40, 20);
            g2.fillRect(textX, textY, gp.tileSize / 2, gp.tileSize / 2);

        }else{
            //gp.updateDimensions(15, 12); gp.gameState = gp.optionsState;
        }

            //Music Volume slider
        textY += gp.tileSize;
        g2.drawRect(textX, textY, 120, gp.tileSize / 2); //120 / 5 = 24 pixels
        int volumeWidth = 24 * gp.music.volumeScale;
        g2.fillRect(textX, textY, volumeWidth, gp.tileSize / 2);

            //SE slider
        textY += gp.tileSize;
        g2.drawRect(textX, textY, 120, gp.tileSize / 2);
        volumeWidth = 24 * gp.soundEffect.volumeScale;
        g2.fillRect(textX, textY, volumeWidth, gp.tileSize / 2);

        try {gp.config.saveConfig();}catch(IOException e){e.printStackTrace();}
    }

    public void options_control(int frameX, int frameY){

        int textX;
        int textY;

        g2.setColor(Color.WHITE);
        //title
        String text = "Controls";
        textX = centeredString(text);
        textY = frameY + gp.tileSize;
        g2.drawString(text, textX, textY);
        textY += gp.tileSize * 2;

        textX = frameX + gp.tileSize;
        g2.drawString("Move", textX, textY); textY += gp.tileSize;
        g2.drawString("Interact/Attack", textX, textY); textY += gp.tileSize;
        g2.drawString("Shoot", textX, textY); textY += gp.tileSize;
        g2.drawString("Character Screen", textX, textY); textY += gp.tileSize;
        g2.drawString("Pause", textX, textY); textY += gp.tileSize;
        g2.drawString("Options", textX, textY); textY += gp.tileSize;

        textX = frameX + gp.tileSize * 5;
        textY = frameY + gp.tileSize * 3;

        g2.drawString("WASD", textX, textY); textY += gp.tileSize;
        g2.drawString("Space/Enter", textX, textY); textY += gp.tileSize;
        g2.drawString("F", textX, textY); textY += gp.tileSize;
        g2.drawString("P", textX, textY); textY += gp.tileSize;
        g2.drawString("ESC", textX, textY); textY += gp.tileSize;

        //Back
        textX = frameX + gp.tileSize;
        textY = frameY + gp.tileSize * 9;
        g2.drawString("Back", textX, textY);
        if(commandNum == 0){
            g2.drawString("->", textX - 25, textY);
            if(gp.keyHandler.enterPressed || gp.keyHandler.spacePressed){subState = 0;commandNum = 3;}
        }
    }

    public void options_endGameConf(int frameX, int frameY){

        int textX = frameX + gp.tileSize;
        int textY = frameY + gp.tileSize * 3;

        g2.setColor(Color.WHITE);

        currentDialogue = "Return to title Screen?";

        for(String line: currentDialogue.split("\n")){
            g2.drawString(line, textX, textY);
            textY += 40;
        }

        //Display yes nd no options
        String text = "Yes";
        textX = centeredString(text);
        textY += gp.tileSize * 3;
        g2.drawString(text, textX, textY);
        if(commandNum == 0){
            g2.drawString("->", textX - 25, textY);
            //Quit the game
            if(gp.keyHandler.enterPressed || gp.keyHandler.spacePressed){subState = 0; gp.gameState = gp.titleState; gp.ui.titleScreenState = 0;gp.exitToMenu();}
        }

        text = "No";
        textX = centeredString(text);
        textY += gp.tileSize;
        g2.drawString(text, textX, textY);
        if(commandNum == 1){
            g2.drawString("->", textX - 25, textY);
            if(gp.keyHandler.enterPressed || gp.keyHandler.spacePressed){subState = 0; commandNum = 4;}
        }
    }

    public void drawTransition(){

        counter++;
        g2.setColor(new Color(0, 0, 0, counter * 5));
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

        if(counter == 50){
            counter = 0;
            gp.gameState = gp.playState;

            gp.currMap = gp.eH.tempMap;
            gp.player.worldX = gp.eH.tempCol * gp.tileSize;
            gp.player.worldY = gp.eH.tempRow * gp.tileSize;
            gp.eH.prevEventX = gp.player.worldX;
            gp.eH.prevEventY = gp.player.worldY;
            gp.changeArea();
        }
    }

    public void drawInventory(Entity entity, boolean cursor){

        int frameX = 0;
        int frameY = 0;
        int frameWidth = 0;
        int frameHeight = 0;
        int slotCol = 0;
        int slotRow = 0;

        if(entity == gp.player){
            frameX = gp.screenWidth / 2;
            frameY = gp.tileSize;
            frameWidth = gp.tileSize * 6;
            frameHeight = gp.tileSize * 5;
            slotCol = playerSlotCol;
            slotRow = playerSlotRow;
        }else{
            frameX = gp.tileSize * 2;
            frameY = gp.tileSize;
            frameWidth = gp.tileSize * 6;
            frameHeight = gp.tileSize * 5;
            slotCol = npcSlotCol;
            slotRow = npcSlotRow;
        }

        //Draw Frame
        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        //SLot
        final int slotXStart = frameX + 20;
        final int slotYStart = frameY + 20;
        int slotX = slotXStart;
        int slotY = slotYStart;
        int slotSize = gp.tileSize + 3;

        //Draw Items
        for(int i = 0; i < entity.inventory.size(); i++){

            //Equip cursor
            if(entity.inventory.get(i) == entity.currentWeapon || entity.inventory.get(i) == entity.currentShield || entity.inventory.get(i) == entity.currLight){
                g2.setColor(new Color(240, 190, 90));
                g2.fillRoundRect(slotX, slotY, gp.tileSize, gp.tileSize, 10, 10);

            }

            g2.drawImage(entity.inventory.get(i).down1, slotX, slotY, null);

            //Display amount
            if(entity.inventory.get(i).amount > 1){
                g2.setFont(g2.getFont().deriveFont(32f));
                int amountX;
                int amountY;

                String s = Integer.toString(entity.inventory.get(i).amount);
                amountX = getXForAlignedToRight(s, slotX + 44);
                amountY = slotY + gp.tileSize;

                //shadow
                g2.setColor(new Color(60, 60, 60));
                g2.drawString(s, amountX, amountY);
                //Number
                g2.setColor(Color.WHITE);
                g2.drawString(s, amountX - 3, amountY - 3);
            }

            slotX += slotSize;
            if (i == 4 || i == 9 || i == 14){
                slotX = slotXStart;
                slotY += slotSize;
            }
        }

        //Cursor init
        if(cursor) {
            int cursorX = slotXStart + (slotSize * slotCol);
            int cursorY = slotYStart + (slotSize * slotRow);
            int cursorWidth = gp.tileSize;
            int cursorHeight = gp.tileSize;

            //Draw Cursor
            g2.setColor(Color.WHITE);
            g2.setStroke(new BasicStroke(4));
            g2.drawRoundRect(cursorX, cursorY, cursorWidth, cursorHeight, 10, 10);

            //draw description frame
            int dframeX = frameX;
            int dframeY = frameY + frameHeight;
            int dframeWidth = frameWidth;
            int dframeHeight = gp.tileSize * 3;
            //Draw description text
            int textX = dframeX + 20;
            int textY = dframeY + gp.tileSize;

            int invIndex = getItemIndex(slotCol, slotRow);

            if (invIndex < entity.inventory.size()) {
                drawSubWindow(dframeX, dframeY, dframeWidth, dframeHeight);
                g2.setColor(Color.GRAY);
                g2.setFont(g2.getFont().deriveFont(28F));
                for (String line : entity.inventory.get(invIndex).description.split("\n")) {
                    g2.drawString(line, textX, textY);
                    textY += 32;
                }
            }
        }
    }

    public void drawTradeScreen(){
    if(gp.currMap == 1 && npc != null) {
        switch (subState) {
            case 0: tradeSelect();break;
            case 1: tradeBuy();break;
            case 2: tradeSell();break;
        }
    }
    }

    public void tradeSelect(){

//        System.out.println("Trade Select - commandNum: " + commandNum + ", subState: " + subState);
//        System.out.println("NPC: " + (npc != null ? npc.name : "NULL"));
//        System.out.println("enterPressed: " + gp.keyHandler.enterPressed + ", spacePressed: " + gp.keyHandler.spacePressed);

        //Display dialogue options
        //currentDialogue = "";
        npc.dialogueSet = 0;
        drawDialogueScreen();
        //Draw window
        int x = gp.tileSize * 15;
        int y = gp.tileSize * 4;
        int width = gp.tileSize * 3;
        int height = (int)(gp.tileSize * 3.5);
        drawSubWindow(x, y, width, height);

        g2.setColor(Color.WHITE);

        //Draw text
        x += gp.tileSize;
        y += gp.tileSize;
        g2.drawString("Buy", x, y);
        if(commandNum == 0){
            g2.drawString("->", x - 25, y);
            if(gp.keyHandler.enterPressed || gp.keyHandler.spacePressed){subState = 1;}
        }
        y += gp.tileSize;
        g2.drawString("Sell", x, y);
        if(commandNum == 1){
            g2.drawString("->", x - 25, y);
            if(gp.keyHandler.enterPressed || gp.keyHandler.spacePressed){subState = 2;}
        }
        y += gp.tileSize;
        g2.drawString("Back", x, y);
        if(commandNum == 2){
            g2.drawString("->", x - 25, y);
            if(gp.keyHandler.enterPressed || gp.keyHandler.spacePressed){
                commandNum = 0;
                //npc.startDialogue(npc, 1);
                gp.gameState = gp.playState;
            }
        }
        y += gp.tileSize;


    }

    public void tradeBuy(){

        //Draw plr inventory
        drawInventory(gp.player, false);

        //Draw NPC Inventory
        drawInventory(npc, true);

        //Draw instructions window
        int x = gp.tileSize * 2;
        int y = gp.tileSize * 9;
        int width = gp.tileSize * 6;
        int height = gp.tileSize * 2;
        drawSubWindow(x, y, width, height);
        g2.drawString("[ESC] Back", x + 24, y + 60);

        //Draw player coin window
        x = gp.screenWidth / 2;
        y = gp.tileSize * 7;
        g2.setColor(Color.WHITE);
        width = gp.tileSize * 6;
        height = gp.tileSize * 2;
        drawSubWindow(x, y, width, height);
        g2.drawString("Coin " + gp.player.coin, x + 24, y + 60);

        //Draw Price Window
        int itemIndex = getItemIndex(npcSlotCol, npcSlotRow);
        if(itemIndex < npc.inventory.size()){
            x = (int) (gp.tileSize * 5.5);
            y = (int) (gp.tileSize * 5.5);
            width = (int) (gp.tileSize * 2.5);
            height = gp.tileSize;
            drawSubWindow(x, y, width , height);
            g2.drawImage(coin, x + 10, y + 8, 32, 32, null);
            int price = npc.inventory.get(itemIndex).price;
            String text = String.valueOf(price);
            x = getXForAlignedToRight(text, (gp.tileSize * 8) - 20);
            g2.drawString(text, x, y + 34);

            //Buy an Item
            if(gp.keyHandler.enterPressed || gp.keyHandler.spacePressed) {
                if (!tradeBuyCooldown) {
                    //Not enough money
                    if (npc.inventory.get(itemIndex).price > gp.player.coin) {
                        subState = 0;
                        gp.gameState = gp.dialogueState;
                        npc.startDialogue(npc, 2);
//                    currentDialogue = "Insufficient funds!";
                        drawDialogueScreen();
                    }

                    //Inventory is full

                    else if (gp.player.canObtainItem(npc.inventory.get(itemIndex))) {
                        gp.player.inventory.add(npc.inventory.get(itemIndex).createNew(gp));
                    } else {
                        subState = 0;
                        npc.startDialogue(npc, 3);
                        //drawDialogueScreen();

                    }
                    tradeBuyCooldown = true;

//                else if(gp.player.inventory.size() == gp.player.maxInventorySize){
//                    subState = 0;
//                    gp.gameState = gp.dialogueState;
//                    currentDialogue = "Inventory Full!";
//                    drawDialogueScreen();
//                }
//
//                else{
//                    gp.player.coin -= npc.inventory.get(itemIndex).price;
//                    gp.player.inventory.add(npc.inventory.get(itemIndex));
//                }
                }

            }
            if(tradeBuyCooldown){
                tradeBuyCounter++;
                if(tradeBuyCounter > 20){
                    tradeBuyCooldown = false;
                    tradeBuyCounter = 0;
                }
            }

        }
    }

    public void tradeSell(){

        //Draw plr inventory
        drawInventory(gp.player, true);

        //Draw instructions window
        int x = gp.tileSize * 2;
        int y = gp.tileSize * 9;
        int width = gp.tileSize * 6;
        int height = gp.tileSize * 2;
        drawSubWindow(x, y, width, height);
        g2.drawString("[ESC] Back", x + 24, y + 60);

        //Draw player coin window
        x = gp.screenWidth / 2;
        y = gp.tileSize * 7;
        g2.setColor(Color.WHITE);
        width = gp.tileSize * 6;
        height = gp.tileSize * 2;
        drawSubWindow(x, y, width, height);
        g2.drawString("Coin " + gp.player.coin, x + 24, y + 60);

        //Draw Price Window
        int itemIndex = getItemIndex(playerSlotCol, playerSlotRow);
        if(itemIndex < gp.player.inventory.size()){
            x = (int) (gp.tileSize * 15.5);
            y = (int) (gp.tileSize * 5.5);
            width = (int) (gp.tileSize * 2.5);
            height = gp.tileSize;
            drawSubWindow(x, y, width , height);
            g2.drawImage(coin, x + 10, y + 8, 32, 32, null);
            int price = gp.player.inventory.get(itemIndex).price;
            String text = String.valueOf(price);
            x = getXForAlignedToRight(text, (gp.tileSize * 18) - 20);
            g2.drawString(text, x, y + 34);

            //sell an Item
            if(gp.keyHandler.enterPressed || gp.keyHandler.spacePressed){
                if(!tradeSellCooldown) {
                    if (gp.player.inventory.get(itemIndex) == gp.player.currentWeapon || gp.player.inventory.get(itemIndex) == gp.player.currentShield) {
                        subState = 0;
                        npc.startDialogue(npc, 4);
                        //addMessage("You cannot sell an equipped item");
                    } else {
                        if (gp.player.inventory.get(itemIndex).amount > 1) {
                            gp.player.inventory.get(itemIndex).amount--;
                        } else {
                            gp.player.inventory.remove(itemIndex);
                        }
                        gp.player.coin += price / 2;
                    }
                    tradeSellCooldown = true;
                }

            }
            if(tradeSellCooldown){
                tradeSellCounter++;
                if(tradeSellCounter > 20){
                    tradeSellCooldown = false;
                    tradeSellCounter = 0;
                }
            }

        }

    }

    public int getItemIndex(int slotCol, int slotRow){
        int itemIndex = slotCol + (slotRow * 5);
        return itemIndex;}

    public void drawSubWindow(int x, int y, int width, int height){

        Color c = new Color(0, 0, 0, 200);
        g2.setColor(c);
        g2.fillRoundRect(x, y, width, height, 35, 35);

        c = new Color(200, 200, 255);
        g2.setColor(c);
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(x + 5, y + 5, width - 10, height - 10, 25, 25);
    }

    public void drawPauseScreen(){


        g2.setFont(arial_80);

        String text = "Paused";
        int x = centeredString(text);

        int y = gp.screenHeight / 2;

        g2.drawString(text, x, y);

    }

    public int centeredString(String text){
        int x;
        int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        x = (gp.screenWidth / 2) - (length / 2);
        return x;
    }

    public void drawTitle(){

        //Main title screen
        if(titleScreenState == 0) {
            //Set background
            g2.setColor(Color.PINK);
            g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

            //Title Name
            //Lost Realms Echoes of the Past
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 85F));
            String text = "Lost Realms";
            int x = centeredString(text);
            int y = gp.tileSize * 3;

            //Shadow
            g2.setColor(Color.GRAY);
            g2.drawString(text, x + 5, y + 5);

            //Main colour
            g2.setColor(Color.white);
            g2.drawString(text, x, y);

            //Character Image
            x = gp.screenWidth / 2 - gp.tileSize;
            y += gp.tileSize * 2;
            g2.drawImage(gp.player.down1, x, y, gp.tileSize * 2, gp.tileSize * 2, null);

            //Menu

            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 55F));
            text = "New Game";
            x = centeredString(text);
            y += gp.tileSize * 3.5;
            g2.drawString(text, x, y);
            if (commandNum == 0) {
                //g2.setFont(g2.getFont().deriveFont(Font.BOLD, 40F));
                g2.drawString("->", x - gp.tileSize, y);
            }

            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 55F));
            text = "Load Game";
            x = centeredString(text);
            y += gp.tileSize + 20;
            g2.drawString(text, x, y);
            if (commandNum == 1) {
                //g2.setFont(g2.getFont().deriveFont(Font.BOLD, 40F));
                g2.drawString("->", x - gp.tileSize, y);
            }

            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 55F));
            text = "Quit";
            y += gp.tileSize + 20;
            x = centeredString(text);
            g2.drawString(text, x, y);
            if (commandNum == 2) {
                //g2.setFont(g2.getFont().deriveFont(Font.BOLD, 40F));
                g2.drawString("->", x - gp.tileSize, y);
            }
        }
        //Class title screen
        else if(titleScreenState == 1){

            g2.setColor(Color.PINK);
            g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

            //Class selection screen
            g2.setColor(Color.WHITE);
            g2.setFont(g2.getFont().deriveFont(42F));

            String text = "Select a class";
            int x = centeredString(text);
            int y = gp.tileSize * 2;

            g2.drawString(text, x, y);

            text = "Fighter";
            x = centeredString(text);
            y += gp.tileSize * 2.5;
            g2.drawString(text, x, y);
            if(commandNum == 0){
                g2.drawString("->", x - gp.tileSize, y);}

            text = "Explorer";
            y += gp.tileSize + 20;
            g2.drawString(text, x, y);
            if(commandNum == 1){
                g2.drawString("->", x - gp.tileSize, y);}

            text = "Sorcerer";
            y += gp.tileSize + 20;
            g2.drawString(text, x, y);
            if(commandNum == 2){
                g2.drawString("->", x - gp.tileSize, y);}

            text = "Paladin";
            y += gp.tileSize + 20;
            g2.drawString(text, x, y);
            if(commandNum == 3){
                g2.drawString("->", x - gp.tileSize, y);}


            text = "Back";
            y += gp.tileSize * 2;
            g2.drawString(text, x, y);
            if(commandNum == 4){
                g2.drawString("->", x - gp.tileSize, y);}

        }

        //g2.drawString(text, x, y);
    }

    public int getXForAlignedToRight(String text, int tailX){

        int len = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = tailX - len;
        return x;
    }



}
