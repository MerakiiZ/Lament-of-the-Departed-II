package entity;

import main.GamePanel;
import main.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Player extends Entity {

    GamePanel gp;
    KeyHandler keyH;

    public final int screenX;
    public final int screenY;

    BufferedImage idleImage;

    public Player(GamePanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;

        screenX = gp.screenWidth / 2 - (gp.tileSize / 2);
        screenY = gp.screenHeight / 2 - (gp.tileSize / 2);

        solidArea = new Rectangle();
            solidArea.x = 10;
            solidArea.y = 20;
            solidArea.width = 28;
            solidArea.height = 28;


        setDefaultValues();
        getPlayerImage();
    }

    public void setDefaultValues() {
        worldX = gp.tileSize * 36;
        worldY = gp.tileSize * 53;
        speed = 4;
        direction = "down"; // Default direction
        idleImage = null;  // Ensure it is initialized
    }

    public void getPlayerImage() {
        try {
            north0 = ImageIO.read(getClass().getClassLoader().getResource("player/spr_npc_daphni_walk_north_0.png"));
            north1 = ImageIO.read(getClass().getClassLoader().getResource("player/spr_npc_dapnhi_walk_north_1.png"));
            north2 = ImageIO.read(getClass().getClassLoader().getResource("player/spr_npc_daphni_walk_north_2.png"));
            north3 = ImageIO.read(getClass().getClassLoader().getResource("player/spr_npc_daphni_walk_north_3.png"));

            east0 = ImageIO.read(getClass().getClassLoader().getResource("player/spr_npc_daphni_walk_east_0.png"));
            east1 = ImageIO.read(getClass().getClassLoader().getResource("player/spr_npc_dapni_walk_east_1.png"));
            east2 = ImageIO.read(getClass().getClassLoader().getResource("player/spr_npc_daphni_walk_east_2.png"));
            east3 = ImageIO.read(getClass().getClassLoader().getResource("player/spr_npc_daphni_east_3.png"));

            west0 = ImageIO.read(getClass().getClassLoader().getResource("player/spr_npc_daphni_walk_west_0.png"));
            west1 = ImageIO.read(getClass().getClassLoader().getResource("player/spr_npc_dapni_walk_west_1.png"));
            west2 = ImageIO.read(getClass().getClassLoader().getResource("player/spr_npc_daphni_walk_west_0.png"));
            west3 = ImageIO.read(getClass().getClassLoader().getResource("player/spr_npc_daphni_west_3.png"));

            south0 = ImageIO.read(getClass().getClassLoader().getResource("player/spr_npc_daphni_walk_south_0.png"));
            south1 = ImageIO.read(getClass().getClassLoader().getResource("player/spr_npc_daphni_walk_south_1.png"));
            south2 = ImageIO.read(getClass().getClassLoader().getResource("player/spr_npc_daphni_south_2.png"));
            south3 = ImageIO.read(getClass().getClassLoader().getResource("player/spr_npc_daphni_south_3.png"));

            idleImage = south0; // Default idle sprite

        } catch (IOException e) {
            System.err.println("Error loading player sprites: " + e.getMessage());
        }
    }

    public void update() {
        boolean isMoving = keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed;

        if (keyH.upPressed) {
            direction = "up";
        }
        if (keyH.downPressed) {
            direction = "down";
        }
        if (keyH.leftPressed) {
            direction = "left";
        }
        if (keyH.rightPressed) {
            direction = "right";
        }

        // Check Tile & Entity Collision
        collisionOn = false;
        gp.cChecker.checkTile(this);

        // If no collision, move the player **only when moving**
        if (!collisionOn && isMoving) {
            System.out.println("Moving " + direction + " - No collision detected");
            switch (direction) {
                case "up" -> worldY -= speed;
                case "down" -> worldY += speed;
                case "left" -> worldX -= speed;
                case "right" -> worldX += speed;
            }
        }

        // Handle sprite animation
        if (isMoving) {
            spriteCounter++;
            if (spriteCounter > 12) {
                spriteNum = (spriteNum % 3) + 1;
                spriteCounter = 0;
            }
        } else {
            // Only update idleImage when not moving
            idleImage = switch (direction) {
                case "up" -> north0;
                case "down" -> south0;
                case "left" -> west0;
                case "right" -> east0;
                default -> south0;
            };
        }
    }


    public void draw(Graphics2D g2) {
        BufferedImage image = keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed
                ? switch (direction) {
            case "up" -> switch (spriteNum) { case 1 -> north1; case 2 -> north2; default -> north3; };
            case "down" -> switch (spriteNum) { case 1 -> south1; case 2 -> south2; default -> south3; };
            case "left" -> switch (spriteNum) { case 1 -> west1; case 2 -> west2; default -> west3; };
            case "right" -> switch (spriteNum) { case 1 -> east1; case 2 -> east2; default -> east3; };
            default -> idleImage;
        }
                : idleImage;

        int playerSize = (int) (gp.tileSize * 1.5); // Increases only player size
        g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
    }
}
