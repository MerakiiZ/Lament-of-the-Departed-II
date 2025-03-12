package entity;

import main.GamePanel;

public class NPC_Acacius extends Entity{

    public NPC_Acacius(GamePanel gp) {
        super(gp);

        direction = "south";
        speed = 1;

        getImage();
    }

    public void getImage() {
        north0 = setup("/npc/npc1_north");
        south0 = setup("/npc/npc1_south");
        west0 = setup("/npc/npc1_west");
        east0 = setup("/npc/npc1_east");

        idleImage = west0;
    }

    @Override
    public void setAction(){
        isMoving = false;
    }

}