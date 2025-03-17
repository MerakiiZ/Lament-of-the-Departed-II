package entity;

//parent class for entities

import main.GamePanel;
import main.KeyHandler;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Objects;

public class Entity {

    // For players, keyH will be set; for NPCs, it remains null.
    KeyHandler keyH;
    GamePanel gp;

    public int worldX, worldY;
    public int speed;

    public BufferedImage south0, south1, south2, south3, north0, north1, north2, north3,
            east0, east1, east2, east3, west0, west1, west2, west3;
    public String direction;

    public int spriteCounter = 0;
    public int spriteNum = 1;

    public Rectangle solidArea = new Rectangle(0, 0, 40, 40);
    public int solidAreaDefaultX, solidAreaDefaultY;
    public boolean collisionOn = false;
    public int actionLookCounter;

    String dialouges[] = new String[20];
    int dialougesIndex = 0;

    boolean isMoving = false;

    // Used as fallback if no sprite is selected
    BufferedImage idleImage;

    // Constructor for all entities; for players, pass in the KeyHandler from GamePanel in the subclass.
    public Entity(GamePanel gp) {
        this.gp = gp;
    }

    public void setAction() {
        // To be overridden by subclasses
    }

    public void speak(){

        if (dialouges [dialougesIndex] == null){
            dialougesIndex = 0;
        }
        gp.ui.currentDialouge = dialouges[dialougesIndex];
        dialougesIndex++;

        switch(gp.player.direction){
            case"up":
                direction = "down";
                break;

            case "down":
                direction = "up";
                break;

            case "left":
                direction = "right";
                break;

            case "right":
                direction = "left";
                break;
        }
    }

    public void update() {
        // To be overridden by subclasses

        setAction();

        collisionOn = false;
        gp.cChecker.checkTile(this);
        gp.cChecker.checkObject(this, false);
        gp.cChecker.checkPlayer(this);

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

    public void draw(Graphics2D g2) {
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
                worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
                worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
                worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {

            BufferedImage image = null;

            if (keyH != null && (keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed)) {
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
            } else {
                // When not using key input (or for NPCs), we rely on our isMoving flag.
                if (isMoving) {
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
                } else {
                    image = idleImage;
                }
            }

            if (image == null) {
                System.out.println("NPC image is null for direction: " + direction);
            }

            g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
        }
    }

    public BufferedImage setup(String imagePath) {
        UtilityTool uTool = new UtilityTool();
        BufferedImage image = null;

        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(imagePath + ".png")));
            image = uTool.scaleImage(image, gp.tileSize, gp.tileSize);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return image;
    }
}
