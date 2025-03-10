package main;

import object.OBJ_Book;
import object.OBJ_Door;
import object.OBJ_Key;

public class AssetSetter {

    GamePanel gp;

    public AssetSetter(GamePanel gp){
        this.gp = gp;
    }

    public void setObject(){

        //KEY
        gp.obj[0] = new OBJ_Key(gp);
        gp.obj[0].worldX = 46 * gp.tileSize;
        gp.obj[0].worldY = 52 * gp.tileSize;

        //BOOK
        gp.obj[1] = new OBJ_Book(gp);
        gp.obj[1].worldX = 33 * gp.tileSize;
        gp.obj[1].worldY = 55 * gp.tileSize;

        //DOOR
        gp.obj[2] = new OBJ_Door(gp);
        gp.obj[2].worldX = 16 * gp.tileSize;
        gp.obj[2].worldY = 53 * gp.tileSize;

        //DOOR
        gp.obj[3] = new OBJ_Door(gp);
        gp.obj[3].worldX = 16 * gp.tileSize;
        gp.obj[3].worldY = 54 * gp.tileSize;
    }
}
