package com.github.lasoloz.gameproj.graphics;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.github.lasoloz.gameproj.math.Vec2f;

/**
 * Abstract class describing the main functions of a drawable object
 */
public abstract class Drawable {
    private int originX;
    private int originY;

    /**
     * Constructor
     */
    public Drawable() {
        this(0, 0);
    }

    /**
     * Constructor
     * @param originX Origin on the X axis
     * @param originY Origin on the Y axis
     */
    public Drawable(int originX, int originY) {
        this.originX = originX;
        this.originY = originY;
    }

    /**
     * Draw drawable
     * @param batch Batch used for drawing
     * @param pos Position of the sprite
     * @param deltaTime State time for animation
     */
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

    /**
     * Method resolving draw (can be overridden in subclasses
     * @param batch Batch for drawing
     * @param posX x position for drawing
     * @param posY y position for drawing
     * @param deltaTime State time for animation
     */
    abstract protected void drawToCoords(
            SpriteBatch batch,
            int posX,
            int posY,
            float deltaTime
    );
}
