package net.runelite.api;

import java.awt.*;

public interface WallDecoration {
    int getLevel();
    int getZ();
    int getX();
    int getY();
    int getID();
    Point[] getLocalTilePoints();
}
