package main;

import javax.swing.*;
import java.awt.*;

public class EventHandler {
    GamePanel gp;
    EventRect[][][] eventRects;
    int eventRectDefaultX = 23;
    int eventRectDefaultY = 23;
    boolean eventActive = false;
    Point currentEventLocation = null;
    int currentEventMap = 0;

    public EventHandler(GamePanel gp) {
        this.gp = gp;
        eventRects = new EventRect[gp.maxMap][gp.maxWorldCol][gp.maxWorldRow];
        initializeEventRects();
    }

    private void initializeEventRects() {
        for (int map = 0; map < gp.maxMap; map++) {
            for (int col = 0; col < gp.maxWorldCol; col++) {
                for (int row = 0; row < gp.maxWorldRow; row++) {
                    eventRects[map][col][row] = new EventRect();
                    eventRects[map][col][row].x = eventRectDefaultX;
                    eventRects[map][col][row].y = eventRectDefaultY;
                    eventRects[map][col][row].width = 2;
                    eventRects[map][col][row].height = 2;
                    eventRects[map][col][row].eventRectDefaultX = eventRectDefaultX;
                    eventRects[map][col][row].eventRectDefaultY = eventRectDefaultY;
                }
            }
        }

        // teleport setup (from map 0 to map 1)
        setupEvent(0, 4, 52, "any", "Strange...");
        setupTeleport(0, 4, 52, "any",
                1, 35, 52, "Step into the glowing portal? (Press Z)");


    }

    public void checkEvent() {
        // Handle pending teleport
        if (eventActive && currentEventLocation != null) {
            EventRect currentEvent = eventRects[currentEventMap][currentEventLocation.x][currentEventLocation.y];

            if (currentEvent.teleportPending) {
                if (gp.keyH.enterPressed) {
                    startTeleport(currentEvent);
                    return;
                }

                // Check if player moved away
                if (!isPlayerInEventArea(currentEventMap, currentEventLocation.x, currentEventLocation.y)) {
                    exitEvent();
                }
                return;
            }

            // Normal event exit check
            if (!isPlayerInEventArea(currentEventMap, currentEventLocation.x, currentEventLocation.y)) {
                exitEvent();
                return;
            }
        }

        // Check for new events
        if (!eventActive && !gp.isTeleporting) {
            checkForEventsNearPlayer();
        }
    }


    private void checkForEventsNearPlayer() {
        // Check 3x3 area around player
        int playerCol = gp.player.worldX / gp.tileSize;
        int playerRow = gp.player.worldY / gp.tileSize;

        for (int col = playerCol - 1; col <= playerCol + 1; col++) {
            for (int row = playerRow - 1; row <= playerRow + 1; row++) {
                if (isValidPosition(gp.currentMap, col, row) &&
                        eventRects[gp.currentMap][col][row].hasEvent) {

                    if (hit(gp.currentMap, col, row,
                            eventRects[gp.currentMap][col][row].eventDirection)) {
                        testEvent(eventRects[gp.currentMap][col][row]);
                        return; // Only trigger one event at a time
                    }
                }
            }
        }
    }

    private boolean isPlayerInEventArea(int map, int col, int row) {
        if (gp.player == null || !isValidPosition(map, col, row)) return false;

        // Save original positions
        int playerSolidX = gp.player.solidArea.x;
        int playerSolidY = gp.player.solidArea.y;

        // Calculate absolute positions
        gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
        gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;
        eventRects[map][col][row].x = col * gp.tileSize + eventRects[map][col][row].eventRectDefaultX;
        eventRects[map][col][row].y = row * gp.tileSize + eventRects[map][col][row].eventRectDefaultY;

        // Check intersection with specific EventRect
        boolean stillInArea = gp.player.solidArea.intersects(
                new Rectangle(
                        eventRects[map][col][row].x,
                        eventRects[map][col][row].y,
                        eventRects[map][col][row].width,
                        eventRects[map][col][row].height
                )
        );

        // Reset positions
        gp.player.solidArea.x = playerSolidX;
        gp.player.solidArea.y = playerSolidY;
        eventRects[map][col][row].x = eventRects[map][col][row].eventRectDefaultX;
        eventRects[map][col][row].y = eventRects[map][col][row].eventRectDefaultY;

        return stillInArea;
    }

    public boolean hit(int map, int col, int row, String reqDirection) {
        if (gp.player == null || !isValidPosition(map, col, row)) return false;

        // Save original positions
        int playerSolidX = gp.player.solidArea.x;
        int playerSolidY = gp.player.solidArea.y;

        // Calculate absolute positions
        gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
        gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;
        eventRects[map][col][row].x = col * gp.tileSize + eventRects[map][col][row].eventRectDefaultX;
        eventRects[map][col][row].y = row * gp.tileSize + eventRects[map][col][row].eventRectDefaultY;

        boolean hit = false;

        // Create Rectangle for intersection test
        Rectangle eventArea = new Rectangle(
                eventRects[map][col][row].x,
                eventRects[map][col][row].y,
                eventRects[map][col][row].width,
                eventRects[map][col][row].height
        );

        if (gp.player.solidArea.intersects(eventArea)) {
            if (reqDirection.equals("any") ||
                    gp.player.direction.equals(reqDirection)) {
                hit = true;
                currentEventLocation = new Point(col, row);
                currentEventMap = map;
                eventActive = true;
            }
        }

        // Reset positions
        gp.player.solidArea.x = playerSolidX;
        gp.player.solidArea.y = playerSolidY;
        eventRects[map][col][row].x = eventRects[map][col][row].eventRectDefaultX;
        eventRects[map][col][row].y = eventRects[map][col][row].eventRectDefaultY;

        return hit;
    }

    private boolean isValidPosition(int map, int col, int row) {
        return map >= 0 && map < gp.maxMap &&
                col >= 0 && col < gp.maxWorldCol &&
                row >= 0 && row < gp.maxWorldRow;
    }

    public void testEvent(EventRect event) {

        if (event.isTeleport) {
            gp.ui.currentDialouge = event.eventMessage;
            gp.gameState = gp.dialougeState;
            event.teleportPending = true;

            // Set up teleport confirmation
            gp.keyH.enterPressed = false; // Reset enter key state
        } else {
            // Regular dialogue event
            gp.ui.currentDialouge = event.eventMessage;
            gp.gameState = gp.dialougeState;
        }
    }

    private void setupEvent(int map, int col, int row, String direction, String message) {
        if (isValidPosition(map, col, row)) {
            eventRects[map][col][row].hasEvent = true;
            eventRects[map][col][row].eventDirection = direction;
            eventRects[map][col][row].eventMessage = message;
        }
    }

    public void exitEvent() {
        gp.gameState = gp.playState;
        gp.ui.currentDialouge = "";
        eventActive = false;
        currentEventLocation = null;
    }

    // TELEPORTING EVENT

    public void setupTeleport(int map, int col, int row, String direction,
                              int targetMap, int targetCol, int targetRow, String message) {
        if (isValidPosition(map, col, row)) {
            eventRects[map][col][row].hasEvent = true;
            eventRects[map][col][row].isTeleport = true;
            eventRects[map][col][row].eventDirection = direction;
            eventRects[map][col][row].teleportMap = targetMap;
            eventRects[map][col][row].teleportCol = targetCol;
            eventRects[map][col][row].teleportRow = targetRow;
            eventRects[map][col][row].eventMessage = message;
        }
    }

    public void startTeleport(EventRect event) {
        // Start teleport sequence
        synchronized(gp.teleportLock) {
            gp.isTeleporting = true;
            gp.teleportAlpha = 0f;
        }

        // Play teleport sound
        gp.playSE(5);

        // Execute teleport in background thread
        new Thread(() -> {
            try {
                // PHASE 1: Fade out (0 → 1.0 alpha)
                while (true) {
                    synchronized(gp.teleportLock) {
                        gp.teleportAlpha = Math.min(1.0f, gp.teleportAlpha + gp.TELEPORT_FADE_SPEED);
                        if (gp.teleportAlpha >= 1.0f) break;
                    }
                    Thread.sleep(16); // ~60fps
                }

                // PHASE 2: Actual map transition
                gp.currentMap = event.teleportMap;
                gp.player.worldX = event.teleportCol * gp.tileSize;
                gp.player.worldY = event.teleportRow * gp.tileSize;

                // Force immediate screen update
                SwingUtilities.invokeLater(() -> {
                    gp.drawToTempScreen();
                    gp.drawToScreen();
                });

                // PHASE 3: Fade in (1.0 → 0 alpha)
                while (true) {
                    synchronized(gp.teleportLock) {
                        gp.teleportAlpha = Math.max(0f, gp.teleportAlpha - gp.TELEPORT_FADE_SPEED);
                        if (gp.teleportAlpha <= 0f) break;
                    }
                    Thread.sleep(16);
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                // PHASE 4: Clean up
                synchronized(gp.teleportLock) {
                    gp.isTeleporting = false;
                }
                event.teleportPending = false;
                gp.gameState = gp.playState;
                gp.playSE(6); // Arrival sound
            }
        }).start();
    }

    private void completeTeleport(EventRect event) {
        gp.isTeleporting = false;
        event.teleportPending = false;
        gp.gameState = gp.playState;
        exitEvent();
    }
}