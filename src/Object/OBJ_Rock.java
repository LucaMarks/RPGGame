package Object;

import Entity.Entity;
import Entity.Projectile;
import Main.GamePanel;

import java.awt.*;

public class OBJ_Rock extends Projectile {

    GamePanel gp;

    public static final String objName = "Rock";

    public OBJ_Rock(GamePanel gp){
        super(gp);
        this.gp = gp;

        name = objName;
        speed = 8;
        maxLife = 50;
        life = maxLife;
        attack = 1;
        useCost = 2;
        alive = false;

        getImage();

    }

    public void getImage(){
        up1 = setup("/Projectile/rock_down_1", gp.tileSize, gp.tileSize);
        up2 =setup("/Projectile/rock_down_1", gp.tileSize, gp.tileSize);
        right1 = setup("/Projectile/rock_down_1", gp.tileSize, gp.tileSize);
        right2 = setup("/Projectile/rock_down_1", gp.tileSize, gp.tileSize);
        left1 = setup("/Projectile/rock_down_1", gp.tileSize, gp.tileSize);
        left2 = setup("/Projectile/rock_down_1", gp.tileSize, gp.tileSize);
        down1 = setup("/Projectile/rock_down_1", gp.tileSize, gp.tileSize);
        down2 = setup("/Projectile/rock_down_1", gp.tileSize, gp.tileSize);
    }

    public boolean hasResource(Entity user){
        boolean hasResource = false;
        if(user.ammo >= useCost){hasResource = true;}

        return hasResource;
    }

    public void subtractResource(Entity user){user.ammo -= useCost;}

    public Color getParticleColor(){
        Color color = new Color(40, 50, 0);
        return color;
    }
    public int getParticleSize(){
        int size = 10;
        return size;
    }
    public int getParticleSpeed(){
        int speed = 1;
        return speed;
    }

    public int getParticleMaxLife(){return 20;}

    public OBJ_Rock createNew(GamePanel gp){
        return new OBJ_Rock(gp);
    }

}
