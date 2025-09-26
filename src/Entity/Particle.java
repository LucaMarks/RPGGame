package Entity;

import Main.GamePanel;

import java.awt.*;

public class Particle extends Entity{

    Entity generator;
    Color color;
    int size;
    int xd;
    int yd;

    public Particle(GamePanel gp, Entity generator, Color color, int size, int speed, int maxLife, int xd, int yd){
        super(gp);

        this.generator = generator;
        this.color = color;
        this.xd = xd;
        this.yd = yd;
        this.size = size;
        this.speed = speed;
        this.maxLife = maxLife;

        life = maxLife;
        int offset = (gp.tileSize / 2) - (size / 2);
        worldX = generator.worldX + offset;
        worldY = generator.worldY + offset;

    }

    public void update(){

        life --;

        if(life < maxLife / 3){
            yd++;
        }

        if(life <= 0){alive = false;}
        worldX += xd * speed;
        worldY += yd * speed;
    }

    public void draw(Graphics2D g2){

        int screenX = worldX - gamePanel.player.worldX + gamePanel.player.screenX;
        int screenY = worldY - gamePanel.player.worldY + gamePanel.player.screenY;

        g2.setColor(color);
        g2.drawRect(screenX, screenY, size, size);
    }
}
