package com.github.lasoloz.gameproj.graphics;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.github.lasoloz.gameproj.math.Vec2f;

public abstract class Drawable {
    private int originX;
    private int originY;

    public Drawable() {
        this(0, 0);
    }

    public Drawable(int originX, int originY) {
        this.originX = originX;
        this.originY = originY;
    }

    final public void draw(
            SpriteBatch batch,
            Vec2f pos,
            float deltaTime
    ) {
        drawToCoords(
                batch,
                (int)pos.getX() + originX,
                (int)pos.getY() + originY,
                deltaTime
        );
    }

    abstract protected void drawToCoords(
            SpriteBatch batch,
            int posX,
            int posY,
            float deltaTime
    );



//    abstract public void draw(
//            SpriteBatch batch,
//            float posX,
//            float posY,
//            float width,
//            float height,
//            float deltaTime
//    );
}
