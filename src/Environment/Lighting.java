package Environment;

import Main.GamePanel;

import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class Lighting {

    GamePanel gp;
    BufferedImage darknessFilter;
    public int dayCounter;
    public float filterAlpha = 0f;

    public final int day = 0;
    public final int dusk = 1;
    public final int night = 2;
    public final int dawn = 3;
    public int dayState = day;

    public Lighting(GamePanel gp) {
        this.gp = gp;
        setLightSource();
    }

    public void setLightSource() {

        //Create a buffered image
        darknessFilter = new BufferedImage(gp.screenWidth, gp.screenHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = (Graphics2D) darknessFilter.getGraphics();

        if (gp.player.currLight == null) {
            g2.setColor(new Color(0, 0, 0, 0.98f));
        } else {
            //for circleX and circleY
            int centerX = gp.player.screenX + (gp.tileSize / 2);
            int centerY = gp.player.screenY + (gp.tileSize / 2);

            //create a gradation effect within the light circle
            Color[] color = new Color[12];
            float[] fraction = new float[12];

            color[0] = new Color(0, 0, 0, .1f);
            color[1] = new Color(0, 0, 0, .42f);
            color[2] = new Color(0, 0, 0, .52f);
            color[3] = new Color(0, 0, 0, .61f);
            color[4] = new Color(0, 0, 0, .69f);
            color[5] = new Color(0, 0, 0, .76f);
            color[6] = new Color(0, 0, 0, .82f);
            color[7] = new Color(0, 0, 0, .87f);
            color[8] = new Color(0, 0, 0, .91f);
            color[9] = new Color(0, 0, 0, .94f);
            color[10] = new Color(0, 0, 0, .96f);
            color[11] = new Color(0, 0, 0, .98f);

            fraction[0] = 0f;
            fraction[1] = 0.4f;
            fraction[2] = 0.5f;
            fraction[3] = 0.6f;
            fraction[4] = .65f;
            fraction[5] = .7f;
            fraction[6] = .75f;
            fraction[7] = .8f;
            fraction[8] = .85f;
            fraction[9] = .9f;
            fraction[10] = .95f;
            fraction[11] = 1f;

            //create a gradation paint for the light circle
            RadialGradientPaint gPaint = new RadialGradientPaint(centerX, centerY, gp.player.currLight.lightRadius, fraction, color);

            //set the gradient data on g2
            g2.setPaint(gPaint);
        }

        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
        //g2.setColor(new Color(0, 0, 0, 0.98f));

        g2.dispose();

    }

    public void resetDay(){
        dayState = day;
        filterAlpha = 0f;
    }

    public void update() {
        if (gp.player.lightUpdated) {
            setLightSource();
            gp.player.lightUpdated = false;
        }
        if (gp.currMap != 2) {

            //check the state of the day
//        dayCounter++;
//        if(dayCounter > 600){
//            dayState ++;
//            if(dayState > 3){dayState = 0;}
//        }
            switch (dayState) {
                case day:
                    dayCounter++;
                    if (dayCounter > 1800) {
                        dayState = dusk;
                        dayCounter = 0;
                    }
                    break;
                case dusk:
                    filterAlpha += 0.0005f;
                    if (filterAlpha > 1f) {
                        filterAlpha = 1f;
                        dayState = night;
                    }
                    break;
                case night:
                    dayCounter++;
                    if (dayCounter > 1800) {
                        dayState = dawn;
                        dayCounter = 0;
                    }
                    break;
                case dawn:
                    filterAlpha -= 0.0005f;
                    if (filterAlpha < 0f) {
                        filterAlpha = 0;
                        dayState = day;
                    }
                    break;
            }
        }
        if (gp.currMap == 2) {

            //need the lantern to come into play
            filterAlpha = .75f;
            dayState = night;
        }
    }

    public void draw(Graphics2D g2) {

        //either outside or inside
        //if(gp.currArea != gp.dungeon){g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, filterAlpha));}
        if (filterAlpha > 0) {
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, filterAlpha));
            g2.drawImage(darknessFilter, 0, 0, null);

            //reset the alpha composite
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        }

        if(gp.currArea == gp.dungeon){
            if(gp.currMap == 3) {filterAlpha = (float) 0.8;}
            else{filterAlpha = (float) 0.25;}//currMap == 4
        }

        //debug
        String situation = "";

        switch (dayState) {
            case day:
                situation = "Day";
                break;
            case dusk:
                situation = "Dusk";
                break;
            case night:
                situation = "Night";
                break;
            case dawn:
                situation = "Dawn";
                break;
        }
//        g2.setColor(Color.WHITE);
//        g2.setFont(g2.getFont().deriveFont(50f));
//        g2.drawString(situation, 800, 500);
    }
}
