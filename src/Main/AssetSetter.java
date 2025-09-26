package Main;

import Entity.Entity;
import Entity.Merchant;
import Entity.OldMan;
import InteractiveTile.DryTree;
import InteractiveTile.MetalPlate;
import InteractiveTile.Tent;
import Monster.MON_GreenSlime;
import Monster.MON_SkeletonBoss;
import Monster.MON_ORC;
import Monster.MON_Bat;
import Object.OBJ_Key;
import Object.OBJ_Door;
import Object.OBJ_Chest;
import Object.OBJ_Axe;
import Object.OBJ_Shield_Blue;
import Object.OBJ_Potion_Red;
import Object.OBJ_Coin_Bronze;
import Object.OBJ_ManaCrystal;
import Object.OBJ_Heart;
import Object.OBJ_Lantern;
import Object.OBJ_Pickaxe;
import InteractiveTile.Wall;
import Object.OBJ_Iron_Door;
import Entity.BigRock;

import java.util.Arrays;
import java.util.Random;

public class AssetSetter {

    GamePanel gamePanel;
    public int[][][] nonPlaceables;//sets x and y coords that are invalid for a randomly selected tile spawn
    public int nonPlaceableIndex = 0;

    public AssetSetter(GamePanel gamePanel){
        this.gamePanel = gamePanel;
        nonPlaceables = new int[gamePanel.maxMap][100][];
        setNonPlaceables();
    }

    public void setObject(){

        int mapNum = 0;

        //create 3 coins randomly
        for(int i = 0; i < 3; i++){
            gamePanel.obj[mapNum][i] = new OBJ_Coin_Bronze(gamePanel);
            int [] valWorldCoord = randTile(mapNum, gamePanel.obj[mapNum][i]);
            gamePanel.obj[mapNum][i].worldX = valWorldCoord[0] * gamePanel.tileSize;
            gamePanel.obj[mapNum][i].worldY = valWorldCoord[1] * gamePanel.tileSize;
        }//obj index is as 2 currently

        //Create an axe in a random location
        gamePanel.obj[mapNum][3] = new OBJ_Axe(gamePanel);
        int[] valWorldCoord = randTile(mapNum, gamePanel.obj[mapNum][3]);
        gamePanel.obj[mapNum][3].worldX = valWorldCoord[0] * gamePanel.tileSize;
        gamePanel.obj[mapNum][3].worldY = valWorldCoord[1] * gamePanel.tileSize;
        //curr obj index is 3

        //Create a new shield (Blue)
        gamePanel.obj[mapNum][4] = new OBJ_Shield_Blue(gamePanel);
        valWorldCoord = randTile(mapNum, gamePanel.obj[mapNum][4]);
        gamePanel.obj[mapNum][4].worldX = valWorldCoord[0] * gamePanel.tileSize;
        gamePanel.obj[mapNum][4].worldY = valWorldCoord[0] * gamePanel.tileSize;
        //cur OBJ index is 4

        //Create a healing potion randomly
        gamePanel.obj[mapNum][5] = new OBJ_Potion_Red(gamePanel);
        valWorldCoord = randTile(mapNum, gamePanel.obj[mapNum][5]);
        gamePanel.obj[mapNum][5].worldX = valWorldCoord[0] * gamePanel.tileSize;
        gamePanel.obj[mapNum][5].worldY = valWorldCoord[1] * gamePanel.tileSize;
        //Cur obj index is 5

        //Create 3 mana crystals randomly
        for(int i = 0; i < 3; i++){
            gamePanel.obj[mapNum][i + 6] = new OBJ_ManaCrystal(gamePanel);
            valWorldCoord = randTile(mapNum, gamePanel.obj[mapNum][i + 6]);

            gamePanel.obj[mapNum][i + 6].worldX = valWorldCoord[0] * gamePanel.tileSize;
            gamePanel.obj[mapNum][i + 6].worldY = valWorldCoord[1] * gamePanel.tileSize;
        }
        //cur obj index is 8

        //Create 3 hearts randomly
        for(int i = 0; i < 3; i++){
            gamePanel.obj[mapNum][i + 9] = new OBJ_Heart(gamePanel);
            valWorldCoord = randTile(mapNum, gamePanel.obj[mapNum][i + 9]);

            gamePanel.obj[mapNum][i + 9].worldX = valWorldCoord[0] * gamePanel.tileSize;
            gamePanel.obj[mapNum][i + 9].worldY = valWorldCoord[1] * gamePanel.tileSize;
        }//cur obj index is 11

        //leave space for the doors (called from a diff class as well
        if(gamePanel.doorsStartup){setDoors();gamePanel.doorsStartup = false;}

        //Pass a loot item for the chest
        gamePanel.obj[mapNum][14] = new OBJ_Chest(gamePanel);
        gamePanel.obj[mapNum][14].setLoot(new OBJ_Key(gamePanel));
        gamePanel.obj[mapNum][14].worldX = gamePanel.tileSize * 30;
        gamePanel.obj[mapNum][14].worldY = gamePanel.tileSize * 27;

        gamePanel.obj[mapNum][15] = new OBJ_Potion_Red(gamePanel);
        valWorldCoord = randTile(mapNum, gamePanel.obj[mapNum][15]);
        gamePanel.obj[mapNum][15].worldX = valWorldCoord[0] * gamePanel.tileSize;
        gamePanel.obj[mapNum][15].worldY = valWorldCoord[1] * gamePanel.tileSize;

        //create a lantern
        gamePanel.obj[mapNum][16] = new OBJ_Lantern(gamePanel);
        valWorldCoord = randTile(mapNum, gamePanel.obj[mapNum][16]);
        gamePanel.obj[mapNum][16].worldX = valWorldCoord[0] * gamePanel.tileSize;
        gamePanel.obj[mapNum][16].worldY = valWorldCoord[1] * gamePanel.tileSize;

        mapNum = 1;

        mapNum = 2;

        mapNum = 3;
        int i = 0;
        gamePanel.obj[mapNum][i] = new OBJ_Chest(gamePanel);
        gamePanel.obj[mapNum][i].setLoot(new OBJ_Pickaxe(gamePanel));
        gamePanel.obj[mapNum][i].worldX = gamePanel.tileSize * 40;
        gamePanel.obj[mapNum][i].worldY = gamePanel.tileSize * 41;
        i++;

        //add an iron door
        gamePanel.obj[mapNum][i] = new OBJ_Iron_Door(gamePanel);
        gamePanel.obj[mapNum][i].worldX = gamePanel.tileSize * 18;
        gamePanel.obj[mapNum][i].worldY = gamePanel.tileSize * 22;
        gamePanel.obj[mapNum][i].openOnRockPuzzle = true;
        i++;

    }
    public void setDoors(){
        int mapNum = 0;

        gamePanel.obj[mapNum][12] = new OBJ_Door(gamePanel);
        gamePanel.obj[mapNum][12].worldX = gamePanel.tileSize * 14;
        gamePanel.obj[mapNum][12].worldY = gamePanel.tileSize * 28;

        gamePanel.obj[mapNum][13] = new OBJ_Door(gamePanel);
        gamePanel.obj[mapNum][13].worldX = gamePanel.tileSize * 12;
        gamePanel.obj[mapNum][13].worldY = gamePanel.tileSize * 12;

    }

    public void setNonPlaceables(){
        int mapNum = 0;
        updatePlaceables(27, 27, mapNum);
        updatePlaceables(30, 28, mapNum);
        updatePlaceables(30, 29, mapNum);
        updatePlaceables(13, 39, mapNum);
        updatePlaceables(13, 37, mapNum);
        updatePlaceables(24, 25, mapNum);
    }

    public void setNCP(){

        int mapNum = 0;

        gamePanel.npc[mapNum][0] = new OldMan(gamePanel);
        gamePanel.npc[mapNum][0].worldX = gamePanel.tileSize * 21;
        gamePanel.npc[mapNum][0].worldY = gamePanel.tileSize * 21;
        System.out.println("OldMan set");
        System.out.println("setting location");

        //Set assets for map 1
        mapNum = 1;

        gamePanel.npc[mapNum][0] = new Merchant(gamePanel);
        gamePanel.npc[mapNum][0].worldX = gamePanel.tileSize * 12;
        gamePanel.npc[mapNum][0].worldY = gamePanel.tileSize * 7;
        System.out.println("AssetSetter - Merchant created with name: " + gamePanel.npc[mapNum][0].name);

        mapNum = 2;

        mapNum = 3;
        setRocks();
    }
    public void setRocks(){
        int mapNum = 3;
        gamePanel.npc[mapNum][0] = new BigRock(gamePanel);
        gamePanel.npc[mapNum][0].worldX = gamePanel.tileSize * 20;
        gamePanel.npc[mapNum][0].worldY = gamePanel.tileSize * 25;

        gamePanel.npc[mapNum][1] = new BigRock(gamePanel);
        gamePanel.npc[mapNum][1].worldX = gamePanel.tileSize * 11;
        gamePanel.npc[mapNum][1].worldY = gamePanel.tileSize * 18;

        gamePanel.npc[mapNum][2] = new BigRock(gamePanel);
        gamePanel.npc[mapNum][2].worldX = gamePanel.tileSize * 23;
        gamePanel.npc[mapNum][2].worldY = gamePanel.tileSize * 14;

    }

    public void setMonster(){

        int mapNum = 0;

        gamePanel.monster[mapNum][0] = new MON_GreenSlime(gamePanel);
        gamePanel.monster[mapNum][0].worldX = gamePanel.tileSize * 23;
        gamePanel.monster[mapNum][0].worldY = gamePanel.tileSize * 36;

        gamePanel.monster[mapNum][1] = new MON_GreenSlime(gamePanel);
        gamePanel.monster[mapNum][1].worldX = gamePanel.tileSize * 23;
        gamePanel.monster[mapNum][1].worldY = gamePanel.tileSize * 37;

        //Create and place 3 slimes randomly
        for(int i = 0; i < 3; i++){
            gamePanel.monster[mapNum][i + 2] = new MON_GreenSlime(gamePanel);
            int[] valWorldCoord = randTile(mapNum, gamePanel.monster[mapNum][i + 2]);
            //index [0] is x coord & index [1] is y coord
            gamePanel.monster[mapNum][i + 2].worldX = valWorldCoord[0] * gamePanel.tileSize;
            gamePanel.monster[mapNum][i + 2].worldY = valWorldCoord[1] * gamePanel.tileSize;
        }
        //curr index is at 4

        //create 2 orcs randomly
        for(int i = 0; i < 2; i++){
            gamePanel.monster[mapNum][i + 4] = new MON_ORC(gamePanel);
            int[] valWorldCoord = randTile(mapNum, gamePanel.monster[mapNum][i + 2]);
            gamePanel.monster[mapNum][i + 4].worldX = valWorldCoord[0] * gamePanel.tileSize;
            gamePanel.monster[mapNum][i + 4].worldY = valWorldCoord[1] * gamePanel.tileSize;
        }
        //curr index is at 6

        mapNum = 1;

        mapNum = 2;

        mapNum = 3;
        //create 3 bats randomly
        for(int i = 0; i < 3; i++){
            gamePanel.monster[mapNum][i] = new MON_Bat(gamePanel);
            int[] valWorldCoord = randTile(mapNum, gamePanel.monster[mapNum][i]);
            gamePanel.monster[mapNum][i].worldX = valWorldCoord[0] * gamePanel.tileSize;
            gamePanel.monster[mapNum][i].worldY = valWorldCoord[1] * gamePanel.tileSize;
            System.out.println("bat at " + valWorldCoord[0] + " " + valWorldCoord[1]);
        }

        mapNum = 4;
        gamePanel.monster[mapNum][0] = new MON_SkeletonBoss(gamePanel);
        gamePanel.monster[mapNum][0].worldX = gamePanel.tileSize * 23;
        gamePanel.monster[mapNum][0].worldY = gamePanel.tileSize * 16;
    }

    public void setInteractiveTile(){

        int mapNum = 0;
        int i = 0;

        gamePanel.iTile[mapNum][i] = new DryTree(gamePanel, 29, 12);i++;updatePlaceables(29, 12, mapNum);
        gamePanel.iTile[mapNum][i] = new DryTree(gamePanel, 30, 12);i++;updatePlaceables(30, 12, mapNum);
        gamePanel.iTile[mapNum][i] = new DryTree(gamePanel, 31, 12);i++;updatePlaceables(31, 12, mapNum);
        gamePanel.iTile[mapNum][i] = new DryTree(gamePanel, 29, 21);i++;updatePlaceables(29, 21, mapNum);
        gamePanel.iTile[mapNum][i] = new DryTree(gamePanel, 29, 20);i++;updatePlaceables(29, 29, mapNum);
        gamePanel.iTile[mapNum][i] = new DryTree(gamePanel, 29, 22);i++;updatePlaceables(29, 22, mapNum);
        gamePanel.iTile[mapNum][i] = new DryTree(gamePanel, 10, 40);i++;updatePlaceables(10, 40, mapNum);
        gamePanel.iTile[mapNum][i] = new DryTree(gamePanel, 10, 41);i++;updatePlaceables(10, 41, mapNum);
        gamePanel.iTile[mapNum][i] = new DryTree(gamePanel, 11, 41);i++;updatePlaceables(11, 41, mapNum);
        gamePanel.iTile[mapNum][i] = new DryTree(gamePanel, 12, 41);i++;updatePlaceables(12, 41, mapNum);
        gamePanel.iTile[mapNum][i] = new DryTree(gamePanel, 13, 41);i++;updatePlaceables(13, 41, mapNum);
        gamePanel.iTile[mapNum][i] = new DryTree(gamePanel, 13, 40);i++;updatePlaceables(13, 40, mapNum);
        gamePanel.iTile[mapNum][i] = new DryTree(gamePanel, 14, 40);i++;updatePlaceables(14, 40, mapNum);
        gamePanel.iTile[mapNum][i] = new DryTree(gamePanel, 15, 40);i++;updatePlaceables(15, 40, mapNum);
        gamePanel.iTile[mapNum][i] = new DryTree(gamePanel, 16, 40);i++;updatePlaceables(16, 40, mapNum);
        gamePanel.iTile[mapNum][i] = new DryTree(gamePanel, 17, 40);i++;updatePlaceables(17, 40, mapNum);
        gamePanel.iTile[mapNum][i] = new DryTree(gamePanel, 18, 40);gamePanel.iTile[mapNum][i].invincible = false;i++;updatePlaceables(18, 40, mapNum);
//        for(int j = 0; j < 6; j++){
//            gamePanel.iTile[mapNum][i + j] = new DryTree(gamePanel, 13 + j, 40);updatePlaceables(13 + j, 40, mapNum);
//        }
//        i += 6;

        gamePanel.iTile[mapNum][i] = new DryTree(gamePanel, 25, 27);i++;updatePlaceables(25, 27, mapNum);
        gamePanel.iTile[mapNum][i] = new DryTree(gamePanel, 26, 27);i++;updatePlaceables(26, 27, mapNum);
//        gamePanel.iTile[mapNum][i] = new DryTree(gamePanel, 27, 27);i++;updatePlaceables(27, 27, mapNum);
        gamePanel.iTile[mapNum][i] = new DryTree(gamePanel, 27, 28);i++;updatePlaceables(27, 28, mapNum);
        gamePanel.iTile[mapNum][i] = new DryTree(gamePanel, 27, 29);i++;updatePlaceables(27, 29, mapNum);
        gamePanel.iTile[mapNum][i] = new DryTree(gamePanel, 28, 29);i++;updatePlaceables(28, 29, mapNum);
        gamePanel.iTile[mapNum][i] = new DryTree(gamePanel, 29, 29);i++;updatePlaceables(29, 29, mapNum);

        gamePanel.iTile[mapNum][i] = new DryTree(gamePanel, 13, 39);i++;updatePlaceables(13, 39, mapNum);
        gamePanel.iTile[mapNum][i] = new DryTree(gamePanel, 13, 38);i++;updatePlaceables(13, 38, mapNum);


        gamePanel.iTile[mapNum][i] = new Tent(gamePanel, 13, 37);i++;updatePlaceables(13, 37, mapNum);

        mapNum = 1;
        mapNum = 2;
        mapNum = 3;
        i = 0;
        gamePanel.iTile[mapNum][i] = new Wall(gamePanel, 18, 23);i++;updatePlaceables(18, 23, mapNum);

        gamePanel.iTile[mapNum][i] = new Wall(gamePanel, 12, 26);i++;updatePlaceables(12, 26, mapNum);
        gamePanel.iTile[mapNum][i] = new Wall(gamePanel, 12, 27);i++;updatePlaceables(12, 27, mapNum);
        gamePanel.iTile[mapNum][i] = new Wall(gamePanel, 12, 28);i++;updatePlaceables(12, 28, mapNum);

        gamePanel.iTile[mapNum][i] = new Wall(gamePanel, 18, 17);i++;updatePlaceables(18, 17, mapNum);

        gamePanel.iTile[mapNum][i] = new Wall(gamePanel, 25, 28);i++;updatePlaceables(25, 28, mapNum);
        gamePanel.iTile[mapNum][i] = new Wall(gamePanel, 26, 28);i++;updatePlaceables(26, 28, mapNum);
        gamePanel.iTile[mapNum][i] = new Wall(gamePanel, 27, 28);i++;updatePlaceables(27, 28, mapNum);

        gamePanel.iTile[mapNum][i] = new MetalPlate(gamePanel, 20, 22);i++;updatePlaceables(20, 21, mapNum);
        gamePanel.iTile[mapNum][i] = new MetalPlate(gamePanel, 8, 17);i++;updatePlaceables(8, 17, mapNum);
        gamePanel.iTile[mapNum][i] = new MetalPlate(gamePanel, 39, 31);i++;updatePlaceables(39, 31, mapNum);

    }

    public void updatePlaceables(int worldX, int worldY, int mapNum){
        nonPlaceables[mapNum][nonPlaceableIndex] = new int[]{worldX, worldY};
        nonPlaceableIndex++;
    }

    // Return a random tile that is a spawnable tile
    public int[] randTile(int mapNum, Entity item) {
        Random rand = new Random();
        int maxAttempts = 500; // Prevent infinite loop
        int attempts = 0;

        while (attempts < maxAttempts) {
            attempts++;

            // Generate random coordinates within safe bounds
            int worldX = rand.nextInt(6, gamePanel.maxWorldCol - 6);
            int worldY = rand.nextInt(6, gamePanel.maxWorldRow - 6);

            int[] currCoords = {worldX, worldY};

            // Get the tile number at these coordinates
            int tileNum = gamePanel.tileManager.mapTileNum[mapNum][worldX][worldY];

            // Check if tile IS in the allowed list (10, 11, 41, 26-39)
            boolean isValidTile = isAllowedTile(tileNum);

            // Check if coordinates are not in nonPlaceables
            boolean isPlaceable = true;

            for (int i = 0; i < nonPlaceables[mapNum].length; i++) {
                if (nonPlaceables[mapNum][i] != null && Arrays.equals(nonPlaceables[mapNum][i], currCoords)) {
                        isPlaceable = false;
                        break;
                }
            }

            // Additional check: ensure tile is not collidable (optional)
            boolean isNotCollidable = true;
            if (tileNum >= 0 && tileNum < gamePanel.tileManager.tile.length && gamePanel.tileManager.tile[tileNum] != null) {
                isNotCollidable = !gamePanel.tileManager.tile[tileNum].collision;
            }

            if (isValidTile && isPlaceable && isNotCollidable) {
                updatePlaceables(worldX, worldY, mapNum);
                System.out.println("Valid spawn at: " + worldX + " " + worldY +
                        ", tile: " + tileNum + " for " + item.name);
                return new int[]{worldX, worldY};
            }
        }

        // Fallback: return invalid coordinates if no valid tile found
        System.out.println("ERROR: Could not find valid spawn location for " + item.name);
        return new int[]{-1, -1};
    }

    private boolean isAllowedTile(int tileNum) {
        // Check specific allowed tile numbers
        if (tileNum == 10 || tileNum == 11 || tileNum == 41 || tileNum == 49) {
            return true;
        }

        // Check range 26-39 (inclusive)
        if (tileNum >= 26 && tileNum <= 39 && tileNum > 9) {
            return true;
        }

        // All other tiles are not allowed
        return false;
    }

    //old method
    //Return a random tile that is a spawnable tile
//    public int[] randTile(int mapNum, Entity item){
//
//        Random xRand = new Random();
//        Random yRand = new Random();
//        int worldX;
//        int worldY;
//        int[] valCoords = new int[2];
//
//        boolean validCoord = false;
//        boolean validPlaceable;
//
//        while(validCoord != true){
//            validPlaceable = true;
//            //Gen random x coord within world limits
//            worldX = xRand.nextInt(6, gamePanel.maxWorldCol - 6);
//            worldY = yRand.nextInt(6, gamePanel.maxWorldRow - 6);
//            //check that tile is spawnable
//            int tileNum = gamePanel.tileManager.mapTileNum[gamePanel.currMap][worldX - 1][worldY];
//            int[] currCoords = {worldX, worldY};
//
//            //check nonPlacable list as well
//            for(int i = 0; i < nonPlaceables[mapNum].length; i++){
//                if(nonPlaceables[mapNum][i] != null){
//                    //if(nonPlaceables[mapNum][i] == currCoords){validPlaceable = false;}//these coords will not work
//                    if(Arrays.equals(nonPlaceables[mapNum][i], currCoords)){validPlaceable = false;}
//                }
//            }
//            //need to consider trees as well
//            //if(tileNum == 10 || tileNum == 11 || (tileNum >= 26 && tileNum <= 39))
//            if((tileNum == 10 || tileNum == 11 || (tileNum >= 26 && tileNum <= 39) || tileNum != 41) || tileNum != 50){
//                if(validPlaceable) {
//                    validCoord = true;
//                    valCoords[0] = worldX - 1;
//                    valCoords[1] = worldY;
//                    updatePlaceables(worldX, worldY, mapNum);
//                    System.out.print(worldX + " " + worldY + ", " + tileNum + " " + item.name);
//                    return valCoords;
//                }
//            }
//        }
//        return valCoords;
//    }

}
