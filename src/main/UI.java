package main;

import object.OBJ_Heart;
import object.OBJ_Key;
import object.SuperObject;

import java.awt.*;
import java.awt.image.BufferedImage;

public class UI {

    GamePanel gp;
    Graphics2D g2;
    Font text_32;
    BufferedImage keyImage;
    BufferedImage heart_full, heart_half, heart_blank;
    public boolean messageOn = false;
    public String message = " ";
    int messageCounter = 0;
//    public boolean gameFinished = false;

    public UI(GamePanel gp){
        this.gp = gp;

        text_32 = new Font("Press Start 2P", Font.PLAIN, 21);
        OBJ_Key key = new OBJ_Key(gp);
        keyImage = key.image;

        // HUD OBJECTS
        SuperObject heart = new OBJ_Heart(gp);
        heart_full = heart.image;
        heart_half = heart.image2;
        heart_blank = heart.image3;
    }

    public void showMessage(String text){

        message = text;
        messageOn = true;
    }

    public void draw (Graphics2D g2) {

        this.g2 = g2;

//        if(gameFinished == true) {

//        g2.setFont(text_32);
//        g2.setColor(Color.color);

//        String text;
//        int textLength;
//        int x;
//        int y;

//        text = "To be continued";
//        textLength = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();

//        int x = gp.screenWidth/2 - textLength/2;
//        int y = gp.screeHeight/2 - (gp.tileSize*3);
//        g2.drawString (text, x, y);
//        }
//        else {

        drawPlayerLife();

        //ADJUSTMENTS KEYS UNDER LIFE
        int keyX = gp.tileSize / 2;  // Same X as hearts
        int keyY = (gp.tileSize / 2) + gp.tileSize + 10;
        int textX = keyX + gp.tileSize + 5;  // Slightly to the right of the key
        int textY = keyY + (gp.tileSize / 2) + 5; // Vertically centered with the key

        g2.setFont(text_32);
        g2.setColor(Color.white);
        g2.drawImage(keyImage, keyX, keyY, gp.tileSize, gp.tileSize, null);
        g2.drawString("= " + gp.player.hasKey, textX, textY);


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
        // PLAYSTATE
        if (gp.gameState == gp.playState){
            drawPlayerLife();
        }
        if(gp.gameState == gp.pauseState){
            drawPlayerLife();
            drawPauseScreen();

        }
//    }
    }

    public void drawPlayerLife() {

        int x = gp.tileSize / 2;
        int y = gp.tileSize / 2;
        int i = 0;

        // DRAW BLANK HEART
        while (i < gp.player.maxLife / 2)
        {
            g2.drawImage(heart_blank, x, y, null);
            i++;
            x += gp.tileSize;
        }

        // RESET
        x = gp.tileSize / 2;
        y = gp.tileSize / 2;
        i = 0;

        // DRAWE CURRENT LIFE
        while(i < gp.player.life){
            g2.drawImage(heart_half, x, y, null);
            i++;
            if(i<gp.player.life){
                g2.drawImage(heart_full, x ,y, null);
            }
            i++;
            x += gp.tileSize;
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

    public int getXforCenteredText(String text){
        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = gp.screenWidth/2 - length/2;

        return x;
    }
}
