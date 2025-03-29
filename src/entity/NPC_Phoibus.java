package entity;

import main.GamePanel;

public class NPC_Phoibus extends Entity {

    public NPC_Phoibus(GamePanel gp) {
        super(gp);

        direction = "north";
        speed = 1;

        getImage();
        setDialouge();
    }

    public void getImage() {
        north0 = setup("/npc/phoibus_sprite");
        south0 = setup("/npc/phoibus_sprite");
        west0 = setup("/npc/phoibus_sprite");
        east0 = setup("/npc/phoibus_sprite");

        idleImage = west0;
    }

    public void setDialouge(){

        dialouges[0] = "You there...\nanother lost soul wandering the wreckage?\nHah. Not much of a talker, are you?";
        dialouges[1] = "Good. Words do nothing here but echo.";
        dialouges[2] = "Do you know what it's like\nto scream your innocence, only to have\nyour voice drowned by the roar of the\ncrowd? ";
        dialouges[3] = "To stand at the edge of the noose,\nfeeling the rope tighten before\nthe world goes black?";
        dialouges[4] = "I was a fool.\nI thought justice was something real.\n";
        dialouges[5] = "Something sharp enough to cut through lies.\nBut the truth is a brittle thing.\nIt breaks under the weight of men’s\nfears and ambitions";
        dialouges[6] = "And now?\nNow, I ride this train with the rest of the\ndamned, waiting for peace that will never\ncome";
        dialouges[7]= "Tell me…\nif I was truly innocent,\nwhy does my soul still linger?";
        dialouges[8]= "...";
    }

    @Override
    public void setAction(){
        isMoving = false;
    }

    @Override
    public void speak(){

        if (dialougesIndex < 8) {
            gp.ui.currentDialouge = dialouges[dialougesIndex];
            dialougesIndex++;
        } else {
            // Always show dialogue index 8 after finishing the conversation.
            gp.ui.currentDialouge = dialouges[8];
        }
    }
}
