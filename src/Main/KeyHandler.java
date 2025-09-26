package Main;

import Entity.Merchant;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;

public class KeyHandler implements KeyListener {


    GamePanel gp;
    public boolean upPressed, downPressed, leftPressed, rightPressed, spacePressed, enterPressed, shotKeyPressed;//shotKeyPressed is VK_F. probably wont end up using it this way however
    //DEBUG
    boolean showdebugText = false;

    public int tradeKeyCooldown = 0;


    public KeyHandler(GamePanel gp) {
        this.gp = gp;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        //Title State
        if (gp.gameState == gp.titleState) {titleState(code);}

        //Play state
        else if (gp.gameState == gp.playState) {playState(code);}

        //Pause state
        else if (gp.gameState == gp.pauseState) {pauseState(code);}

        //dialogue state
        else if (gp.gameState == gp.dialogueState) {dialogueState(code);}

        //Character state
        else if(gp.gameState == gp.characterState){characterState(code);}

        //Options State
        else if(gp.gameState == gp.optionsState){optionsState(code);}

        //Game over state
        else if(gp.gameState == gp.gameOverState){gameOverState(code);}

        //Trade State
        else if(gp.gameState == gp.tradeState){tradeState(code);}

        //map state
        else if(gp.gameState == gp.mapState){mapState(code);}

    }

    public void titleState(int code){

        if (gp.ui.titleScreenState == 0) {
            if (code == KeyEvent.VK_W) {
                if (gp.ui.commandNum == 0) {
                    gp.ui.commandNum = 2;
                } else {
                    gp.ui.commandNum -= 1;
                }
            }
            if (code == KeyEvent.VK_S) {
                if (gp.ui.commandNum == 2) {
                    gp.ui.commandNum = 0;
                } else {
                    gp.ui.commandNum += 1;
                }
            }
            if (code == KeyEvent.VK_ENTER) {
                switch (gp.ui.commandNum) {
                    case 0:
                        //Start a new game, move to choose character
                        gp.ui.titleScreenState = 1;
                        break;
                    case 1:
                        //
                        gp.saveLoad.load();
                        gp.gameState = gp.playState;
                        gp.playMusic(0);
                        break;
                    case 2:
                        System.exit(0);
                        break;
                }
            }

        } else if (gp.ui.titleScreenState == 1) {
            if (code == KeyEvent.VK_W) {
                if (gp.ui.commandNum == 0) {
                    gp.ui.commandNum = 4;
                } else {
                    gp.ui.commandNum -= 1;
                }
            }
            if (code == KeyEvent.VK_S) {
                if (gp.ui.commandNum == 4) {
                    gp.ui.commandNum = 0;
                } else {
                    gp.ui.commandNum += 1;
                }
            }
            if (code == KeyEvent.VK_ENTER) {
                switch (gp.ui.commandNum) {
                    case 0:
                        gp.playMusic(0);
                        gp.gameState = gp.playState;
                        break;
                    case 1:
                        gp.playMusic(0);
                        gp.gameState = gp.playState;
                        break;
                    case 2:
                        gp.playMusic(0);
                        gp.gameState = gp.playState;
                        break;
                    case 3:
                        gp.playMusic(0);
                        gp.gameState = gp.playState;
                        break;
                    case 4:
                        gp.ui.titleScreenState = 0;
                        gp.ui.commandNum = 0;
                        break;
                }
            }

        }
    }

    public void playState(int code){

        if (code == KeyEvent.VK_W) {upPressed = true;}

        if (code == KeyEvent.VK_A) {leftPressed = true;}

        if (code == KeyEvent.VK_S) {downPressed = true;}

        if (code == KeyEvent.VK_D) {rightPressed = true;}

        if (code == KeyEvent.VK_C) {gp.gameState = gp.characterState;}

        //Pause game
        if (code == KeyEvent.VK_P) {gp.gameState = gp.pauseState;}

        if(code == KeyEvent.VK_ESCAPE){gp.gameState = gp.optionsState;}

        if (code == KeyEvent.VK_SPACE) {spacePressed = true;}

        if (code == KeyEvent.VK_ENTER) {enterPressed = true;}
        if(code == KeyEvent.VK_F){shotKeyPressed = true;}

        if(code == KeyEvent.VK_M){gp.gameState = gp.mapState;}

        //DEBUG
        if (code == KeyEvent.VK_T) {
            if (showdebugText == false) {
                showdebugText = true;
            } else {
                if (showdebugText) {showdebugText = false;}
            }
        }

        if(code ==  KeyEvent.VK_R){
            gp.tileManager.loadMap("/maps/" + gp.mapName + ".txt", gp.currMap);
        }

        if(code == KeyEvent.VK_X){
            if(!gp.map.miniMapOn){gp.map.miniMapOn = true;}
            else{gp.map.miniMapOn = false;}
        }
    }

    public void pauseState(int code){
        if (code == KeyEvent.VK_P) {
            if (gp.gameState == gp.pauseState) {
                gp.gameState = gp.playState;
            }

        }
    }

    public void dialogueState(int code){

        if (code == KeyEvent.VK_SPACE) {spacePressed = true;}
        if(code == KeyEvent.VK_ENTER) {enterPressed = true;}
    }

    public void characterState(int code){
        if(code == KeyEvent.VK_C){gp.gameState = gp.playState;}

        playerInventoryControls(code);

        if(code == KeyEvent.VK_SPACE || code == KeyEvent.VK_ENTER){gp.player.selectItem();}
    }

    public void playerInventoryControls(int code){

        if(code == KeyEvent.VK_W) {
            if (gp.ui.playerSlotRow != 0){gp.ui.playerSlotRow--;gp.playSE(9);}
        }
        if(code == KeyEvent.VK_A){
            if(gp.ui.playerSlotCol != 0){gp.ui.playerSlotCol --;gp.playSE(9);}
        }
        if(code == KeyEvent.VK_S){
            if(gp.ui.playerSlotRow != 3){gp.ui.playerSlotRow++;gp.playSE(9);}
        }
        if(code == KeyEvent.VK_D){
            if(gp.ui.playerSlotCol != 4){gp.ui.playerSlotCol++;gp.playSE(9);}
        }
    }
    public void npcInventoryControls(int code){

        if(code == KeyEvent.VK_W) {
            if (gp.ui.npcSlotRow != 0){gp.ui.npcSlotRow--;gp.playSE(9);}
        }
        if(code == KeyEvent.VK_A){
            if(gp.ui.npcSlotCol != 0){gp.ui.npcSlotCol --;gp.playSE(9);}
        }
        if(code == KeyEvent.VK_S){
            if(gp.ui.npcSlotRow != 3){gp.ui.npcSlotRow++;gp.playSE(9);}
        }
        if(code == KeyEvent.VK_D){
            if(gp.ui.npcSlotCol != 4){gp.ui.npcSlotCol++;gp.playSE(9);}
        }

    }

    public void optionsState(int code){

            if(code == KeyEvent.VK_ESCAPE){gp.gameState = gp.playState;}

            if(code == KeyEvent.VK_ENTER){enterPressed = true;}
            if(code == KeyEvent.VK_SPACE){spacePressed = true;}

            int maxCommandNum = 0;
            switch(gp.ui.subState){
                case 0: maxCommandNum = 5;break;
                case 1: break;
                case 3: maxCommandNum = 1; break;
            }
            if(code == KeyEvent.VK_W){
                gp.ui.commandNum--;gp.playSE(9);
                if(gp.ui.commandNum < 0){gp.ui.commandNum = maxCommandNum;}
            }
            if(code == KeyEvent.VK_S){
                gp.ui.commandNum++; gp.playSE(9);
                if(gp.ui.commandNum > maxCommandNum){gp.ui.commandNum = 0;}
            }
            if(code == KeyEvent.VK_A){
                if(gp.ui.subState == 0){
                    if(gp.ui.commandNum == 1 && gp.music.volumeScale > 0){
                        gp.music.volumeScale --;
                        gp.music.checkVolume();
                        gp.playSE(9);
                    }
                    if(gp.ui.commandNum == 2 && gp.soundEffect.volumeScale > 0){
                        gp.soundEffect.volumeScale --;
                        gp.playSE(9);
                    }

                }
            }
            if(code == KeyEvent.VK_D) {
                if (gp.ui.subState == 0) {
                    if (gp.ui.commandNum == 1 && gp.music.volumeScale < 5) {
                        gp.music.volumeScale++;
                        gp.music.checkVolume();
                        gp.playSE(9);
                    }
                    if (gp.ui.commandNum == 2 && gp.soundEffect.volumeScale < 5) {
                        gp.soundEffect.volumeScale++;
                        gp.playSE(9);
                    }

                }
            }
    }

    public void gameOverState(int code){

        if(code == KeyEvent.VK_W){
            gp.ui.commandNum --;
            if(gp.ui.commandNum < 0){gp.ui.commandNum = 1;}
            gp.playSE(9);
        }
        if(code == KeyEvent.VK_S){
            gp.ui.commandNum ++;
            if(gp.ui.commandNum > 1){gp.ui.commandNum = 0;}
            gp.playSE(9);
        }

        if(code == KeyEvent.VK_ENTER || code == KeyEvent.VK_SPACE){
            if(gp.ui.commandNum == 0){gp.stopMusic();gp.retry();}

            else if(gp.ui.commandNum == 1){gp.stopMusic();gp.exitToMenu();}
        }
    }

    public void tradeState(int code){

//        if(gp.ui.npc == null && gp.currMap == 1) {
//            for(int i = 0; i < gp.npc[1].length; i++) {
//                if(gp.npc[1][i] != null && gp.npc[1][i] instanceof Merchant) {
//                    gp.ui.npc = gp.npc[1][i];
//                    System.out.println("KeyHandler.tradeState() - Restored npc reference: " + gp.ui.npc.name);
//                    break;
//                }
//            }
//        }

        if(code == KeyEvent.VK_ENTER){enterPressed = true;}
        if(code == KeyEvent.VK_SPACE){spacePressed = true;}

        if(gp.ui.subState == 0) {
            if (code == KeyEvent.VK_W) {
                gp.ui.commandNum--;
                if (gp.ui.commandNum < 0) {gp.ui.commandNum = 2;}
                gp.playSE(9);
            }
            if (code == KeyEvent.VK_S) {
                gp.ui.commandNum++;
                if (gp.ui.commandNum > 2) {gp.ui.commandNum = 0;}
                gp.playSE(9);
            }


            if ((enterPressed || spacePressed) && tradeKeyCooldown < 5) {
                // Handle selection here instead of in UI
                if (gp.ui.commandNum == 0) {gp.ui.subState = 1;}
                else if (gp.ui.commandNum == 1) {gp.ui.subState = 2;}
                else if (gp.ui.commandNum == 2) {
                    gp.ui.commandNum = 0;
                    gp.gameState = gp.playState;
                }
                if(tradeKeyCooldown > 40){tradeKeyCooldown = 0;}

                tradeKeyCooldown++;
                enterPressed = false;
                spacePressed = false;
            }

        }

        if(gp.ui.subState == 1){
            npcInventoryControls(code);
            if(code == KeyEvent.VK_ESCAPE){gp.ui.subState = 0;gp.gameState = gp.playState;}
        }
        if(gp.ui.subState == 2){
            playerInventoryControls(code);
            if(code == KeyEvent.VK_ESCAPE){gp.ui.subState = 0;gp.gameState = gp.playState;}
        }
    }

    public void mapState(int code){
        if(code == KeyEvent.VK_M){
            gp.gameState = gp.playState;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();

        if (code == KeyEvent.VK_W) {upPressed = false;}
        if (code == KeyEvent.VK_A) {leftPressed = false;}
        if (code == KeyEvent.VK_S) {downPressed = false;}
        if (code == KeyEvent.VK_D) {rightPressed = false;}

        if(code == KeyEvent.VK_F){shotKeyPressed = false;}

        if(code == KeyEvent.VK_ENTER){enterPressed = false;}
        if(code == KeyEvent.VK_SPACE){spacePressed = false;}
    }
}

