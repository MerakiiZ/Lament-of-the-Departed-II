package entity;

import main.GamePanel;

public class NPC_Mihr extends Entity {

    public NPC_Mihr(GamePanel gp) {
        super(gp);

        direction = "north";
        speed = 1;

        getImage();
        setDialouge();
    }

    public void getImage() {
        north0 = setup("/npc/mihr_sprite");
        south0 = setup("/npc/mihr_sprite");
        west0 = setup("/npc/mihr_sprite");
        east0 = setup("/npc/mihr_sprite");

        idleImage = west0;
    }

    public void setDialouge(){

        dialouges[0] = "... Are you proud of yourself?";
        dialouges[1] = "The train has been stopped...";
        dialouges[2] = "The souls are restless.";
        dialouges[3] = "Do you hear them? Do you hear their cries?";
        dialouges[4] = "You derailed this train, now you must fix it.";
        dialouges[5] = "Unless... of course, you answer to the wrath of the souls aboard this train.";
        dialouges[6] = "Goodbye then, Penelope.";
        dialouges[7]= "...";
        dialouges[8]= "...";
    }

    @Override
    public void setAction(){
        isMoving = false;
    }

    @Override
    public void speak(){
        gp.ui.speakerName = "???";
        gp.currentSpeaker = "???";

        if (dialougesIndex < 8) {
            gp.ui.currentDialouge = dialouges[dialougesIndex];
            dialougesIndex++;
        } else {
            // Always show dialogue index 8 after finishing the conversation.
            gp.ui.currentDialouge = dialouges[8];
        }
    }
}
