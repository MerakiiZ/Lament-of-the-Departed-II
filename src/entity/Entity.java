package entity;

//parent class for entities

import java.awt.*;
import java.awt.image.BufferedImage;

public class Entity {

    public int worldX, worldY;
    public int speed;

    public BufferedImage south0, south1, south2, south3, north0, north1, north2, north3, east0, east1, east2, east3, west0, west1, west2, west3;        //Buffered Image describes an image with an accessible buffer of image data
    public String direction;

    public int spriteCounter = 0;
    public int spriteNum = 1;

    public Rectangle solidArea;
    public int solidAreaDefaultX, solidAreaDefaultY;
    public boolean collisionOn = false;
}
