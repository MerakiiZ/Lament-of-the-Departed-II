package tile;

import entity.Entity;
import main.GamePanel;

public class CollisionChecker {

    GamePanel gp;

    public CollisionChecker(GamePanel gp) {
        this.gp = gp;
    }

    public void checkTile(Entity entity) {
        int entityLeftWorldX = entity.worldX + entity.solidArea.x;
        int entityRightWorldX = entity.worldX + entity.solidArea.x + entity.solidArea.width;
        int entityTopWorldY = entity.worldY + entity.solidArea.y;
        int entityBottomWorldY = entity.worldY + entity.solidArea.y + entity.solidArea.height;

        int entityLeftCol = entityLeftWorldX / gp.tileSize;
        int entityRightCol = entityRightWorldX / gp.tileSize;
        int entityTopRow = entityTopWorldY / gp.tileSize;
        int entityBottomRow = entityBottomWorldY / gp.tileSize;

        int tileNum1, tileNum2;

        entity.collisionOn = false; // Reset collision before checking

        switch (entity.direction) {
            case "up":
                entityTopRow = (entityTopWorldY - entity.speed) / gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[entityTopRow][entityLeftCol];
                tileNum2 = gp.tileM.mapTileNum[entityTopRow][entityRightCol];

//                System.out.println("Checking UP collision at row " + entityTopRow +
//                        " col [" + entityLeftCol + "," + entityRightCol + "]");

                if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
                    entity.collisionOn = true;
//                    System.out.println("Collision detected UP");
                }
                break;

            case "down":
                entityBottomRow = (entityBottomWorldY + entity.speed) / gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[entityBottomRow][entityLeftCol];
                tileNum2 = gp.tileM.mapTileNum[entityBottomRow][entityRightCol];

//                System.out.println("Checking DOWN collision at row " + entityBottomRow +
//                        " col [" + entityLeftCol + "," + entityRightCol + "]");

                if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
                    entity.collisionOn = true;
//                    System.out.println("Collision detected DOWN");
                }
                break;

            case "left":
                entityLeftCol = (entityLeftWorldX - entity.speed) / gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[entityTopRow][entityLeftCol];
                tileNum2 = gp.tileM.mapTileNum[entityBottomRow][entityLeftCol];

//                System.out.println("Checking LEFT collision at col " + entityLeftCol +
//                        " row [" + entityTopRow + "," + entityBottomRow + "]");

                if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
                    entity.collisionOn = true;
//                    System.out.println("Collision detected LEFT");
                }
                break;

            case "right":
                entityRightCol = (entityRightWorldX + entity.speed) / gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[entityTopRow][entityRightCol];
                tileNum2 = gp.tileM.mapTileNum[entityBottomRow][entityRightCol];

//                System.out.println("Checking RIGHT collision at col " + entityRightCol +
//                        " row [" + entityTopRow + "," + entityBottomRow + "]");

                if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
                    entity.collisionOn = true;
//                    System.out.println("Collision detected RIGHT");
                }
                break;
        }
    }

    public int checkObject(Entity entity, boolean player) {
        int index = 999; // Default: No object collision

        for (int i = 0; i < gp.obj.length; i++) {

            if (gp.obj[i] != null) {

//                // Save original positions
//                int entitySolidX = entity.solidArea.x;
//                int entitySolidY = entity.solidArea.y;
//                int objSolidX = gp.obj[i].solidArea.x;
//                int objSolidY = gp.obj[i].solidArea.y;

                // Set real positions
                entity.solidArea.x = entity.worldX + entity.solidArea.x;
                entity.solidArea.y = entity.worldY + entity.solidArea.y;
                gp.obj[i].solidArea.x = gp.obj[i].worldX + gp.obj[i].solidArea.x;
                gp.obj[i].solidArea.y = gp.obj[i].worldY + gp.obj[i].solidArea.y;

                switch (entity.direction) {
                    case "up":
                        entity.solidArea.y -= entity.speed;
                        if (entity.solidArea.intersects(gp.obj[i].solidArea)) {
                            if(gp.obj[i].collision == true);{
                                entity.collisionOn=true;
                            }
                            if(player==true){
                                index = i;
                            }
                        }
                        break;
                    case "down":
                        entity.solidArea.y += entity.speed;
                        if (entity.solidArea.intersects(gp.obj[i].solidArea)) {
                            if(gp.obj[i].collision == true);{
                                entity.collisionOn=true;
                            }
                            if(player==true){
                                index = i;
                            }
                        }
                        break;
                    case "left":
                        entity.solidArea.x -= entity.speed;
                        if (entity.solidArea.intersects(gp.obj[i].solidArea)) {
                            if(gp.obj[i].collision == true);{
                                entity.collisionOn=true;
                            }
                            if(player==true){
                                index = i;
                            }
                        }
                        break;
                    case "right":
                        entity.solidArea.x += entity.speed;
                        if (entity.solidArea.intersects(gp.obj[i].solidArea)) {
                            if(gp.obj[i].collision == true);{
                                entity.collisionOn=true;
                            }
                            if(player==true){
                                index = i;
                            }
                        }
                        break;
                }

//                // Check if player intersects object
//                if (entity.solidArea.intersects(gp.obj[i].solidArea)) {
//                    System.out.println("Touching object: " + gp.obj[i].name);
//
//                    if (player) {
//                        index = i; // Return object index
//                    }
//                }

                // Reset positions after checking
                entity.solidArea.x = entity.solidAreaDefaultX;
                entity.solidArea.y = entity.solidAreaDefaultY;
                assert gp.obj[i] != null;
                gp.obj[i].solidArea.x = gp.obj[i].solidAreaDefaultX;
                gp.obj[i].solidArea.y = gp.obj[i].solidAreaDefaultY;
            }

        }
        return index;
    }
}