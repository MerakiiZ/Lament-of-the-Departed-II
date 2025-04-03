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
        dialouges[8] = "(The man laughs)\nToo Bad...";
        dialouges[9] = "You'll be stuck here with me.";
        dialouges[10] = "...";
    }

    @Override
    public void setAction(){
        isMoving = false;
    }

    @Override
    public void speak() {
        // Set speaker name
        gp.ui.speakerName = dialougesIndex > 2 ? "Mihr" : "???";
        gp.currentSpeaker = dialougesIndex > 2 ? "Mihr" : "???";

        // Only show choices at the specific dialogue index (7)
        if (dialougesIndex == 7) { // Choice dialogue
            setupChoices(new String[]{"Accept", "Refuse"});
            gp.ui.showChoice = true;
            gp.ui.currentDialouge = dialouges[dialougesIndex];
            return;
        }
        else if (dialougesIndex < dialouges.length) {
            gp.ui.currentDialouge = dialouges[dialougesIndex];
            gp.ui.showChoice = false; // Ensure choices are hidden for other dialogues
            dialougesIndex++;
        }
        else {
            // After all dialogues, show the final one
            gp.ui.currentDialouge = dialouges[dialouges.length - 1];
            gp.ui.showChoice = false;
            // Always show dialogue index 8 after finishing the conversation.
            gp.ui.currentDialouge = dialouges[8];
        }
    }

    @Override
    public void handleChoice(int choice) {
        if (!isAwaitingChoice) return;

        System.out.println("Mihr handling choice: " + choice);
        gp.ui.showChoice = false;
        isAwaitingChoice = false;

        if (choice == 0) { // Accept
            gp.ui.startFade(() -> {
                gp.gameState = gp.endState;
                gp.playEndMusic();
                gp.ui.fadeAlpha = 0f; // Reset fade so end screen is visible
                gp.ui.fadeState = "none";
            });
        } else {
            // Refuse logic
            dialougesIndex++;
            speak();
        }
    }

}