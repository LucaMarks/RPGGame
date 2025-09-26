package Data;

import Entity.Entity;
import Main.GamePanel;

import java.io.*;

public class SaveLoad {

    GamePanel gp;

    public SaveLoad(GamePanel gp){
        this.gp = gp;
    }
    public void save(){

        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File("save.dat")));

            DataStorage ds = new DataStorage();

            //player stats
            ds.level = gp.player.level;
            ds.maxLife = gp.player.maxLife;
            ds.exp = gp.player.exp;
            ds.maxMana = gp.player.maxMana;
            ds.strength = gp.player.strength;
            ds.dexterity = gp.player.dexterity;
            ds.nextLevelExp = gp.player.nextLevelExp;
            ds.coin = gp.player.coin;
            ds.xCoord = gp.player.worldX;
            ds.yCoord = gp.player.worldY;

            //player inventory
            for(int i = 0; i < gp.player.inventory.size(); i++){
                ds.itemNames.add(gp.player.inventory.get(i).name);
                ds.itemAmounts.add(gp.player.inventory.get(i).amount);
                //System.out.println(ds.itemNames.get(i) + " saved!");
            }

            //plr equipment
            ds.currWeaponSlot = gp.player.getCurrWeaponSlot();
            ds.currShieldSlot = gp.player.getCurrShieldSlot();

            //Objects on Map
            ds.mapObjectNames = new String[gp.maxMap][gp.obj[1].length];
            ds.mapObjectWorldX = new int[gp.maxMap][gp.obj[1].length];
            ds.mapObjectWorldY = new int[gp.maxMap][gp.obj[1].length];
            ds.mapObjectLootNames = new String[gp.maxMap][gp.obj[1].length];
            ds.mapObjectOpened = new boolean[gp.maxMap][gp.obj[1].length];

            for(int mapNum = 0; mapNum < gp.maxMap; mapNum++){

                for(int i = 0; i < gp.obj[1].length; i++){

                    if(gp.obj[mapNum][i] == null){ds.mapObjectNames[mapNum][i] = "NA";}
                    else{
                        ds.mapObjectNames[mapNum][i] = gp.obj[mapNum][i].name;
                        ds.mapObjectWorldX[mapNum][i] = gp.obj[mapNum][i].worldX;
                        ds.mapObjectWorldY[mapNum][i] = gp.obj[mapNum][i].worldY;
                        if(gp.obj[mapNum][i].loot != null){ds.mapObjectLootNames[mapNum][i] = gp.obj[mapNum][i].loot.name;}

                        ds.mapObjectOpened[mapNum][i] = gp.obj[mapNum][i].opened;
                    }
                }
            }

            //write the dataStorage object
            oos.writeObject(ds);
        }catch(Exception e){e.printStackTrace();}


    }
    public void load(){

        try{
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File("save.dat")));

            //read data storage
            DataStorage ds = (DataStorage) ois.readObject();

            gp.player.level = ds.level;
            gp.player.maxLife = ds.maxLife;
            gp.player.exp = ds.exp;
            gp.player.maxMana = ds.maxMana;
            gp.player.strength = ds.strength;
            gp.player.dexterity = ds.dexterity;
            gp.player.nextLevelExp = ds.nextLevelExp;
            gp.player.coin = ds.coin;
//            gp.player.worldX = ds.xCoord * gp.tileSize;
//            gp.player.worldY = ds.yCoord * gp.tileSize;

            //plr inventory
            gp.player.inventory.clear();
            for(int i = 0; i < ds.itemNames.size(); i++){
                Entity item = gp.entityGenerator.getObject(ds.itemNames.get(i));
                if(item != null) {
                    gp.player.inventory.add(item);
                    //System.out.println("added " + item.name);
                    gp.player.inventory.get(i).amount = ds.itemAmounts.get(i);//the last item aint gettin loaded or sum
                    //System.out.print(gp.player.inventory.get(i).name + " Loaded!");
                }
                //else{System.out.println("Item is null!");}
            }

            //plr equipment
            gp.player.currentWeapon = gp.player.inventory.get(ds.currWeaponSlot);
            gp.player.currentShield = gp.player.inventory.get(ds.currShieldSlot);
            gp.player.getAttack();
            gp.player.getDefense();
            gp.player.getPlayerAttackImage();

            //objects on map
            for(int mapNum = 0; mapNum < gp.maxMap; mapNum++){

                for(int i = 0; i < gp.obj[1].length; i++){

                    if(ds.mapObjectNames[mapNum][i] != null && ds.mapObjectNames[mapNum][i].equals("NA")){gp.obj[mapNum][i] = null;}
                    else{
                        gp.obj[mapNum][i] = gp.entityGenerator.getObject(ds.mapObjectNames[mapNum][i]);
                        gp.obj[mapNum][i].worldX = ds.mapObjectWorldX[mapNum][i];
                        gp.obj[mapNum][i].worldY = ds.mapObjectWorldY[mapNum][i];

                        if(ds.mapObjectLootNames[mapNum][i] != null){gp.obj[mapNum][i].setLoot(gp.entityGenerator.getObject(ds.mapObjectLootNames[mapNum][i]));}

                        gp.obj[mapNum][i].opened = ds.mapObjectOpened[mapNum][i];

                        if(gp.obj[mapNum][i].opened){gp.obj[mapNum][i].down1 = gp.obj[mapNum][i].image2;}
                    }
                }
            }

        }catch(Exception e){e.printStackTrace();}
    }

}
