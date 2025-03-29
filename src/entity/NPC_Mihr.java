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
        dialouges[3] = "But maybe...you can help...\nCan't you?";
        dialouges[4] = "I heard from the shadows.\nYou derailed the train of Souls, defeated its conductor...\n";
        dialouges[5] = "I can help you, you know...";
        dialouges[6] = "All you have to do... is help me leave this forsaken place.";
        dialouges[7]= "Do we have a deal?";
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
            dialougesIndex++;

            // Check if we've passed the introduction point (assuming introduction is at index X)
            // For example, if introduction finishes at index 4:
            if (dialougesIndex > 2) {  // Change this number to match when introduction ends
                gp.ui.speakerName = "Mihr"; // Replace with actual name
                gp.currentSpeaker = "Mihr"; // Replace with actual name
            }
        } else {
            // Always show dialogue index 8 after finishing the conversation
            gp.ui.currentDialouge = dialouges[8];
            // Make sure name is set for final dialogue too
            gp.ui.speakerName = "Mihr";
            gp.currentSpeaker = "Mihr";
        }
    }
}
