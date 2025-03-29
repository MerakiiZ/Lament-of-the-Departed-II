package entity;

import main.GamePanel;

public class NPC_Ghost2 extends Entity{

    public NPC_Ghost2(GamePanel gp) {
        super(gp);

        direction = "south";
        speed = 1;

        getImage();
        setDialouge();
    }

    public void getImage() {
        north0 = setup("/npc/npc3_ghost");
        south0 = setup("/npc/npc3_ghost");
        west0 = setup("/npc/npc3_ghost");
        east0 = setup("/npc/npc3_ghost");

        idleImage = west0;
    }

    public void setDialouge(){

        dialouges[0] = "hehe";
        dialouges[1] = "(You could here her slightly giggling, to no one in particular)";
        dialouges[2] = "HEHEHEHEHE";
        dialouges[3] = "Can you see them outside?";
        dialouges[4] = "The eyes... can you see them?";
        dialouges[5] = "They're trying to get inside! HAHA! \nLook at them clawing at the windows.";
        dialouges[6] = "They look hungry...";
        dialouges[7]= "Hehehe";
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
