package entity;

import main.GamePanel;

import java.awt.image.BufferedImage;
import java.util.Random;

public class NPC_Daphni extends Entity{

    BufferedImage idleImage;
    public NPC_Daphni(GamePanel gp){
        super(gp);

        direction = "down";
        speed = 1;
        getImage();


    }

    public void getImage() {
        north0 = setup("/npc/spr_npc_daphni_walk_north_0");
        north1 = setup("/npc/spr_npc_dapnhi_walk_north_1");
        north2 = setup("/npc/spr_npc_daphni_walk_north_2");
        north3 = setup("/npc/spr_npc_daphni_walk_north_3");

        east0 = setup("/npc/spr_npc_daphni_walk_east_0");
        east1 = setup("/npc/spr_npc_dapni_walk_east_1");
        east2 = setup("/npc/spr_npc_daphni_walk_east_2");
        east3 = setup("/npc/spr_npc_daphni_east_3");

        west0 = setup("/npc/spr_npc_daphni_walk_west_0");
        west1 = setup("/npc/spr_npc_dapni_walk_west_1");
        west2 = setup("/npc/spr_npc_daphni_walk_west_0");
        west3 = setup("/npc/spr_npc_daphni_west_3");

        south0 = setup("/npc/spr_npc_daphni_walk_south_0");
        south1 = setup("/npc/spr_npc_daphni_walk_south_1");
        south2 = setup("/npc/spr_npc_daphni_south_2");
        south3 = setup("/npc/spr_npc_daphni_south_3");
    }

    public void setAction() {

//        actionLockCounter++;


            Random random = new Random();
            int i = random.nextInt(100) + 1;

            if(i <= 25) {
                direction = "up";
            }
            if(i > 25 && i <= 50) {
                direction = "down";
            }
            if (i > 50 && i <= 75) {
                direction = "right";
            }
            if (i > 75 && i <= 100){
                direction = "left";
            }


//        actionLockCounter = 0;


    }

}
