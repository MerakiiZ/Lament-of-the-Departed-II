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
                tileNum1 = gp.tileM.mapTileNum[gp.currentMap][entityTopRow][entityLeftCol];
                tileNum2 = gp.tileM.mapTileNum[gp.currentMap][entityTopRow][entityRightCol];

//                System.out.println("Checking UP collision at row " + entityTopRow +
//                        " col [" + entityLeftCol + "," + entityRightCol + "]");

                if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
                    entity.collisionOn = true;
//                    System.out.println("Collision detected UP");
                }
                break;

            case "down":
                entityBottomRow = (entityBottomWorldY + entity.speed) / gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[gp.currentMap][entityBottomRow][entityLeftCol];
                tileNum2 = gp.tileM.mapTileNum[gp.currentMap][entityBottomRow][entityRightCol];

//                System.out.println("Checking DOWN collision at row " + entityBottomRow +
//                        " col [" + entityLeftCol + "," + entityRightCol + "]");

                if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
                    entity.collisionOn = true;
//                    System.out.println("Collision detected DOWN");
                }
                break;

            case "left":
                entityLeftCol = (entityLeftWorldX - entity.speed) / gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[gp.currentMap][entityTopRow][entityLeftCol];
                tileNum2 = gp.tileM.mapTileNum[gp.currentMap][entityBottomRow][entityLeftCol];

//                System.out.println("Checking LEFT collision at col " + entityLeftCol +
//                        " row [" + entityTopRow + "," + entityBottomRow + "]");

                if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
                    entity.collisionOn = true;
//                    System.out.println("Collision detected LEFT");
                }
                break;

            case "right":
                entityRightCol = (entityRightWorldX + entity.speed) / gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[gp.currentMap][entityTopRow][entityRightCol];
                tileNum2 = gp.tileM.mapTileNum[gp.currentMap][entityBottomRow][entityRightCol];

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

        for (int i = 0; i < gp.obj[1].length; i++) {

            if (gp.obj[gp.currentMap][i] != null) {

//                // Save original positions
//                int entitySolidX = entity.solidArea.x;
//                int entitySolidY = entity.solidArea.y;
//                int objSolidX = gp.obj[i].solidArea.x;
//                int objSolidY = gp.obj[i].solidArea.y;

                // Set real positions
                entity.solidArea.x = entity.worldX + entity.solidArea.x;
                entity.solidArea.y = entity.worldY + entity.solidArea.y;
                gp.obj[gp.currentMap][i].solidArea.x = gp.obj[gp.currentMap][i].worldX + gp.obj[gp.currentMap][i].solidArea.x;
                gp.obj[gp.currentMap][i].solidArea.y = gp.obj[gp.currentMap][i].worldY + gp.obj[gp.currentMap][i].solidArea.y;

                switch (entity.direction) {
                    case "up":
                        entity.solidArea.y -= entity.speed;
                        if (entity.solidArea.intersects(gp.obj[gp.currentMap][i].solidArea)) {
                            if (gp.obj[gp.currentMap][i].collision == true) ;
                            {
                                entity.collisionOn = true;
                            }
                            if (player == true) {
                                index = i;
                            }
                        }
                        break;
                    case "down":
                        entity.solidArea.y += entity.speed;
                        if (entity.solidArea.intersects(gp.obj[gp.currentMap][i].solidArea)) {
                            if (gp.obj[gp.currentMap][i].collision == true) ;
                            {
                                entity.collisionOn = true;
                            }
                            if (player == true) {
                                index = i;
                            }
                        }
                        break;
                    case "left":
                        entity.solidArea.x -= entity.speed;
                        if (entity.solidArea.intersects(gp.obj[gp.currentMap][i].solidArea)) {
                            if (gp.obj[gp.currentMap][i].collision == true) ;
                            {
                                entity.collisionOn = true;
                            }
                            if (player == true) {
                                index = i;
                            }
                        }
                        break;
                    case "right":
                        entity.solidArea.x += entity.speed;
                        if (entity.solidArea.intersects(gp.obj[gp.currentMap][i].solidArea)) {
                            if (gp.obj[gp.currentMap][i].collision == true) ;
                            {
                                entity.collisionOn = true;
                            }
                            if (player == true) {
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
                gp.obj[gp.currentMap][i].solidArea.x = gp.obj[gp.currentMap][i].solidAreaDefaultX;
                gp.obj[gp.currentMap][i].solidArea.y = gp.obj[gp.currentMap][i].solidAreaDefaultY;
            }

        }
        return index;
    }

    //check NPC OR MONSTER COLLISION
    public int checkEntity(Entity entity, Entity[][] target) {
        int index = 999; // Default: No object collision

        for (int i = 0; i < target[1].length; i++) {

            if (target[gp.currentMap][i] != null) {

//                // Save original positions
//                int entitySolidX = entity.solidArea.x;
//                int entitySolidY = entity.solidArea.y;
//                int objSolidX = gp.obj[i].solidArea.x;
//                int objSolidY = gp.obj[i].solidArea.y;

                // Set real positions
                entity.solidArea.x = entity.worldX + entity.solidArea.x;
                entity.solidArea.y = entity.worldY + entity.solidArea.y;
                target[gp.currentMap][i].solidArea.x = target[gp.currentMap][i].worldX + target[gp.currentMap][i].solidArea.x;
                target[gp.currentMap][i].solidArea.y = target[gp.currentMap][i].worldY + target[gp.currentMap][i].solidArea.y;

                switch (entity.direction) {
                    case "up":
                        entity.solidArea.y -= entity.speed;
                        if (entity.solidArea.intersects(target[gp.currentMap][i].solidArea)) {
                            entity.collisionOn = true;
                            index = i;

                        }
                        break;

                    case "down":
                        entity.solidArea.y += entity.speed;
                        if (entity.solidArea.intersects(target[gp.currentMap][i].solidArea)) {
                            entity.collisionOn = true;
                            index = i;

                        }

                        break;

                    case "left":
                        entity.solidArea.x -= entity.speed;
                        if (entity.solidArea.intersects(target[gp.currentMap][i].solidArea)) {
                            entity.collisionOn = true;
                            index = i;

                        }
                        break;
                    case "right":
                        entity.solidArea.x += entity.speed;
                        if (entity.solidArea.intersects(target[gp.currentMap][i].solidArea)) {
                            entity.collisionOn = true;
                            index = i;

                            break;
                        }
                }


                        // Reset positions after checking
                        entity.solidArea.x = entity.solidAreaDefaultX;
                        entity.solidArea.y = entity.solidAreaDefaultY;
                        assert target[i] != null;
                        target[gp.currentMap][i].solidArea.x = target[gp.currentMap][i].solidAreaDefaultX;
                        target[gp.currentMap][i].solidArea.y = target[gp.currentMap][i].solidAreaDefaultY;

            }
        }
        return index;
    }

    public void checkPlayer (Entity entity){
        entity.solidArea.x = entity.worldX + entity.solidArea.x;
        entity.solidArea.y = entity.worldY + entity.solidArea.y;
        gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
        gp.player.solidArea.y =gp.player.worldY + gp.player.solidArea.y;

        switch (entity.direction) {
            case "up":
                entity.solidArea.y -= entity.speed;
                if (entity.solidArea.intersects(gp.player.solidArea)) {
                    entity.collisionOn = true;

                }
                break;

            case "down":
                entity.solidArea.y += entity.speed;
                if (entity.solidArea.intersects(gp.player.solidArea)) {
                    entity.collisionOn = true;

                }

                break;

            case "left":
                entity.solidArea.x -= entity.speed;
                if (entity.solidArea.intersects(gp.player.solidArea)) {
                    entity.collisionOn = true;

                }
                break;
            case "right":
                entity.solidArea.x += entity.speed;
                if (entity.solidArea.intersects(gp.player.solidArea)) {
                    entity.collisionOn = true;

                    break;
                }
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
        assert gp.player!= null;
        gp.player.solidArea.x = gp.player.solidAreaDefaultX;
        gp.player.solidArea.y = gp.player.solidAreaDefaultY;
    }
}
