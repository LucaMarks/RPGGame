package Entity;

import Main.GamePanel;

public class Projectile extends Entity{

    Entity user;

    public Projectile(GamePanel gp){
        super(gp);
    }

    public void set(int worldX, int worldY, String direction, boolean alive, Entity user){

        this.worldX = worldX;
        this.worldY = worldY;
        this.direction = direction;
        this.alive = alive;
        this.user = user;
        //Reset life to max everytime we want to use fireball
        this.life = this.maxLife;
    }

    public void update(){

        if(user == gamePanel.player){
            int monsterIndex = gamePanel.collisionChecker.checkEntity(this, gamePanel.monster);
            if(monsterIndex != 999){
                gamePanel.player.damageMonster(monsterIndex, attack, knockBackValue, this);
                generateParticle(user.projectile, gamePanel.monster[gamePanel.currMap][monsterIndex]);
                alive = false;
            }
        }
        else if(user != gamePanel.player){
            boolean contactPlayer = gamePanel.collisionChecker.checkPlayer(this);

            if(gamePanel.player.invincible == false && contactPlayer == true){
                damagePlayer(attack);alive = false;
                generateParticle(user.projectile, user.projectile);
            }
        }

        if(spriteCounter % 2 == 0) {
            switch (direction) {
                case "up": worldY -= speed;break;//speed
                case "down": worldY += speed;break;
                case "right": worldX += speed;break;
                case "left": worldX -= speed;break;
            }
        }

        if(alive){life --;}
        if(life <= 0){alive = false;}

        spriteCounter++;
        if(spriteCounter > 12){

            if(spriteNumber == 1){spriteNumber = 2;}
            else if(spriteNumber == 2){spriteNumber = 1;}
        }
        spriteCounter = 0;

    }

    //Method is always overridden
    public boolean hasResource(Entity user){
        boolean hasResource = false;
        //if(user.mana >= useCost){hasResource = true;}

        return hasResource;
    }

    public void subtractResource(Entity user){user.mana -= useCost;}
}