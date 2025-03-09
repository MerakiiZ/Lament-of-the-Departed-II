package main;

import entity.Player;
import tile.CollisionChecker;
import tile.TileManager;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {

    //Screen Settings
    public final int originalTileSize = 16; //16 x 16
    final int scale = 3; //16 x 4 = 64

    public int tileSize = originalTileSize * scale; //48x48 tile
    public final int maxScreenCol = 16; //20 tiles wide
    public final int maxScreenRow = 12; //12 tiles height
    public final int screenWidth = tileSize * maxScreenCol; //1280 pixels
    public final int screenHeight = tileSize * maxScreenRow;//768 pixels

    //WORLD SETTINGS
    public final int maxWorldCol = 100;
    public final int maxWorldRow = 100;
    public final int worldWidth = tileSize * maxWorldCol;
    public final int worldHeight = tileSize * maxWorldRow;

    //FPS
    int FPS = 60;
    public TileManager tileM = new TileManager(this);
    KeyHandler keyH = new KeyHandler();
    Thread gameThread;
    public CollisionChecker cChecker = new CollisionChecker(this);
    public Player player = new Player (this, keyH);

    //constructor
    public GamePanel(){
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true); //if set to true, all the drawing from this component will be done in an offscreen buffer
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    public void startGameThread(){
        gameThread = new Thread(this); //pass GamePanel class
        gameThread.start();
    }

    //Game Loop
    @Override
   /* public void run() {

        //sleep method
        double drawInterval = 1000000000/FPS;
        double nextDrawtime = System.nanoTime() + drawInterval;

        while(gameThread != null){

            //Update
            update();
            //Draw with updated info
            repaint();

            try {
                double remainingTime = nextDrawtime - System.nanoTime();
                remainingTime = remainingTime/1000000;

                if (remainingTime <0){
                    remainingTime = 0;
                }

                Thread.sleep((long) remainingTime);

                nextDrawtime += drawInterval;

            } catch (InterruptedException e) {
                //TODO
                e.printStackTrace();
            }
        }
    }*/

    //delta method

    public void run (){

        double drawInterval = 1000000000/FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;

        while (gameThread != null){

            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;

            timer += (currentTime - lastTime);

            lastTime = currentTime;

            if (delta >= 1){
                update();
                repaint();
                delta--;
                drawCount++;
            }

            if (timer >= 1000000000){
                System.out.println("FPS: " + drawCount);
                drawCount = 0;
                timer = 0;
            }

        }
    }


    //4 x 3

    //Upper corner sa Java X:0 ; Y:0
    public void update(){

        player.update();
    }

    //draw
    public void paintComponent(Graphics g){
        super.paintComponent(g);


        Graphics2D g2 = (Graphics2D) g;

        tileM.draw(g2);
        player.draw(g2);

        g2.dispose();
    }

}
