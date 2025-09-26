package InteractiveTile;

import Entity.Entity;
import Main.GamePanel;

import java.awt.*;

public class InteractiveTile extends Entity {

    public boolean destructable = false;
    public int correctItemType;

    public InteractiveTile(GamePanel gp){
        super(gp);
    }

    public boolean isCorrectItem(Entity e){
        boolean isCorrectItem = false;

        if(e.currentWeapon.type == correctItemType){
            isCorrectItem = true;
        }

        return isCorrectItem;
    }

    public void playSE(){}

    public InteractiveTile getDestroyedForm(){
        InteractiveTile tile = null;

        return tile;
    }

    public void update(){
        if(invincible){
            invincibleCounter++;
            if(invincibleCounter > 20){invincible = false;invincibleCounter = 0;}
        }
    }

    public Color getParticleColor(){return null;}
    public int getParticleSize(){return 0;}
    public int getParticleSpeed(){return 0;}
    public int getParticleMaxLife(){return 0;}

}
