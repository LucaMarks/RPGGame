package Main;

import Entity.Entity;

public class EntityGenerator {

    GamePanel gp;

    public EntityGenerator(GamePanel gp){
        this.gp = gp;
    }

    public Entity getObject(String itemName){
        Entity obj = null;

//        for(int i = 0; i < gp.allObjects.size(); i++){
//            System.out.println(gp.allObjects.get(i).name);
//        }

        //System.out.println("looking for " + itemName);
        for(int i = 0; i < gp.allObjects.size(); i++){
            //System.out.println(gp.allObjects.get(i).name);
            if(gp.allObjects.get(i).name != null) {
                if (gp.allObjects.get(i).name.equalsIgnoreCase(itemName)) {//what's happening isthat allObjects is null when a new game starts so we jus need to initialize all objects when we restart
                    obj = gp.allObjects.get(i);}
            }
            //System.out.println(gp.allObjects.get(i).name);
        }
        return obj;
    }

}
