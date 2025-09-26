package Object;

import Entity.Entity;
import Entity.Projectile;
import Main.GamePanel;

import java.awt.*;


//Pipeline: Entity -> Projectile -> OBJ_Fireball
//Projectile needs to have all the classes we will use in OBJ_Fireball, however Entity does not

public class OBJ_Fireball extends Projectile {

    public static final String objName = "Fireball";

    GamePanel gp;

    public OBJ_Fireball(GamePanel gp){
        super(gp);
        this.gp = gp;

        name = objName;
        speed = 5;
        maxLife = 80;
        life = maxLife;
        attack = 3;
        useCost = 1;
        alive = false;
        getImage();
        knockBackValue = 0;

    }

    public void getImage(){
        up1 = setup("/Projectile/fireball_up_1", gp.tileSize, gp.tileSize);
        up1 = setup("/Projectile/fireball_up_1", gp.tileSize, gp.tileSize);
        down1 = setup("/Projectile/fireball_down_1", gp.tileSize, gp.tileSize);
        down2 = setup("/Projectile/fireball_down_2", gp.tileSize, gp.tileSize);
        left1 = setup("/Projectile/fireball_left_1", gp.tileSize, gp.tileSize);
        left2 = setup("/Projectile/fireball_left_2", gp.tileSize, gp.tileSize);
        right1 = setup("/Projectile/fireball_right_1", gp.tileSize, gp.tileSize);
        right2 = setup("/Projectile/fireball_right_2", gp.tileSize, gp.tileSize);
    }

    public boolean hasResource(Entity user){
        boolean hasResource = false;
        if(user.mana >= useCost){hasResource = true;}

        return hasResource;
    }

    public void subtractResource(Entity user){user.mana -= useCost;}

    public Color getParticleColor(){
        Color color = new Color(240, 50, 0);
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

    public OBJ_Fireball createNew(GamePanel gp){
        return new OBJ_Fireball(gp);
    }

}
