package entity;

import main.GamePanel;

public class NPC_Ghost1 extends Entity{

    public NPC_Ghost1(GamePanel gp) {
        super(gp);

        direction = "south";
        speed = 1;

        getImage();
        setDialouge();
    }

    public void getImage() {
        north0 = setup("/npc/npc2_ghost");
        south0 = setup("/npc/npc2_ghost");
        west0 = setup("/npc/npc2_ghost");
        east0 = setup("/npc/npc2_ghost");

        idleImage = west0;
    }

    public void setDialouge(){

        dialouges[0] = "...";
        dialouges[1] = "Huhuhuhu";
        dialouges[2] = "Why? Why? WHY?";
        dialouges[3] = "WHY WHY WHY WHY?";
        dialouges[4] = "WHY HAVE YOU DONE THIS?!";
        dialouges[5] = "The train... \nOh the train!! Stopped!\nWe're surrounded by the abyss!";
        dialouges[6] = "YOU FOOL OF A GIRL! HOW DARE YOU?!";
        dialouges[7]= "Mama... Papa...oh...";
        dialouges[8]= "...";
    }

    @Override
    public void setAction(){
        isMoving = false;
    }

    @Override
    public void speak(){
        gp.ui.speakerName = "Soul";
        gp.currentSpeaker = "Soul";

        if (dialougesIndex < 8) {
            gp.ui.currentDialouge = dialouges[dialougesIndex];
            dialougesIndex++;
        } else {
            // Always show dialogue index 8 after finishing the conversation.
            gp.ui.currentDialouge = dialouges[8];
        }
    }
}
