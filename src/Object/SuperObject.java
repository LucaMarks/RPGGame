package Object;

import Main.GamePanel;
import Main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class SuperObject {

    public BufferedImage image, image2, image3;
    public String name;
    public boolean Collision = false;
    public int worldX, worldY;
    public Rectangle solidArea = new Rectangle(0,0, 48, 48);
    public int solidAreaDefaultX = 0;
    public int solidAreaDefaultY = 0;
    UtilityTool uTool = new UtilityTool();//dfvgbhjkjhokfdsjklsdfjk fuckk nissswea
    GamePanel gp;

    public SuperObject(){}

    public SuperObject(GamePanel gp){
        this.gp = gp;
    }

    public void draw(Graphics2D g2, GamePanel gamePanel){

        int screenX = worldX - gamePanel.player.worldX + gamePanel.player.screenX;
        int screenY = worldY - gamePanel.player.worldY + gamePanel.player.screenY;

        if(worldX + gamePanel.tileSize > gamePanel.player.worldX - gamePanel.player.screenX && worldX - gamePanel.tileSize < gamePanel.player.worldX + gamePanel.player.screenX
                && worldY + gamePanel.tileSize > gamePanel.player.worldY - gamePanel.player.screenY && worldY - gamePanel.tileSize < gamePanel.player.worldY + gamePanel.player.screenY) {
            g2.drawImage(image, screenX, screenY, gamePanel.tileSize, gamePanel.tileSize, null);
        }
    }

    public BufferedImage scaleImage(String name){
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/objects/" + name + ".png"));
            image = uTool.scaleImage(image, gp.tileSize, gp.tileSize);
            System.out.println("Loading: /objects/" + name + ".png");

        }catch(IOException e) {
                e.printStackTrace();}
        return image;
    }
}
