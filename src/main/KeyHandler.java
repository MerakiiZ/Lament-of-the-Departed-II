package main;

import entity.Entity;
import entity.NPC_Mihr;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import static java.awt.event.KeyEvent.VK_T;

public class KeyHandler implements KeyListener {

    GamePanel gamePanel;
    public boolean upPressed, downPressed, leftPressed, rightPressed, enterPressed;
    //DEBUG
    boolean showDebugText;

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
            if (gamePanel.gameState == gamePanel.playState) {
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
                    gamePanel.gameState = gamePanel.pauseState;
                }
                if (code == KeyEvent.VK_Z) {
                    enterPressed = true;
                }
            }
            // DEBUG
            if (code == KeyEvent.VK_T) {
                if (showDebugText == false) {
                    showDebugText = true;
                } else if (showDebugText == true) {
                    showDebugText = false;
                }
            }
            if (code == KeyEvent.VK_R) {
                int nextMap = (gamePanel.currentMap + 1) % 2; // Toggle between 0 and 1
                gamePanel.tileM.switchMap(nextMap);
            }


        }
        //PAUSE STATE
        if (gamePanel.gameState == gamePanel.pauseState) {
            if (code == KeyEvent.VK_P) {
                gamePanel.gameState = gamePanel.playState;
            }
        }

        //DIALOUGE STATE
        else if (gamePanel.gameState == gamePanel.dialougeState) {
            if (code == KeyEvent.VK_Z) {
                gamePanel.gameState = gamePanel.playState;
            }
        }

        if (gamePanel.ui.showChoice && gamePanel.player.targetNPC != null) {
            // Choice navigation
            if (code == KeyEvent.VK_UP) {
                gamePanel.player.targetNPC.currentChoiceIndex = 0;
                gamePanel.playSE(4);
                return;
            }
            if (code == KeyEvent.VK_DOWN) {
                gamePanel.player.targetNPC.currentChoiceIndex = 1;
                gamePanel.playSE(4);
                return;
            }
            // Choice confirmation
            if (code == KeyEvent.VK_ENTER || code == KeyEvent.VK_SPACE) {
                System.out.println("Choice confirmed!");
                gamePanel.playSE(5);
                gamePanel.player.targetNPC.handleChoice(gamePanel.player.targetNPC.currentChoiceIndex);
                return;
            }
        }

// Separate check for end state
        if (gamePanel.gameState == gamePanel.endState) {
            if (code == KeyEvent.VK_ENTER) {
                gamePanel.resetGame();
            }
            else if (code == KeyEvent.VK_ESCAPE) {
                System.exit(0);
            }
            return;
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
