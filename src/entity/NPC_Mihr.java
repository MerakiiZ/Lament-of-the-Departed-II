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

        dialouges[0] = "... My, My... \nA Mortal? Here?";
        dialouges[1] = "How surprising.";
        dialouges[2] = "My name is Mihr...\nA pitiful creature, as you can see...";
        dialouges[3] = "";
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
    public void speak() {
        // Default to "???" if not introduced yet
        gp.ui.speakerName = "???";
        gp.currentSpeaker = "???";

        if (dialougesIndex < 8) {
            gp.ui.currentDialouge = dialouges[dialougesIndex];

            // Check if this is the line where character introduces themselves
            if (dialouges[dialougesIndex].contains("My name is")) {  // or some other identifying text
                gp.ui.speakerName = "Character Name"; // Replace with actual name
                gp.currentSpeaker = "Character Name"; // Replace with actual name
            }

            dialougesIndex++;
        } else {
            // Always show dialogue index 8 after finishing the conversation
            gp.ui.currentDialouge = dialouges[8];
            // Make sure name is set for final dialogue too
            gp.ui.speakerName = "Character Name";
            gp.currentSpeaker = "Character Name";
        }
    }
}
