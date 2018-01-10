package com.github.lasoloz.gameproj.control.details;

import com.github.lasoloz.gameproj.entitites.Instance;
import com.github.lasoloz.gameproj.math.Vec2f;

public class GameMapTile {
    private int tileCode;
    private Instance content;
    private Vec2f relativePos;
    private boolean occupied;
    private boolean seen;
    private boolean known;

    public GameMapTile(int tileCode) {
        /// TODO: Create enum type for tile code
        if (tileCode < -1 || tileCode > 1) {
            this.tileCode = 0;
        } else {
            this.tileCode = tileCode;
        }

        content = null;
        relativePos = new Vec2f(0f, 0f);

        occupied = false;
    }

    public int getTileCode() {
        return tileCode;
    }

    public Instance getContent() {
        return content;
    }

    public void addContent(Instance content) {
        occupied = true;
        this.content = content;
    }

    public void removeContent() {
        occupied = false;
        this.content = null;
    }

    public void moveContent(GameMapTile other) {
        other.addContent(this.content);
        removeContent();
    }


    public Vec2f getRelativePos() {
        return relativePos;
    }

    public boolean isOccupied() {
        return occupied;
    }

    public void markOccupied() {
        this.occupied = true;
    }


    public boolean isSeen() {
        return seen;
    }

    public void setSeen(boolean seen) {
        this.seen = seen;
    }

    public boolean isKnown() {
        return known;
    }

    public void setKnown(boolean known) {
        this.known = known;
    }
}
