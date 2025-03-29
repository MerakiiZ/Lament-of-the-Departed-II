package main;

import java.awt.Rectangle;

public class EventRect extends Rectangle {
    public int x, y;
    public int width, height;
    public int eventRectDefaultX, eventRectDefaultY;
    public boolean hasEvent = false;
    public String eventDirection = "any";
    public String eventMessage = "";

    // New teleport properties
    public boolean teleportPending = false;
    public boolean isTeleport = false;
    public int teleportMap = 0;
    public int teleportCol = 0;
    public int teleportRow = 0;




}