package Main;

import javax.swing.*;

public class Main {

    public static JFrame window;

    /*Would be nice to create a better system with enter pressed so that enter pressed false didn't have to be
      manually set outside of key released. This would require some sort of buffer variable for each feature
      that dealt with enter pressed*/
    //16 hours
    //14 hrs
    public static void main(String[] args){
        window = new JFrame();

        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(true);

        window.setTitle("2D Adventure");
        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);

        gamePanel.config.loadConfig();
        if(gamePanel.fullScreenOn){
            window.setUndecorated(true);
        }


        window.pack();

        window.setLocationRelativeTo(null);
        window.setVisible(true);

        gamePanel.setupGame();
        gamePanel.startGameThread();

    }

}
