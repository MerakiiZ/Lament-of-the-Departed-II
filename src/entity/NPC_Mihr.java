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
        dialouges[7] = "Do we have a deal?";
        dialouges[8] = "...";
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

        if (dialougesIndex < 7) {
            gp.ui.currentDialouge = dialouges[dialougesIndex];
            dialougesIndex++;

            // Check if we've passed the introduction point
            if (dialougesIndex > 2) {
                gp.ui.speakerName = "Mihr";
                gp.currentSpeaker = "Mihr";
            }
        }
        else if (dialougesIndex == 7) {
            // Show the choice dialogue
            gp.ui.currentDialouge = dialouges[dialougesIndex];
            gp.ui.showChoice = true; // Assuming you have a showChoice boolean in your UI class
            gp.ui.choiceText = new String[]{"Accept", "Refuse"};
            gp.ui.speakerName = "Mihr";
            gp.currentSpeaker = "Mihr";

            // Don't increment dialougesIndex yet - wait for player choice
        }
        else if (dialougesIndex == 8) {
            // Final dialogue after choice is made
            gp.ui.currentDialouge = dialouges[8];
            gp.ui.speakerName = "Mihr";
            gp.currentSpeaker = "Mihr";
        }
    }

    // Call this method when player makes a choice
    public void handleChoice(int choice) {
        if (dialougesIndex == 7) { // When at the "Do we have a deal?" dialogue
            if (choice == 0) { // Player chose "Accept"
                gp.ui.fadeState = "fading";
                // Trigger any events that should happen after acceptance
            }
            dialougesIndex = 8; // Move to next dialogue
            gp.ui.showChoice = false;
            speak(); // Show the next dialogue
        }
    }
}