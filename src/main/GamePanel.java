package main;

import com.sun.tools.javac.Main;
import entity.Entity;
import entity.Player;
import menu.MenuState;
import object.SuperObject;
import tile.CollisionChecker;
import tile.TileManager;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.lang.reflect.Array;
import java.security.PublicKey;
import java.util.Random;

import static com.sun.tools.javac.Main.*;
import static java.awt.SystemColor.window;

public class GamePanel extends JPanel implements Runnable {
    public boolean inMenu = true; // Start the game in the menu
    public int transitionState;
    //    public Array currentMap;
    MenuState menuState;

    //Screen Settings
    public final int originalTileSize = 16; //16 x 16
    final int scale = 3; //16 x 4 = 64

    public int tileSize = originalTileSize * scale; //48x48 tile
    public final int maxScreenCol = 16; //20 tiles wide
    public final int maxScreenRow = 12; //12 tiles height
    public final int screenWidth = tileSize * maxScreenCol; //1280 pixels
    public final int screenHeight = tileSize * maxScreenRow;//768 pixels

    //  FOR FULLSCREEN
    int screenWidth2 = screenWidth;
    int screenHeight2 = screenHeight;
    BufferedImage tempScreen;
    Graphics2D g2;


    //WORLD SETTINGS
    public int maxWorldCol;
    public int maxWorldRow;
    public int maxMap = 10;
    public int currentMap = 0;

    //FPS
    int FPS = 60;

    //SYSTEM
    public TileManager tileM = new TileManager(this);
    public KeyHandler keyH = new KeyHandler(this);
    public CollisionChecker cChecker = new CollisionChecker(this);
    public AssetSetter aSetter = new AssetSetter(this);
    public Sound music = new Sound();
    public Sound menuMusic = new Sound();
    public Sound gameMusic = new Sound();
    public Sound endMusic = new Sound();
    public Sound se = new Sound();
    public UI ui = new UI(this);
    public EventHandler eHandler = new EventHandler(this);
    Thread gameThread;
    public boolean checkEvents = true;

    //ENTITY AND OBJECT
    public Player player = new Player (this, keyH);
    public SuperObject obj[][] = new SuperObject[maxMap][10];
    public Entity npc[][] = new Entity[maxMap][10];
    public String currentSpeaker = "";

    // VISUAL EFFECTS
    public float teleportAlpha = 0f;
    public boolean isTeleporting = false;
    public int teleportCounter = 0;
    public final float TELEPORT_FADE_SPEED = 0.05f;
    final Object teleportLock = new Object();



    //GAME STATE
    public int gameState;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int dialougeState = 3;
    public final int endState = 4;


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

        tempScreen = new BufferedImage(screenWidth, screenHeight, BufferedImage.TYPE_INT_ARGB);
        g2 = (Graphics2D) tempScreen.getGraphics();

    }

    public void startGameThread(){
        gameThread = new Thread(this); //pass GamePanel class
        gameThread.start();
    }



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
//                repaint();
                drawToTempScreen();
                drawToScreen();
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
        if (isTeleporting) {
            return;
        }

        // Handle fade transitions first
        if (ui.fadeState.equals("fading")) {
            ui.fadeAlpha += 0.05f;
            if (ui.fadeAlpha >= 1.0f) {
                ui.fadeState = "complete";
            }
            return; // Skip other updates during fade
        }

        if (inMenu) {
            menuState.update();
        } else {
            if (gameState == playState) {
                player.update();
                for (int i = 0; i < npc[1].length; i++) {
                    if (npc[currentMap][i] != null) {
                        npc[currentMap][i].update();
                    }
                }
            }
        }
    }

    public void drawToTempScreen() {
        g2.setColor(Color.black);
        g2.fillRect(0, 0, screenWidth, screenHeight);


        if (gameState == endState) {
            ui.drawEndScreen();
        }

        // DEBUG timing
        long drawStart = 0;
        if (keyH.showDebugText) {
            drawStart = System.nanoTime();
        }

        if (inMenu) {
            menuState.draw(g2);
        } else {
            // TILE
            tileM.draw(g2);

            // OBJECT
            for (int i = 0; i < obj[1].length; i++) {
                if (obj[currentMap][i] != null) {
                    obj[currentMap][i].draw(g2, this);
                }
            }

            // NPC
            for (int i = 0; i < npc[1].length; i++) {
                if (npc[currentMap][i] != null) {
                    npc[currentMap][i].draw(g2);
                }
            }

            // PLAYER
            player.draw(g2);

            // Teleport effect
            synchronized (teleportLock) {
                if (isTeleporting) {
                    g2.setColor(new Color(107, 21, 15, (int)(teleportAlpha * 255)));
                    g2.fillRect(0, 0, screenWidth, screenHeight);
                }
            }

            // UI (always drawn except in end state)
            ui.draw(g2);

            // DEBUG info
            if (keyH.showDebugText) {
                long drawEnd = System.nanoTime();
                long passed = drawEnd - drawStart;
                g2.setFont(new Font("Arial", Font.PLAIN, 20));
                g2.setColor(Color.white);
                int x = 10;
                int y = 400;
                int lineHeight = 20;

                g2.drawString("WorldX = " + player.worldX, x, y); y += lineHeight;
                g2.drawString("WorldY = " + player.worldY, x, y); y += lineHeight;
                g2.drawString("Col = " + (player.worldX + player.solidArea.x)/tileSize, x, y); y += lineHeight;
                g2.drawString("Row = " + (player.worldY + player.solidArea.y)/tileSize, x, y); y += lineHeight;
                g2.drawString("Draw time: " + passed, x, y);
            }
        }

        ui.drawFade(g2);
        // Always draw end state if active

    }


    public void drawToScreen(){

        Graphics g = getGraphics();
        if (g != null) {
            try {
                // Draw the buffered image
                g.drawImage(tempScreen, 0, 0, screenWidth2, screenHeight2, null);

                // Ensure the OS processes the graphics
                Toolkit.getDefaultToolkit().sync();
            } finally {
                g.dispose();
            }
        }
    }
// TELEPORT
public void updateTeleportEffect() {}
    private void drawTeleportParticles(Graphics2D g2, int x, int y) {
        Random rand = new Random();
        for (int i = 0; i < 20; i++) {
            int particleX = x + player.solidArea.x + rand.nextInt(player.solidArea.width);
            int particleY = y + player.solidArea.y + rand.nextInt(player.solidArea.height);
            int size = rand.nextInt(5) + 2;
            g2.setColor(new Color(100 + rand.nextInt(155), 150 + rand.nextInt(105), 255, 150));
            g2.fillOval(screenWidth + particleX - player.worldX + player.screenX,
                    screenHeight + particleY - player.worldY + player.screenY,
                    size, size);
        }
    }


    public void resetGame() {
        gameState = playState;
        inMenu = true; // Or true if you want to return to menu

        // Reset player
        player.setDefaultValues();
        player.hasKey = 0;

        // Reset UI
        ui.resetFade();
        ui.currentDialouge = "";
        ui.showChoice = false;



        // Reset map
        currentMap = 0;

        // Stop and restart music
        stopMusic(7);
        playMusic(2);

        setupGame();
        System.out.println("Game fully reset");
    }

// MUSIC PLAYERS

    int currentMusicTrack = -1;

    public void playMusic(int i){
        if (currentMusicTrack != i) {
            stopMusic(currentMusicTrack);
            music.setFile(i);
            music.loop();
            currentMusicTrack = i;
        }
    }


    public void playEndMusic() {
        music.stop();
        music.setFile(7);
        music.play();
        music.loop();
    }

    public void stopMusic (int i){
        System.out.println("Stopping music track: " + i);
        music.stop();
    }

    public void playSE(int i){
        se.setFile(i);
        se.play();
    }

}
