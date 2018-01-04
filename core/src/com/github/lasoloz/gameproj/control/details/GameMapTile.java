package com.github.lasoloz.gameproj.control.details;

import com.github.lasoloz.gameproj.entitites.Instance;
import com.github.lasoloz.gameproj.math.Vec2f;

public class GameMapTile {
    private int tileCode;
    private Instance content;
    private Vec2f relativePos;

    public GameMapTile(int tileCode) {
        /// TODO: Create enum type for tile code
        if (tileCode < -1 || tileCode > 1) {
            this.tileCode = 0;
        } else {
            this.tileCode = tileCode;
        }

        content = null;
        relativePos = new Vec2f(0f, 0f);
    }

    public int getTileCode() {
        return tileCode;
    }

    public Instance getContent() {
        return content;
    }

    public void addContent(Instance content) {
        this.content = content;
    }

    public void removeContet() {
        this.content = null;
    }

    public void moveContent(GameMapTile other) {
        other.addContent(this.content);
        removeContet();
    }


    public Vec2f getRelativePos() {
        return relativePos;
    }
}
