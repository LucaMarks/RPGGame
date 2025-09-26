package Tile;

import Main.GamePanel;
import Main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TileManager {

    GamePanel gamePanel;
    public Tile[] tile;
    public int mapTileNum[][][];
    boolean drawPath = true;

    public TileManager(GamePanel gp){
        gamePanel = gp;
        tile = new Tile[99];
        mapTileNum = new int[gp.maxMap][gp.maxWorldCol] [gp.maxWorldRow];

        getTileImage();
        //loadMap("/Maps/" + gp.mapName + ".txt");
        loadMap("/Maps/worldV3.txt", 0);
        loadMap("/Maps/interior01.txt", 1);
        loadMap("/Maps/tent.txt", 2);
        loadMap("/Maps/dungeon01.txt", 3);
        loadMap("/Maps/dungeon02.txt", 4);
    }

    public void getTileImage(){


            setupTiles(0, "grass", false);
            setupTiles(1, "mossyWall", false);
            setupTiles(2, "water", true);
            setupTiles(3, "earth", false);
            setupTiles(4, "tree", true);
            setupTiles(5, "earth", false);
            setupTiles(6, "earth", false);
            setupTiles(7, "earth", false);
            setupTiles(8, "earth", false);
            setupTiles(9, "earth", false);

            setupTiles(10, "grass00", false);//spwanable{
            setupTiles(11, "grass01", false);//spawnable}
            setupTiles(12, "water00", true);
            setupTiles(13, "water01", true);
            setupTiles(14, "water02", true);
            setupTiles(15, "water03", true);
            setupTiles(16, "water04", true);
            setupTiles(17, "water05", true);
            setupTiles(18, "water06", true);
            setupTiles(19, "water07", true);
            setupTiles(20, "water08", true);
            setupTiles(21, "water09", true);
            setupTiles(22, "water10", true);
            setupTiles(23, "water11", true);
            setupTiles(24, "water12", true);
            setupTiles(25, "water13", true);
            setupTiles(26, "road00", false);//Spawnable{
            setupTiles(27, "road01", false);
            setupTiles(28, "road02", false);
            setupTiles(29, "road03", false);
            setupTiles(30, "road04", false);
            setupTiles(31, "road05", false);
            setupTiles(32, "road06", false);
            setupTiles(33, "road07", false);
            setupTiles(34, "road08", false);
            setupTiles(35, "road09", false);
            setupTiles(36, "road10", false);
            setupTiles(37, "road11", false);
            setupTiles(38, "road12", false);
            setupTiles(39, "earth", false);//Spawnable}
            setupTiles(40, "mossyWall", true);
            setupTiles(41, "tree", true);
            setupTiles(42, "hut", false);
            setupTiles(43, "floor01", false);
            setupTiles(44, "table01", true);
            setupTiles(45, "tent", false);
            setupTiles(46, "black", true);
            setupTiles(47, "stairDown", false);
            setupTiles(48, "stairUp", false);
            setupTiles(49, "dirt", false);
            setupTiles(50, "stoneWall", true);




          /*  tile[1] = new Tile();
            tile[1].image = ImageIO.read(getClass().getResourceAsStream("/Tiles/mossyWall.png"));
            tile[1].collision = true;

            tile[2] = new Tile();
            tile[2].image = ImageIO.read(getClass().getResourceAsStream("/Tiles/water.png"));
            tile[2].collision = true;

            tile[3] = new Tile();
            tile[3].image = ImageIO.read(getClass().getResourceAsStream("/Tiles/earth.png"));

            tile[4] = new Tile();
            tile[4].image = ImageIO.read(getClass().getResourceAsStream("/Tiles/tree.png"));
            tile[4].collision = true;

            tile[5] = new Tile();
            tile[5].image = ImageIO.read(getClass().getResourceAsStream("/Tiles/sand.png"));*/
    }
    public void setupTiles(int indexNum, String name, boolean collisionOn){

        UtilityTool uTool = new UtilityTool();

        String path;
        path = "/Tiles/" + name + ".png";
        try {
            tile[indexNum] = new Tile();
            tile[indexNum].image = ImageIO.read(getClass().getResourceAsStream(path));
            tile[indexNum].image =  uTool.scaleImage(tile[indexNum].image, gamePanel.tileSize, gamePanel.tileSize);
            tile[indexNum].collision = collisionOn;
            //System.out.println("Index " + indexNum + " Successful");
        }catch(IOException e){e.printStackTrace();
        //System.out.println("wrong name");
        }
    }

    public void loadMap(String map, int mapNum){
        try{
            InputStream is = getClass().getResourceAsStream(map);
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));

            int col = 0;
            int row = 0;

            while(col < gamePanel.maxWorldCol && row < gamePanel.maxWorldRow){

                String line = reader.readLine();

                while(col < gamePanel.maxWorldCol){

                    String numbers[] = line.split(" ");
                    int num = Integer.parseInt(numbers[col]);
                    mapTileNum[mapNum][col][row] = num;
                    col++;
                }

                if(col == gamePanel.maxWorldCol){
                    col = 0;
                    row++;
                }
            }
            reader.close();
        }catch(Exception e){

        }
    }

    public void draw(Graphics2D g){

        int worldCol = 0;
        int worldRow = 0;


        while(worldCol < gamePanel.maxWorldCol && worldRow < gamePanel.maxWorldRow){

            int tileNum = mapTileNum[gamePanel.currMap][worldCol][worldRow];

            int worldX = worldCol * gamePanel.tileSize;
            int worldY = worldRow * gamePanel.tileSize;
            int screenX = worldX - gamePanel.player.worldX + gamePanel.player.screenX;
            int screenY = worldY - gamePanel.player.worldY + gamePanel.player.screenY;

            if(worldX + gamePanel.tileSize > gamePanel.player.worldX - gamePanel.player.screenX && worldX - gamePanel.tileSize < gamePanel.player.worldX + gamePanel.player.screenX
            && worldY + gamePanel.tileSize > gamePanel.player.worldY - gamePanel.player.screenY && worldY - gamePanel.tileSize < gamePanel.player.worldY + gamePanel.player.screenY) {
                g.drawImage(tile[tileNum].image, screenX, screenY, gamePanel.tileSize, gamePanel.tileSize, null);
            }
            worldCol++;


            if(worldCol == gamePanel.maxWorldCol){
                worldRow++;
                worldCol = 0;


            }
        }
        if (drawPath){
            g.setColor(new Color(255, 0, 0, 70));

            for(int i = 0; i < gamePanel.pathFinder.pathList.size(); i++){
                int worldX = gamePanel.pathFinder.pathList.get(i).col * gamePanel.tileSize;
                int worldY = gamePanel.pathFinder.pathList.get(i).row * gamePanel.tileSize;
                int screenX = worldX - gamePanel.player.worldX + gamePanel.player.screenX;
                int screenY = worldY - gamePanel.player.worldY + gamePanel.player.screenY;

                g.fillRect(screenX, screenY, gamePanel.tileSize, gamePanel.tileSize);
            }
        }

        /*g.drawImage(tile[1].image, 0, 0, gamePanel.tileSize, gamePanel.tileSize, null);
        g.drawImage(tile[1].image, gamePanel.tileSize, 0, gamePanel.tileSize, gamePanel.tileSize, null);
        g.drawImage(tile[1].image, 2 * gamePanel.tileSize, 0, gamePanel.tileSize, gamePanel.tileSize, null);
        g.drawImage(tile[1].image, 3*gamePanel.tileSize, 0, gamePanel.tileSize, gamePanel.tileSize, null);
        g.drawImage(tile[1].image, 4*gamePanel.tileSize, 0, gamePanel.tileSize, gamePanel.tileSize, null);

        g.drawImage(tile[1].image, 0, 1 *gamePanel.tileSize, gamePanel.tileSize, gamePanel.tileSize, null);
        g.drawImage(tile[0].image, 1 *gamePanel.tileSize, gamePanel.tileSize, gamePanel.tileSize, gamePanel.tileSize, null);
        g.drawImage(tile[0].image, 2 *gamePanel.tileSize, gamePanel.tileSize, gamePanel.tileSize, gamePanel.tileSize, null);
        g.drawImage(tile[0].image,  3 *gamePanel.tileSize, gamePanel.tileSize, gamePanel.tileSize, gamePanel.tileSize, null);
        g.drawImage(tile[1].image, 4 *gamePanel.tileSize, gamePanel.tileSize, gamePanel.tileSize, gamePanel.tileSize, null);

        g.drawImage(tile[1].image, 0, 2 * gamePanel.tileSize, gamePanel.tileSize, gamePanel.tileSize, null);
        g.drawImage(tile[0].image, gamePanel.tileSize, 2 * gamePanel.tileSize, gamePanel.tileSize, gamePanel.tileSize, null);
        g.drawImage(tile[0].image, 2 *gamePanel.tileSize, 2 * gamePanel.tileSize, gamePanel.tileSize, gamePanel.tileSize, null);
        g.drawImage(tile[0].image,  3 *gamePanel.tileSize, 2 * gamePanel.tileSize, gamePanel.tileSize, gamePanel.tileSize, null);
        g.drawImage(tile[0].image, 4 *gamePanel.tileSize, 2 * gamePanel.tileSize, gamePanel.tileSize, gamePanel.tileSize, null);

        g.drawImage(tile[1].image, 0, 3 *gamePanel.tileSize, gamePanel.tileSize, gamePanel.tileSize, null);
        g.drawImage(tile[0].image, 1*gamePanel.tileSize, 3 *gamePanel.tileSize, gamePanel.tileSize, gamePanel.tileSize, null);
        g.drawImage(tile[0].image, 2 *gamePanel.tileSize, 3 *gamePanel.tileSize, gamePanel.tileSize, gamePanel.tileSize, null);
        g.drawImage(tile[0].image,  3 *gamePanel.tileSize,3 * gamePanel.tileSize, gamePanel.tileSize, gamePanel.tileSize, null);
        g.drawImage(tile[1].image, 4 *gamePanel.tileSize, 3 *gamePanel.tileSize, gamePanel.tileSize, gamePanel.tileSize, null);

        g.drawImage(tile[1].image, 0, 3 * gamePanel.tileSize, gamePanel.tileSize, gamePanel.tileSize, null);
        g.drawImage(tile[2].image, gamePanel.tileSize, 3 * gamePanel.tileSize, gamePanel.tileSize, gamePanel.tileSize, null);
        g.drawImage(tile[2].image, 2 * gamePanel.tileSize, 3 * gamePanel.tileSize, gamePanel.tileSize, gamePanel.tileSize, null);
        g.drawImage(tile[2].image, 3*gamePanel.tileSize, 3 * gamePanel.tileSize, gamePanel.tileSize, gamePanel.tileSize, null);
        g.drawImage(tile[1].image, 4*gamePanel.tileSize, 3 * gamePanel.tileSize, gamePanel.tileSize, gamePanel.tileSize, null);

        g.drawImage(tile[0].image, 4 * gamePanel.tileSize, 2 * gamePanel.tileSize, gamePanel.tileSize, gamePanel.tileSize, null);
         */
    }

}
