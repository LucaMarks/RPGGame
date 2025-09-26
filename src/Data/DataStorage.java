package Data;

import Main.GamePanel;

import java.io.Serializable;
import java.util.ArrayList;

public class DataStorage implements Serializable {

    //plr stats
    int level;
    int maxLife;
    int exp;
    int maxMana;
    int strength;
    int dexterity;
    int nextLevelExp;
    int coin;
    int xCoord;
    int yCoord;

    //player inventory
    ArrayList<String> itemNames = new ArrayList<>();
    ArrayList<Integer> itemAmounts = new ArrayList<>();

    int currWeaponSlot;
    int currShieldSlot;

    //Objects on map
    String mapObjectNames[][];
    int mapObjectWorldX[][];
    int mapObjectWorldY[][];
    String mapObjectLootNames[][];
    boolean mapObjectOpened[][];
}
