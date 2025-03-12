package entity;

import main.GamePanel;

import java.util.Random;

public class NPC_Daphni extends Entity{

    private int moveDuration = 120;  // number of frames to move
    private int pauseDuration = 60;  // number of frames to pause
    private int actionCounter = 0;   // general counter

    public NPC_Daphni(GamePanel gp) {
        super(gp);
//        worldX = gp.tileSize * 69;
//        worldY = gp.tileSize * 53;
        direction = "south";
        speed = 1;

        getImage();
    }

    public void getImage() {
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

        idleImage = west0;
    }

    @Override
    public void setAction(){

        actionCounter++;
        // First, decide whether the NPC is moving or pausing:
        if (actionCounter < moveDuration) {
            // NPC is moving
            isMoving = true;
            // Optionally, change direction periodically (or based on your own logic)
            // For example, every 60 frames, change direction randomly:
            if (actionCounter % 60 == 0) {
                int i = (int)(Math.random() * 100) + 1;
                if (i <= 25) {
                    direction = "up";
                } else if (i <= 50) {
                    direction = "down";
                } else if (i <= 75) {
                    direction = "left";
                } else {
                    direction = "right";
                }
            }
        } else if (actionCounter < moveDuration + pauseDuration) {
            // NPC is pausing
            isMoving = false;
            // When pausing, set idle image based on current direction
            idleImage = switch (direction) {
                case "up" -> north0;
                case "down" -> south0;
                case "left" -> west0;
                case "right" -> east0;
                default -> south0;
            };
        } else {
            // Reset counter to restart the cycle
            actionCounter = 0;
        }
    }
}