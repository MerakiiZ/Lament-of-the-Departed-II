package menu;

import main.GamePanel;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;



public class MenuState {

    private List<DustParticle> dustParticles;

    private GamePanel gp;
    private String[] menuOptions = {
            "START GAME",
            "CONTINUE",
            "OPTIONS",
            "ACHIEVEMENTS",
            "EXTRAS",
            "QUIT GAME"
    };

    private int currentSelection = 0;
    private BufferedImage background;
    private Font menuFont;
    private boolean enterPressed = false;

    public MenuState(GamePanel gamePanel) {
        this.gp = gamePanel;
        this.dustParticles = new ArrayList<>();

        try {
            background = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/main_menu/menu.png")));
            menuFont = new Font("Serif", Font.BOLD, 32);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Generate 50 floating dust particles
        Random rand = new Random();
        for (int i = 0; i < 50; i++) {
            dustParticles.add(new DustParticle(rand.nextInt(gp.screenWidth), rand.nextInt(gp.screenHeight)));
        }
    }

    public void update() {
        if (enterPressed) {
            selectOption();
            enterPressed = false;
        }

        // Update all dust particles
        for (DustParticle p : dustParticles) {
            p.update();
        }
    }

    public void draw(Graphics2D g) {
        if (background != null) {
            g.drawImage(background, 0, 0, gp.screenWidth, gp.screenHeight, null);
        }

        // Draw floating dust particles
        for (DustParticle p : dustParticles) {
            p.draw(g);
        }

        g.setFont(menuFont);
        g.setColor(Color.WHITE);

        int startX = gp.screenWidth / 4;
        int startY = gp.screenHeight / 3;
        int lineHeight = 50;

        for (int i = 0; i < menuOptions.length; i++) {
            if (i == currentSelection) {
                g.setColor(Color.YELLOW);
            } else {
                g.setColor(Color.WHITE);
            }
            g.drawString(menuOptions[i], startX, startY + i * lineHeight);
        }
    }

    public void handleKeyPress(int keyCode) {
        if (keyCode == KeyEvent.VK_DOWN) {
            currentSelection = (currentSelection + 1) % menuOptions.length;
        } else if (keyCode == KeyEvent.VK_UP) {
            currentSelection = (currentSelection - 1 + menuOptions.length) % menuOptions.length;
        } else if (keyCode == KeyEvent.VK_ENTER) {
            enterPressed = true;
        }
    }

    private void selectOption() {
        switch (currentSelection) {
            case 0 -> {
                System.out.println("Starting Game...");
                gp.inMenu = false; // Switch to game
            }
            case 1 -> System.out.println("Continue...");
            case 2 -> System.out.println("Opening Options...");
            case 3 -> System.out.println("Showing Achievements...");
            case 4 -> System.out.println("Opening Extras...");
            case 5 -> System.exit(0); // Quit the game
        }
    }
}
