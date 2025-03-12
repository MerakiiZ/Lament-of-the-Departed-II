package entity;

//parent class for entities

import main.GamePanel;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Objects;

public class Entity {

    GamePanel gp;
    public int worldX, worldY;
    public int speed;

    public BufferedImage south0, south1, south2, south3, north0, north1, north2, north3, east0, east1, east2, east3, west0, west1, west2, west3;        //Buffered Image describes an image with an accessible buffer of image data
    public String direction;

    public int spriteCounter = 0;
    public int spriteNum = 1;

    public Rectangle solidArea = new Rectangle(0, 0, 20, 20);
    public int solidAreaDefaultX, solidAreaDefaultY;
    public boolean collisionOn = false;
    public int actionLockCounter = 0;

    // ENTITIES
    public Entity(GamePanel gp){
        this.gp = gp;
    }

    // CHARACTER STATUS
    public int maxLife;
    public int life;

    public void setAction(){}
    public void update() {
        setAction();

        collisionOn = false;
        gp.cChecker.checkTile(this);
        gp.cChecker.checkObject(this, false);
        gp.cChecker.checkPlayer(this);

        if (collisionOn == false) {
//            System.out.println("Moving " + direction + " - No collision detected");
            switch (direction) {
                case "up" -> worldY -= speed;
                case "down" -> worldY += speed;
                case "left" -> worldX -= speed;
                case "right" -> worldX += speed;
            }
        }

        spriteCounter++;

        if (spriteCounter > 12) {
            spriteNum = (spriteNum % 3) + 1;
            spriteCounter = 0;
        }
        // **Step 5: Handle Sprite Animation**
//        if (isMoving) {
//            spriteCounter++;
//            if (spriteCounter > 12) {
//                spriteNum = (spriteNum % 3) + 1;
//                spriteCounter = 0;
//            }
//        } else {
//            // **Idle animation when not moving**
//            idleImage = switch (direction) {
//                case "up" -> north0;
//                case "down" -> south0;
//                case "left" -> west0;
//                case "right" -> east0;
//                default -> south0;
//            };
//        }
    }

    public void draw(Graphics2D g2){
        BufferedImage idleImage = null;
        BufferedImage image = null;
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        // **Only draw tiles that are visible on screen**
        if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
                worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
                worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
                worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {


            image = switch (direction) {
                case "up" -> switch (spriteNum) {
                    case 1 -> north1;
                    case 2 -> north2;
                    default -> north3;
                };
                case "down" -> switch (spriteNum) {
                    case 1 -> south1;
                    case 2 -> south2;
                    default -> south3;
                };
                case "left" -> switch (spriteNum) {
                    case 1 -> west1;
                    case 2 -> west2;
                    default -> west3;
                };
                case "right" -> switch (spriteNum) {
                    case 1 -> east1;
                    case 2 -> east2;
                    default -> east3;
                };
                default -> idleImage;
            };

            g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
        }
    }
    public BufferedImage setup (String imageName){

        UtilityTool uTool = new UtilityTool();
        BufferedImage image = null;

        try{
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(imageName + ".png")));
            image = uTool.scaleImage(image, gp.tileSize, gp.tileSize);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return image;
    }
}
