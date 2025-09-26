package Main;

import AI.PathFinder;
import Data.SaveLoad;
import Entity.Entity;
import Entity.Player;
import Environment.EnvManager;
import InteractiveTile.InteractiveTile;
import Tile.Map;
import Tile.TileManager;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import Object.OBJ_Axe;
import Object.OBJ_Boots;
import Object.OBJ_Chest;
import Object.OBJ_Coin_Bronze;
import Object.OBJ_Door;
import Object.OBJ_Fireball;
import Object.OBJ_Heart;
import Object.OBJ_Key;
import Object.OBJ_Lantern;
import Object.OBJ_ManaCrystal;
import Object.OBJ_Potion_Red;
import Object.OBJ_Rock;
import Object.OBJ_Shield_Blue;
import Object.OBJ_Sword_Normal;
import Object.OBJ_Wood_Shield;

public class GamePanel extends JPanel implements Runnable{

    //Screen settings

    final int originalTileSize = 16; //16x16 tiles
    final int scale = 3;

    public final int tileSize = originalTileSize * scale; //48x48 tile
    public int maxScreenCol = 40;//Final? //40, 20
    public int maxScreenRow = 20;//15,12
    public int screenWidth = tileSize * maxScreenCol; // 960 pixels
    public int screenHeight = tileSize * maxScreenRow; // 576 pixels

    //For full screen
    int screenWidth2 = screenWidth;
    int screenHeight2 = screenHeight;
    BufferedImage tempScreen;
    Graphics2D g2;
    public boolean fullScreenOn = false;

    //WorldSettings
    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;
    public final int worldWidth = tileSize * maxWorldCol;
    public final int worldHeight = tileSize * maxWorldRow;
    public final int maxMap = 10;
    public int currMap = 0;


    //FPS
    int FPS = 60;

    //System
    public TileManager tileManager = new TileManager(this);
    public KeyHandler keyHandler = new KeyHandler(this);
    public String mapName = "worldV3";
    Sound soundEffect = new Sound();
    Sound music = new Sound();
    public CollisionChecker collisionChecker = new CollisionChecker(this);
    public AssetSetter assetSetter = new AssetSetter(this);
    public UI ui  = new UI(this);
    public EventHandler eH = new EventHandler(this);
    Config config = new Config(this);
    public PathFinder pathFinder = new PathFinder(this);
    public EnvManager envManager = new EnvManager(this);
    public SaveLoad saveLoad = new SaveLoad(this);
    public EntityGenerator entityGenerator = new EntityGenerator(this);
    Map map = new Map(this);
    Thread gameThread;

    //Entity and Object
    public Player player = new Player(this, keyHandler);
    public Entity[][] obj = new Entity[maxMap][25];
    public Entity[][] npc = new Entity[maxMap][10];
    public Entity[][] monster = new Entity[maxMap][20];
    public InteractiveTile[][] iTile  = new InteractiveTile[maxMap][50];
    ArrayList<Entity> entityList = new ArrayList<>();
    public Entity[][] projectile = new Entity[maxMap][20];
    //public ArrayList<Entity> projectileList = new ArrayList<>();
    public ArrayList<Entity> particleList = new ArrayList<>();
    public ArrayList<Entity> allObjects;
    public boolean doorsStartup = true;

    //Area
    public int currArea;
    public int nextArea;
    public final int outside = 50;
    public final int inside = 51;
    public final int dungeon = 52;

    //Game State
    public final int titleState = 0;
    public int gameState;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int dialogueState = 3;
    public final int characterState = 4;
    public final int optionsState = 5;
    public final int gameOverState = 6;
    public final int transitionState = 7;
    public final int tradeState = 8;
    public final int mapState = 9;

    public GamePanel(){
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyHandler);
        this.setFocusable(true);
        allObjects = new ArrayList<>();

        allObjects.add(new OBJ_Axe(this));
        allObjects.add(new OBJ_Boots(this));
        allObjects.add(new OBJ_Chest(this));
        allObjects.add(new OBJ_Coin_Bronze(this));
        allObjects.add(new OBJ_Door(this));
        allObjects.add(new OBJ_Fireball(this));
        allObjects.add(new OBJ_Heart(this));
        allObjects.add(new OBJ_Key(this));
        allObjects.add(new OBJ_Lantern(this));
        allObjects.add(new OBJ_ManaCrystal(this));
        allObjects.add(new OBJ_Potion_Red(this));
        allObjects.add(new OBJ_Rock(this));
        allObjects.add(new OBJ_Shield_Blue(this));
        allObjects.add(new OBJ_Sword_Normal(this));
        allObjects.add(new OBJ_Wood_Shield(this));
    }

    public void setupGame(){
        assetSetter.setInteractiveTile();
        assetSetter.setObject();
        assetSetter.setDoors();
        //playMusic(0);
        assetSetter.setNCP();
        assetSetter.setMonster();
        envManager.setup();
        gameState = titleState;
        currArea = outside;

        tempScreen = new BufferedImage(screenWidth, screenHeight, BufferedImage.TYPE_INT_ARGB);
        g2 = (Graphics2D) tempScreen.getGraphics();

        if(fullScreenOn){setFullScreen();}
    }

    //Update width & height dimensions for screen
//nah im bouta rash the fuck out like i cant with ts. shit jus wont work idk dont understand code dont leanr to code its not worth it fr
    public void updateDimensions(int maxCol, int maxRow){
        maxScreenCol = maxCol; maxScreenRow = maxRow;
        screenWidth = tileSize * maxScreenCol; screenHeight = tileSize * maxScreenRow;
        screenWidth2 = screenWidth; screenHeight2 = screenHeight;

        tempScreen = new BufferedImage(screenWidth, screenHeight, BufferedImage.TYPE_INT_ARGB);
        g2 = (Graphics2D) tempScreen.getGraphics();

        setFullScreen();
    }

    public void setFullScreen(){

        //get local screen device
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();
        //gd.setFullScreenWindow(Main.window);

        //get fullscreen width & height
        screenWidth2 = Main.window.getWidth();
        screenHeight2 = Main.window.getHeight();

        //Main.window.setVisible(true);
    }

    public void retry(){//Keep plr items on retry
        player.restoreDefaultValues();
        //reset nonPlaceables in assetSetter
        assetSetter.nonPlaceables = new int[maxMap][50][];
        assetSetter.nonPlaceableIndex = 0;
//        for(int i = 0; i < assetSetter.nonPlaceables[1].length; i++){
//            if(assetSetter.nonPlaceables.)
//        }
        //playMusic(0);
        assetSetter.setMonster();
        player.resetCounters();
        gameState = playState;
        envManager.lighting.resetDay();
    }

    //restart
    public void exitToMenu(){

        for(int i = 0; i < assetSetter.nonPlaceables.length; i++){assetSetter.nonPlaceables[i] = null;}
        assetSetter.nonPlaceables = new int[maxMap][100][];
        assetSetter.nonPlaceableIndex = 0;
        currMap = 0;
        player.inventory.clear();
        player.restoreDefaultValues();
        assetSetter.setInteractiveTile();
        assetSetter.setObject();
        //playMusic(0);
        assetSetter.setNCP();
        assetSetter.setMonster();
        ui.titleScreenState = 0;
        gameState = titleState;
        envManager.lighting.resetDay();

    }

    public void startGameThread(){
        gameThread = new Thread(this);
        gameThread.start();
    }

   @Override
   public void run(){

    double drawInterval = (double) 1000000000/FPS; //0.01666 seconds
    double delta = 0;
    long lastTime = System.nanoTime();
    long currentTime;

    long timer = 0;
    int drawCount = 0;

    while(gameThread != null){
    currentTime = System.nanoTime();
    delta += (currentTime - lastTime) / drawInterval;
    timer += (currentTime - lastTime);
    lastTime = currentTime;

    if(delta >= 1){
    update();
    //repaint();
    drawToTempScreen();//draw everything to buffered image
    drawToScreen();//draw buffered image to screen
    delta--;
    drawCount++;

    }
    if(timer >= 1000000000){
        System.out.println("FPS: " + drawCount);
        drawCount = 0;
        timer = 0;
    }
    }
    }

/*
    public void run() {

        double drawInterval = (double) 1000000000/FPS; //0.01666 seconds
        double nextDrawTime = System.nanoTime() + drawInterval;

        while(gameThread != null){

            //1: Update information such as character positions
            update();
            //2: Draw: Draw the screen with updated information
            repaint();



            try {double remainingTime = nextDrawTime - System.nanoTime();
                remainingTime /= 1000000;
                if(remainingTime < 0){
                    remainingTime = 0;}
                Thread.sleep((long) remainingTime);
                nextDrawTime += drawInterval;
            } catch (InterruptedException e) {throw new RuntimeException(e);}

        }
    }
 */

    public void update() {

        if(gameState == playState) {

            //Player
            player.update();

            //NPC
            for(int i = 0; i < npc[1].length; i++){
                if(npc[currMap][i] != null){
                    npc[currMap][i].update();}
            }
            //Monster
            for(int i = 0; i < monster[1].length; i++){
                if(monster[currMap][i] != null) {

                    if (monster[currMap][i].alive && monster[currMap][i].dying == false){monster[currMap][i].update();}
                    if(monster[currMap][i].alive == false){monster[currMap][i].checkDrop();monster[currMap][i] = null;}
                }
            }

            //Projectile
            for(int i = 0; i < projectile[1].length; i++){
                if(projectile[currMap][i] != null) {

                    if (projectile[currMap][i].alive){projectile[currMap][i].update();}
                    if(projectile[currMap][i].alive == false){projectile[currMap][i] = null;}
                }
            }
            //Particle
            for(int i = 0; i < particleList.size(); i++){
                if(particleList.get(i) != null){
                    if(particleList.get(i).alive){particleList.get(i).update();}
                    if(particleList.get(i).alive == false){particleList.remove(i);}
                }
            }

            //Interactive Tiles
            for(int i = 0; i < iTile[1].length; i++){
                if(iTile[currMap][i] != null){
                    iTile[currMap][i].update();
                }
            }
            envManager.update();
        }
        if(gameState == pauseState){
            //do not update player information
        }
    }

    public void drawToTempScreen(){


        //Debug
        long drawStart = 0;
        if(keyHandler.showdebugText){
            drawStart = System.nanoTime();
        }


        //Title screen
        if(gameState == titleState){
            ui.draw(g2);
        }
        else if(gameState == mapState){
            map.drawFullMapScreen(g2);
        }
        //Others
        else{

            //Tile
            tileManager.draw(g2);

            //Interactive Tile
            for(int i = 0; i < iTile[1].length; i++){
                if(iTile[currMap][i] != null){
                    iTile[currMap][i].draw(g2);
                }
            }

            //Adding entities to list
            //player
            entityList.add(player);
            //NPCs
            for(int i = 0; i < npc[1].length; i++){
                if(npc[currMap][i] != null){
                    entityList.add(npc[currMap][i]);}
            }

            //Objects               currMap
            for(int i = 0; i < obj[1].length; i++){
                if(obj[currMap][i] != null){
                    entityList.add(obj[currMap][i]);
                }
            }
            //Monsters
            for(int i = 0; i < monster[1].length; i++){
                if(monster[currMap][i] != null){
                    entityList.add(monster[currMap][i]);}
            }

            //Particle
            for(int i = 0; i < particleList.size(); i++){
                if(particleList.get(i) != null){
                    entityList.add(particleList.get(i));
                }
            }

            //Projectile
            for(int i = 0; i < projectile[1].length; i++){
                if(projectile[currMap][i] != null){
                    entityList.add(projectile[currMap][i]);}
            }


            //Sort
            Collections.sort(entityList, new Comparator<Entity>() {
                @Override
                public int compare(Entity e1, Entity e2) {

                    int result = Integer.compare(e1.worldY, e2.worldY);
                    return result;
                }
            });

            //Draw Entities
            for(int i = 0; i < entityList.size(); i++){
                entityList.get(i).draw(g2);
            }

            //Empty EntityList
            entityList.clear();

            //Env
            envManager.draw(g2);

            //mini map
            map.drawMiniMap(g2);

            //ui
            ui.draw(g2);
        }

        //DEBUG
        if(keyHandler.showdebugText) {
            long drawEnd = System.nanoTime();
            long passed = drawEnd - drawStart;

            g2.setFont(new Font("Arial", Font.PLAIN, 20));
            g2.setColor(Color.white);
            int x = 10;
            int y = 400;
            int lineHeight = 20;

            g2.drawString("WorldX " + player.worldX + ", Col " + (player.worldX + player.solidArea.x) / tileSize, x, y);
            g2.drawString("WorldY " + player.worldY + ", Row " + (player.worldY + player.solidArea.y) / tileSize, x, y + lineHeight);
            g2.drawString("Draw Time: " + passed, 10, 400 + lineHeight * 2);
            //System.out.println("Draw Time: " + passed);
        }
    }

    public void drawToScreen(){

        Graphics g = getGraphics();
        g.drawImage(tempScreen, 0, 0, screenWidth2, screenHeight2, null);
        g.dispose();
    }

//    public void paintComponent(Graphics g){
//        super.paintComponent(g);
//        Graphics2D g2 = (Graphics2D) g;
//
//        //Debug
//        long drawStart = 0;
//        if(keyHandler.showdebugText){
//            drawStart = System.nanoTime();
//        }
//
//
//        //Title screen
//        if(gameState == titleState){
//            ui.draw(g2);
//
//        }
//        //Others
//        else{
//
//        //Tile
//        tileManager.draw(g2);
//
//        //Interactive Tile
//        for(int i = 0; i < iTile.length; i++){
//            if(iTile[i] != null){
//                iTile[i].draw(g2);
//            }
//        }
//
//        //Adding entities to list
//            //player
//        entityList.add(player);
//            //NPCs
//        for(int i = 0; i < npc.length; i++){
//            if(npc[i] != null){
//                entityList.add(npc[i]);}
//        }
//
//            //Objects
//        for(int i = 0; i < obj.length; i++){
//            if(obj[i] != null){
//                entityList.add(obj[i]);}
//        }
//            //Monsters
//            for(int i = 0; i < monster.length; i++){
//                if(monster[i] != null){
//                    entityList.add(monster[i]);}
//            }
//
//            //Particle
//            for(int i = 0; i < particleList.size(); i++){
//                if(particleList.get(i) != null){
//                    entityList.add(particleList.get(i));
//                }
//            }
//
//            //Projectile
//            for(int i = 0; i < projectileList.size(); i++){
//                if(projectileList.get(i) != null){
//                    entityList.add(projectileList.get(i));}
//            }
//
//
//        //Sort
//        Collections.sort(entityList, new Comparator<Entity>() {
//            @Override
//            public int compare(Entity e1, Entity e2) {
//
//                int result = Integer.compare(e1.worldY, e2.worldY);
//                return result;
//            }
//        });
//
//        //Draw Entities
//        for(int i = 0; i < entityList.size(); i++){
//            entityList.get(i).draw(g2);
//        }
//
//        //Empty EntityList
//        entityList.clear();
//
//
//            //ui
//        ui.draw(g2);
//        }
//
//        //DEBUG
//        if(keyHandler.showdebugText) {
//            long drawEnd = System.nanoTime();
//            long passed = drawEnd - drawStart;
//
//            g2.setFont(new Font("Arial", Font.PLAIN, 20));
//            g2.setColor(Color.white);
//            int x = 10;
//            int y = 400;
//            int lineHeight = 20;
//
//            g2.drawString("WorldX " + player.worldX + ", Col " + (player.worldX + player.solidArea.x) / tileSize, x, y);
//            g2.drawString("WorldY " + player.worldY + ", Row " + (player.worldY + player.solidArea.y) / tileSize, x, y + lineHeight);
//            g2.drawString("Draw Time: " + passed, 10, 400 + lineHeight * 2);
//            //System.out.println("Draw Time: " + passed);
//        }
//        g2.dispose();
//    }

    public void playMusic(int i){
        music.setFile(i);
        music.play();
        music.loop();

    }

    public void stopMusic(){music.stop();}


    public void playSE(int i){
        soundEffect.setFile(i);
        soundEffect.play();
    }

    public void changeArea(){

        if(nextArea != currArea){

            stopMusic();

            switch(nextArea){
                case outside: playMusic(0); break;
                case dungeon: playMusic(16); break;
                case inside: playMusic(15); break;
            }
            assetSetter.setRocks();
        }

        currArea = nextArea;
        //assetSetter.setMonster();
        gameState = playState;
    }

}
