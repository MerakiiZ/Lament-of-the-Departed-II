package entity;

import main.GamePanel;
import main.KeyHandler;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Objects;

public class Player extends Entity {

    KeyHandler keyH;

    public final int screenX;
    public final int screenY;

    public int hasKey = 0; // Track how many keys the player has

    BufferedImage idleImage;

    public Player(GamePanel gp, KeyHandler keyH) {

        super(gp);
        this.keyH = keyH;

        screenX = gp.screenWidth / 2 - (gp.tileSize / 2);
        screenY = gp.screenHeight / 2 - (gp.tileSize / 2);

        solidArea = new Rectangle();
            solidArea.x = 10;
            solidArea.y = 20;
            solidAreaDefaultX = solidArea.x;
            solidAreaDefaultY = solidArea.y;
            solidArea.width = 28;
            solidArea.height = 28;

        setDefaultValues();
        getPlayerImage();
    }

    public void setDefaultValues() {
        worldX = gp.tileSize * 35;
        worldY = gp.tileSize * 52;
        speed = 4;
        direction = "down"; // Default direction
        idleImage = null;  // Ensure it is initialized
    }

    public void getPlayerImage() {
        north0 = setup("/player/spr_npc_daphni_walk_north_0");
        north1 = setup("/player/spr_npc_dapnhi_walk_north_1");
        north2 = setup("/player/spr_npc_daphni_walk_north_2");
        north3 = setup("/player/spr_npc_daphni_walk_north_3");

        east0 = setup("/player/spr_npc_daphni_walk_east_0");
        east1 = setup("/player/spr_npc_dapni_walk_east_1");
        east2 = setup("/player/spr_npc_daphni_walk_east_2");
        east3 = setup("/player/spr_npc_daphni_east_3");

        west0 = setup("/player/spr_npc_daphni_walk_west_0");
        west1 = setup("/player/spr_npc_dapni_walk_west_1");
        west2 = setup("/player/spr_npc_daphni_walk_west_0");
        west3 = setup("/player/spr_npc_daphni_west_3");

        south0 = setup("/player/spr_npc_daphni_walk_south_0");
        south1 = setup("/player/spr_npc_daphni_walk_south_1");
        south2 = setup("/player/spr_npc_daphni_south_2");
        south3 = setup("/player/spr_npc_daphni_south_3");
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

        // **Step 1: Reset Collision Flag**
        collisionOn = false;

        // **Step 2: Check Tile Collision (Walls, Water, etc.)**
        gp.cChecker.checkTile(this);

        // **Step 3: Check Object Collision (Detect objects but still move)**
        int objIndex = gp.cChecker.checkObject(this, true);
        pickUpObject(objIndex);

        //check NPC
        int npcIndex = gp.cChecker.checkEntity(this, gp.npc);
        interactNPC(npcIndex);

        // **Step 4: Move Player if No Tile Collision**
        if (!collisionOn && isMoving) {
//            System.out.println("Moving " + direction + " - No collision detected");
            switch (direction) {
                case "up" -> worldY -= speed;
                case "down" -> worldY += speed;
                case "left" -> worldX -= speed;
                case "right" -> worldX += speed;
            }
        }

        // **Step 5: Handle Sprite Animation**
        if (isMoving) {
            spriteCounter++;
            if (spriteCounter > 12) {
                spriteNum = (spriteNum % 3) + 1;
                spriteCounter = 0;
            }
        } else {
            // **Idle animation when not moving**
            idleImage = switch (direction) {
                case "up" -> north0;
                case "down" -> south0;
                case "left" -> west0;
                case "right" -> east0;
                default -> south0;
            };
        }
    }

    public void pickUpObject (int i){
        if (i != 999){

            String objectName = gp.obj[i].name;

            switch(objectName){
                case "Key":
                    gp.playSE(1);
                    hasKey++;
                    gp.obj[i] = null;
                   gp.ui.showMessage("Acquired Key!");
                    break;

                case "Door":
                    if (hasKey == 0){
                        gp.ui.showMessage("It's locked.");
                    }
                    if (hasKey > 0 ){
                        gp.ui.showMessage("Door unlocked.");
                        gp.playSE(3);
                        gp.obj[i] = null;
                        hasKey--;
                    }
                    System.out.println("Key: "+hasKey);
                    break;

                case "Book":
                    gp.ui.showMessage("Acquired \"Godspeed\"");
                    gp.playSE(0);
                    speed +=0.5;
                    gp.obj[i] = null;
                    break;

//                case ""  : //end
//                gp.ui.gameFinished();
//                gp.stopMusic();
//                break;
            }
        }
    }

    public void interactNPC (int i){

        if (i != 999) {

            if (gp.keyH.enterPressed == true) {
                gp.gameState = gp.dialougeState;
                gp.npc[i].speak();
            }
        }
        gp.keyH.enterPressed = false;
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
        g2.drawImage(image, screenX, screenY, null);
    }
}
