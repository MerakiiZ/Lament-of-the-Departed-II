package main;

import object.OBJ_Key;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Objects;

public class UI {

    GamePanel gp;
    Graphics2D g2;
    Font text_32;
    BufferedImage keyImage;
    public boolean messageOn = false;
    public String message = " ";
    public boolean dialogueFinished = false;
    int messageCounter = 0;
//    public boolean gameFinished = false;
    public String currentDialouge = "";
    public String speakerName= "";

    // CHOICES
    public boolean showChoice = false;
    public String[] choiceText;
    public int choiceIndex = 0;

    // FADE
    public String fadeState = "none"; // "none", "fading", "black"
    public float fadeAlpha = 0f; // O is transparent, 1 is opaque
    private Runnable postFadeAction = null;

    public UI(GamePanel gp){
        this.gp = gp;

        text_32 = new Font("Press Start 2P", Font.PLAIN, 21);
        OBJ_Key key = new OBJ_Key(gp);
        keyImage = key.image;


    }

    public void showMessage(String text){

        message = text;
        messageOn = true;
    }

    public void draw (Graphics2D g2) {

        this.g2 = g2;


        // Draw regular UI elements first
        g2.setFont(text_32);
        g2.setColor(Color.white);
        g2.drawImage(keyImage, gp.tileSize / 2, gp.tileSize / 2, gp.tileSize, gp.tileSize, null);
        g2.drawString("= " + gp.player.hasKey, 74, 60);

        if (messageOn == true) {

            g2.setFont(g2.getFont().deriveFont(15F));
            g2.drawString(message, gp.tileSize / 2, gp.tileSize * 5);

            messageCounter++;

            if (messageCounter > 120) {
                messageCounter = 0;
                messageOn = false;
            }
        }

        //PLAY STATE
        if (gp.gameState == gp.playState) {

        }

        //END STATE
        if (gp.gameState == gp.endState) {
            drawEndScreen();
        }

        //PAUSE STATE
        if (gp.gameState == gp.pauseState) {
            drawPauseScreen();
        }

        //DIALOUGE STATE
        if (gp.gameState == gp.dialougeState) {
            drawDialougeScreen();
        }

        // Handle fade effect (drawn last)
        if (gp.gameState != gp.endState) {
            drawFade(g2);
        }
    }

    public void drawPauseScreen(){

        String text = "PAUSED";
        int x;

        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        x = getXforCenteredText(text);

        int y = gp.screenHeight/2;

        g2.drawString(text, x, y);
    }

    public void drawDialougeScreen() {
        // === DIALOGUE BOX SETTINGS ===
        int x = gp.tileSize * 2;
        int width = gp.screenWidth - (gp.tileSize * 4);
        int height = gp.tileSize * 4;
        int y = gp.screenHeight - height - (gp.tileSize / 2);

        // === DRAW DIALOGUE BOX ===
        drawSubWindow(x, y, width, height);

        // === DRAW SPEAKER NAME ===
        if (!speakerName.isEmpty()) {
            int nameX = x + 20;
            int nameY = y - 10;
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 16F));

            Color bgColor = new Color(0, 0, 0, 180);
            int padding = 8;
            int nameWidth = g2.getFontMetrics().stringWidth(speakerName);
            g2.setColor(bgColor);
            g2.fillRoundRect(nameX - padding, nameY - 20, nameWidth + padding * 2, 30, 15, 15);

            g2.setColor(Color.white);
            g2.drawString(speakerName, nameX, nameY);
        }

        // === DRAW DIALOGUE TEXT ===
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 12));
        int textX = x + gp.tileSize;
        int textY = y + gp.tileSize;

        for(String line : currentDialouge.split("\n")) {
            g2.drawString(line, textX, textY);
            textY += 40;
        }

        // === DRAW CHOICE BOX (INSIDE DIALOGUE WINDOW) ===
        if (showChoice && gp.player.targetNPC != null) {
            // Draw choice box
            int boxX = x + 20;
            int boxY = y + height - 90;
            int boxWidth = width - 40;

            drawSubWindow(boxX, boxY, boxWidth, 50);

            // Draw choices
            for (int i = 0; i < gp.player.targetNPC.choiceOptions.length; i++) {
                if (i == gp.player.targetNPC.currentChoiceIndex) {
                    g2.setColor(Color.RED);
                    g2.fillOval(boxX + 10, boxY + 15 + (i * 20), 8, 8);
                } else {
                    g2.setColor(Color.WHITE);
                }
                g2.drawString(gp.player.targetNPC.choiceOptions[i], boxX + 25, boxY + 20 + (i * 20));
            }
        }
    }

    public void closeDialogue() {
        currentDialouge = null;
        showChoice = false; // Important!
        if (gp.player != null) {
            gp.player.clearTargetNPC();
        }
        gp.gameState = gp.playState;
    }

    public void startFade(Runnable action) {
        fadeAlpha = 0f;
        fadeState = "fading";
        postFadeAction = action; // Store action to run after fade
    }

    public void resetFade() {
        fadeAlpha = 0f;
        fadeState = "none";
        postFadeAction = null;
    }

//    public void drawFade(Graphics2D g2) {
//        System.out.println("Current gameState: " + gp.gameState);
//        System.out.println("Current fadeState: " + fadeState + ", alpha: " + fadeAlpha);
//        if (!fadeState.equals("none")) {
//            g2.setColor(new Color(0, 0, 0, fadeAlpha));
//            g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
//
//            if (fadeState.equals("fading")) {
//                fadeAlpha += 0.05f;
//                if (fadeAlpha >= 1.0f) {
//                    fadeState = "complete";
//                    if (postFadeAction != null) {
//                        postFadeAction.run();
//                    }
//                }
//            }
//        }
//    }

    public void drawFade(Graphics2D g2) {
        if (!fadeState.equals("none")) {
            // Always draw the fade overlay
            g2.setColor(new Color(0, 0, 0, fadeAlpha));
            g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

            if (fadeState.equals("fading")) {
                fadeAlpha += 0.05f;
                if (fadeAlpha >= 1.0f) {
                    fadeState = "complete"; // Changed from "black" to "complete"
                    if (postFadeAction != null) {
                        postFadeAction.run();
                        postFadeAction = null;
                    }
                }
            }
        }
    }


    // END SCREEN
    public void drawEndScreen() {
        g2.setColor(Color.BLACK);
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
        //Gradient background
        GradientPaint gradient = new GradientPaint(0, 0, new Color(10, 10, 30),
                0, gp.screenHeight, new Color(0, 0, 0));
        g2.setPaint(gradient);
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);


            // Title
            g2.setFont(new Font("Serif", Font.BOLD, 72));
            g2.setColor(new Color(200, 150, 50));

            String title = "Journey's End";
            int x = getXForCenteredText(title, g2);
            int y = gp.screenHeight / 3;
            g2.drawString(title, x, y);

            // Ending message
            g2.setFont(new Font("Serif", Font.PLAIN, 24));
            g2.setColor(Color.WHITE);

            String[] lines = {
                    "You helped Mihr escape the forsaken realm,",
                    "fulfilling an ancient pact between mortals and spirits.",
                    "",
                    "As the last shadows fade, you feel a strange peace",
                    "settle over the land...",
                    "",
                    "Press Enter to return to title"
            };

            y += 80;
            for (String line : lines) {
                if (!line.isEmpty()) {
                    x = getXForCenteredText(line, g2);
                    g2.drawString(line, x, y);
                }
                y += 30;
            }
    }


    private int getXForCenteredText(String text, Graphics2D g2) {
        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        return gp.screenWidth/2 - length/2;
    }


    public void drawSubWindow(int x, int y, int width, int height){

        Color c = new Color(0, 0,0, 200);
        g2.setColor(c);
        g2.fillRoundRect(x, y, width, height,35,35);

        c = new Color(255, 255, 255);
        g2.setColor(c);
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(x+5, y+5, width-10, height-10, 25,25);
    }

    public int getXforCenteredText(String text){
        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = gp.screenWidth/2 - length/2;

        return x;
    }
}
