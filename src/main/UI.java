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

    // FADINg
    public boolean showChoice = false;
    public String[] choiceText;
    public int choiceIndex = 0;
    public String fadeState = "normal"; // "normal", "fading", "black"
    public float fadeAlpha = 0f;

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


        g2.setFont(text_32);
        g2.setColor(Color.white);
        g2.drawImage(keyImage, gp.tileSize / 2, gp.tileSize / 2, gp.tileSize, gp.tileSize, null);
        g2.drawString("= " + gp.player.hasKey, 74, 60);

        //MESSAGE
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
        if (gp.gameState == gp.playState){

        }

        //PAUSE STATE
        if(gp.gameState == gp.pauseState){
            drawPauseScreen();
        }

        //DIALOUGE STATE
        if (gp.gameState == gp.dialougeState){
            drawDialougeScreen();
        }

        if (fadeState.equals("fading")) {
            fadeAlpha += 0.05f;
            if (fadeAlpha >= 1.0f) {
                fadeAlpha = 1.0f;
                fadeState = "black";
                // Trigger any events that should happen after full fade
            }
            // Draw black rectangle with increasing alpha
            g2.setColor(new Color(0, 0, 0, fadeAlpha));
            g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
        } else if (fadeState.equals("black")) {
            // Keep screen black
            g2.setColor(Color.BLACK);
            g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
        }

//    }
    }

    public void drawPauseScreen(){

        String text = "PAUSED";
        int x;

        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        x = getXforCenteredText(text);

        int y = gp.screenHeight/2;

        g2.drawString(text, x, y);
    }

    public void drawDialougeScreen(){

        // === DRAW PORTRAIT IF SPEAKER IS DAPHNI OR PHOIBUS ===
        int portraitWidth = gp.tileSize * 4;
        int portraitHeight = gp.tileSize * 6;
        int portraitX = gp.screenWidth - portraitWidth - (gp.tileSize * 2);
        int portraitY = gp.screenHeight - portraitHeight - (gp.tileSize * 2);

        // === DRAW DIALOGUE BOX FIRST ===
        int x = gp.tileSize * 2;
        int height = gp.tileSize * 4;
        int y = gp.screenHeight - height - (gp.tileSize / 2);
        int width = gp.screenWidth - (gp.tileSize * 4);

        drawSubWindow(x, y, width, height); // 🟦 Draw box first

        // === NOW DRAW NAME TAG ON TOP ===
        if (!speakerName.isEmpty()) {
            int nameX = x + 20;
            int nameY = y - 10; // slightly above the dialogue box
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 16F));

            Color bgColor = new Color(0, 0, 0, 180);
            int padding = 8;
            int nameWidth = g2.getFontMetrics().stringWidth(speakerName);
            g2.setColor(bgColor);
            g2.fillRoundRect(nameX - padding, nameY - 20, nameWidth + padding * 2, 30, 15, 15);

            g2.setColor(Color.white);
            g2.drawString(speakerName, nameX, nameY);
        }

        // === FINALLY DRAW DIALOGUE TEXT ===
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 12));
        x += gp.tileSize;
        y += gp.tileSize;

        for(String line: currentDialouge.split("\n")){
            g2.drawString(line, x, y);
            y += 40;
        }
        // CHOICE
        if (showChoice) {
            // Draw choice box
            g2.setColor(Color.DARK_GRAY);
            g2.fillRect(x, y + height + 20, width, 60);

            // Draw choice text
            for (int i = 0; i < choiceText.length; i++) {
                if (i == choiceIndex) {
                    g2.setColor(Color.YELLOW); // Highlight selected choice
                } else {
                    g2.setColor(Color.WHITE);
                }
                g2.drawString(choiceText[i], x + 20, y + height + 50 + (i * 30));
            }
        }
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
