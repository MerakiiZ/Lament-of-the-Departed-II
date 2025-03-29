package main;

import entity.*;
import object.OBJ_Book;
import object.OBJ_Door;
import object.OBJ_Key;
import object.OBJ_Secret;

public class AssetSetter {

    GamePanel gp;

    public AssetSetter(GamePanel gp){
        this.gp = gp;
    }

    public void setObject(){

        int mapNum = 0;
        //KEY
        gp.obj[mapNum][0] = new OBJ_Key(gp);
        gp.obj[mapNum][0].worldX = 46 * gp.tileSize;
        gp.obj[mapNum][0].worldY = 52 * gp.tileSize;

        //BOOK
        gp.obj[mapNum][1] = new OBJ_Book(gp);
        gp.obj[mapNum][1].worldX = 33 * gp.tileSize;
        gp.obj[mapNum][1].worldY = 55 * gp.tileSize;

        //DOOR
        gp.obj[mapNum][3] = new OBJ_Door(gp);
        gp.obj[mapNum][3].worldX = 16 * gp.tileSize;
        gp.obj[mapNum][3].worldY = 54 * gp.tileSize;

        //SECRET
        gp.obj[mapNum][4] = new OBJ_Secret(gp);
        gp.obj[mapNum][4].worldX = 4 * gp.tileSize;
        gp.obj[mapNum][4].worldY = 51 * gp.tileSize;
    }

    public void sertNpc(){
        int mapNum = 0;
        gp.npc[mapNum][0]= new NPC_Acacius(gp);
        gp.npc[mapNum][0].worldX = 42 * gp.tileSize;
        gp.npc[mapNum][0].worldY = 52 * gp.tileSize;

        gp.npc[mapNum][1] = new NPC_Daphni(gp);
        gp.npc[mapNum][1].worldX = 70 * gp.tileSize;
        gp.npc[mapNum][1].worldY = 53 * gp.tileSize;

        gp.npc[mapNum][2] = new NPC_Phoibus(gp);
        gp.npc[mapNum][2].worldX = 37 * gp.tileSize;
        gp.npc[mapNum][2].worldY = 53 * gp.tileSize;

        gp.npc[mapNum][3] = new NPC_WiseGirl(gp);
        gp.npc[mapNum][3].worldX = 49 * gp.tileSize;
        gp.npc[mapNum][3].worldY = 51 * gp.tileSize;

        gp.npc[mapNum][4] = new NPC_Shein(gp);
        gp.npc[mapNum][4].worldX = 44 * gp.tileSize;
        gp.npc[mapNum][4].worldY = 51 * gp.tileSize;

        gp.npc[mapNum][5] = new NPC_Ghost1(gp);
        gp.npc[mapNum][5].worldX = 55 * gp.tileSize;
        gp.npc[mapNum][5].worldY = 51 * gp.tileSize;

        gp.npc[mapNum][6] = new NPC_Ghost2(gp);
        gp.npc[mapNum][6].worldX = 52 * gp.tileSize;
        gp.npc[mapNum][6].worldY = 57 * gp.tileSize;

        gp.npc[mapNum][7] = new NPC_Sir(gp);
        gp.npc[mapNum][7].worldX = 58 * gp.tileSize;
        gp.npc[mapNum][7].worldY = 56 * gp.tileSize;

        // NPC FOR MAP 2


    }
}
