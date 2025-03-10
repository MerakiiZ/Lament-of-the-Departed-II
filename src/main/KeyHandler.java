package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import static java.awt.event.KeyEvent.VK_T;

public class KeyHandler implements KeyListener {

    GamePanel gamePanel;
    public boolean upPressed, downPressed, leftPressed, rightPressed, enterPressed;
    //DEBUG
    boolean checkDrawTime;

    public KeyHandler(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        if (gamePanel.inMenu) {
            // MENU NAVIGATION CONTROLS
            if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
                gamePanel.menuState.handleKeyPress(KeyEvent.VK_UP);
            }
            if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
                gamePanel.menuState.handleKeyPress(KeyEvent.VK_DOWN);
            }
            if (code == KeyEvent.VK_ENTER) {
                gamePanel.menuState.handleKeyPress(KeyEvent.VK_ENTER);
            }
        } else {
            // GAME CONTROLS
            if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
                upPressed = true;
            }
            if (code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT) {
                leftPressed = true;
            }
            if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
                downPressed = true;
            }
            if (code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT) {
                rightPressed = true;
            }
            if (code == KeyEvent.VK_P) {
               if (gamePanel.gameState == gamePanel.playState){
                   gamePanel.gameState = gamePanel.pauseState;
               } else if (gamePanel.gameState == gamePanel.pauseState) {
                   gamePanel.gameState = gamePanel.playState;
               }
            }

            if (code == KeyEvent.VK_T){
                if (checkDrawTime == false){
                    checkDrawTime = true;
                }
                else if (checkDrawTime == true){
                    checkDrawTime = false;
                }

            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();

        if (!gamePanel.inMenu) {
            // RELEASE KEYS FOR MOVEMENT
            if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
                upPressed = false;
            }
            if (code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT) {
                leftPressed = false;
            }
            if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
                downPressed = false;
            }
            if (code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT) {
                rightPressed = false;
            }
        }
    }
}
