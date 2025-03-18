import main.GamePanel;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Objects;

public class Main {

    public static JFrame window;

    public static void main(String[] args) {

        //create Frame
        window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //user can xit the window
        window.setResizable(false); //so the window cannot be resized
        window.setTitle("A Lament for the Departed II");

        //change logo
       try{
           BufferedImage logo = ImageIO.read(Objects.requireNonNull(Main.class.getResourceAsStream("/icon/icon1.png")));
           window.setIconImage(logo);
       } catch (IOException e) {
           e.printStackTrace();
        }

        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);

        window.pack();

        window.setLocationRelativeTo(null); //not specifying the location; window will be in the center
        window.setVisible(true);

        gamePanel.setupGame();
        gamePanel.startGameThread();
    }

}

