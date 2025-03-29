package entity;

import main.GamePanel;

public class NPC_WiseGirl extends Entity{

    public NPC_WiseGirl(GamePanel gp) {
        super(gp);

        direction = "south";
        speed = 1;

        getImage();
        setDialouge();
    }

    public void getImage() {
        south0 = setup("/npc/wise_girl_ghost");

        idleImage = south0;
    }

    public void setDialouge(){

        dialouges[0] = "You're quiet. Smart move.\nSometimes it's better to observe before\nspeaking";
        dialouges[1] = "This place reminds me of a labyrinth...\nendless paths, hidden choices,\nand a price for every wrong turn.";
        dialouges[2] = "Everyone here is looking for something.\nRedemption, closure...\nmaybe even a second chance.";
        dialouges[3] = "But if you ask me?\nKnowing when to let go is the real wisdom.";
        dialouges[4] = "If you get the chance to change the\npast—think twice.\nSome threads aren’t meant to be pulled.";
        dialouges[5] = "I’ve walked through darker places than\nthis.\nMade it out, too. (smirks)";
        dialouges[6] = "Mostly thanks to a seaweed-brained idiot...\nbut that’s another story.";
        dialouges[7]= "...";
        dialouges[8]= "You remind me of someone.\nBrave. Stubborn.\nSilent, though... he never stopped talking.\n(laughs)";
        dialouges[9]=  "Take care, okay?\nEven the wisest can get lost here.";
    }

    @Override
    public void setAction(){
        isMoving = false;
    }

    int dialoguesIndex = 0;
    int postDialogueClicks = 0;
    boolean easterEggUnlocked = false;

    @Override
    public void speak() {

        gp.ui.speakerName = "Wise Girl";
        gp.currentSpeaker = "Wise Girl";

        if (dialoguesIndex < 7) {
            gp.ui.currentDialouge = dialouges[dialoguesIndex];
            dialoguesIndex++;
        } else if (!easterEggUnlocked) {
            postDialogueClicks++;
            if (postDialogueClicks == 6) {
                easterEggUnlocked = true;
                dialoguesIndex = 8;
                gp.ui.currentDialouge = dialouges[dialoguesIndex];
                dialoguesIndex++;
            } else {
                gp.ui.currentDialouge = dialouges[7];
            }
        } else {
            if (dialoguesIndex < 10) {
                gp.ui.currentDialouge = dialouges[dialoguesIndex];
                dialoguesIndex++;
            } else {
                gp.ui.currentDialouge = dialouges[7];
            }
        }
    }


}