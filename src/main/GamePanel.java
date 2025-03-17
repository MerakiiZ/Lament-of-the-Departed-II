package main;

import entity.Entity;
import entity.Player;
import menu.MenuState;
import object.SuperObject;
import tile.CollisionChecker;
import tile.TileManager;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {

    public boolean inMenu = true; // Start the game in the menu
    MenuState menuState;

    //Screen Settings
    public final int originalTileSize = 16; //16 x 16
    final int scale = 3; //16 x 4 = 64

    public int tileSize = originalTileSize * scale; //48x48 tile
    public final int maxScreenCol = 16; //20 tiles wide
    public final int maxScreenRow = 12; //12 tiles height
    public final int screenWidth = tileSize * maxScreenCol; //1280 pixels
    public final int screenHeight = tileSize * maxScreenRow;//768 pixels

    //WORLD SETTINGS
    public int maxWorldCol;
    public int maxWorldRow;
//    public final int worldWidth = tileSize * maxWorldCol;
//    public final int worldHeight = tileSize * maxWorldRow;

    //FPS
    int FPS = 60;

    //SYSTEM
    public TileManager tileM = new TileManager(this);
    public KeyHandler keyH = new KeyHandler(this);
    public CollisionChecker cChecker = new CollisionChecker(this);
    public AssetSetter aSetter = new AssetSetter(this);
    public Sound music = new Sound();
    public Sound se = new Sound();
    public UI ui = new UI(this);
    Thread gameThread;

    //ENTITY AND OBJECT
    public Player player = new Player (this, keyH);
    public SuperObject obj[] = new SuperObject[10];
    public Entity npc[] = new Entity[10];

    //GAME STATE
    public int gameState;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int dialougeState = 3;

    //constructor
    public GamePanel(){
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true); //if set to true, all the drawing from this component will be done in an offscreen buffer
        this.addKeyListener(keyH);
        this.setFocusable(true);

        menuState = new MenuState(this); // Initialize the menu

        setupGame();
    }

    public void setupGame(){

        aSetter.setObject();
        aSetter.sertNpc();
        playMusic(2);
        gameState = playState;
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
    public void update() {
        if (inMenu) {
            menuState.update();
        } else {
            if (gameState == playState) {
                player.update();
                for (int i = 0; i < npc.length; i++) {
                    if(npc[i] != null){
                        npc[i].update();
                    }
                }
            }
            if (gameState == pauseState) {
            }
        }
    }

    //draw
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        //DEBUG
        long drawStart = 0;
        if(keyH.checkDrawTime == true){
            drawStart = System.nanoTime();
        }

        if (inMenu) {
            menuState.draw(g2);
        } else {

            //TILE
            tileM.draw(g2);
            //OBJECT
            for (int i = 0; i < obj.length; i++) {
                if (obj[i]!=null){
                    obj[i].draw(g2, this);
                }
            }

            //NPC
            for (int i = 0; i < npc.length; i++) {
                if (npc[i] != null){
                    npc[i].draw(g2);
                }
            }

            //PLAYER
            player.draw(g2);

            //UI
            ui.draw(g2);

            //DEBUG
            if(keyH.checkDrawTime == true){
                long drawEnd = System.nanoTime();
                long passed = drawEnd - drawStart;
                g2.setColor(Color.white);
                g2.drawString("Draw time: " + passed, 10, 400);
                System.out.println("Draw time: " + passed);
            }

        }

        g2.dispose();
    }

    public void playMusic (int i){
        music.setFile(i);
        music.play();
        music.loop();
    }

    public void stopMusic (int i){
        music.stop();
    }

    public void playSE(int i){
        se.setFile(i);
        se.play();
    }

}
