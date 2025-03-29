package entity;

import main.GamePanel;
import main.JumpScare;

public class NPC_Shein extends Entity {

    public boolean postJumpscare = false;

    public NPC_Shein(GamePanel gp) {
        super(gp);

        direction = "south";
        speed = 1;

        getImage();
        setDialouge();
    }

    public void getImage() {
        south0 = setup("/npc/shein_ghost");
        idleImage = south0;
    }

    public void setDialouge() {
        dialouges[0] = "Oh hey! Is that you?";
        dialouges[1] = "Aw, I’m just a friendly NPC...\nNothing suspicious here.";
        dialouges[2] = "Go on, press that interact button again.\nI totally won’t do anything weird.";
        dialouges[3] = "Actually, you're kinda brave.";
        dialouges[4] = "Or stupid.";
        dialouges[5] = "Anyway, guess what?";
        dialouges[6] = "I made a jumpscare of you in my game!";
        dialouges[7] = "So I thought...\nwhy not make one of me in yours?";
        dialouges[8] = "Just kidding. I’m totally normal.";
        dialouges[9] = "*stares at you.*";
        dialouges[10] = "...";
        dialouges[11] = "...";
        dialouges[12] = "RAAAAAAAAAAAAAAAAAAAAAAHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHH!!!";

        // Post-jumpscare dialogue
        dialouges[13] = "AHAHAHAHAHA!";
        dialouges[14] = "You should've seen your face!";
        dialouges[15] = "Totally worth it.";
        dialouges[16] = "...";
    }

    @Override
    public void setAction() {
        isMoving = false;
    }

    @Override
    public void speak() {
        gp.ui.speakerName = "Shein";
        gp.currentSpeaker = "Shein";

        if (!dialogueFinished && !postJumpscare) {
            if (dialougesIndex <= 12) {
                gp.ui.currentDialouge = dialouges[dialougesIndex];
                dialougesIndex++;

                if (dialougesIndex == 13) {
                    dialogueFinished = true;
                }
            } else {
                dialogueFinished = true;
            }

        } else if (dialogueFinished && !postJumpscare) {
            // Jumpscare trigger
            if (gp.keyH.enterPressed) {
                new JumpScare();
                gp.playSE(5);
                dialogueFinished = false;
                postJumpscare = true;
                dialougesIndex = 13; // jump to post-jumpscare dialogue
            }

        } else if (postJumpscare) {
            // Post-jumpscare dialogue
            if (dialougesIndex <= 16) {
                gp.ui.currentDialouge = dialouges[dialougesIndex];
                dialougesIndex++;
            } else {
                // Loop "..." after everything
                gp.ui.currentDialouge = dialouges[16];
            }
        }
    }
}
