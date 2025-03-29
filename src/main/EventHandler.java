package main;

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

        setupEvent(0, 38, 53, "any", "You found a secret spot!"); // Map 0, position (38,53)
        setupEvent(1, 12, 25, "down", "Press DOWN to interact"); // Map 1, position (12,25)
    }

    public void checkEvent() {
        // Exit current event if needed
        if (eventActive && currentEventLocation != null &&
                !isPlayerInEventArea(currentEventMap, currentEventLocation.x, currentEventLocation.y)) {
            exitEvent();
            return;
        }

        // Only check for new events if none are active
        if (!eventActive) {
            checkForEventsNearPlayer();
        }
    }

    private void checkForEventsNearPlayer() {
        // Check 3x3 area around player for better responsiveness
        int playerCol = gp.player.worldX / gp.tileSize;
        int playerRow = gp.player.worldY / gp.tileSize;

        for (int col = playerCol - 1; col <= playerCol + 1; col++) {
            for (int row = playerRow - 1; row <= playerRow + 1; row++) {
                if (isValidPosition(gp.currentMap, col, row) &&
                        eventRects[gp.currentMap][col][row].hasEvent) {

                    if (hit(gp.currentMap, col, row,
                            eventRects[gp.currentMap][col][row].eventDirection)) {
                        testEvent(gp.dialougeState,
                                eventRects[gp.currentMap][col][row].eventMessage);
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

    public void testEvent(int gameState, String eventMessage) {
        if (gp.ui == null) return;

        gp.gameState = gameState;
        gp.ui.currentDialouge = "Event triggered! Move away to exit.";
        eventActive = true;
    }

    private void setupEvent(int map, int col, int row, String direction, String message) {
        if (isValidPosition(map, col, row)) {
            // Mark this position as having an event
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
}