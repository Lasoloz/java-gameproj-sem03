package com.github.lasoloz.gameproj.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.github.lasoloz.gameproj.graphics.DirectionSprites;
import com.github.lasoloz.gameproj.graphics.Drawable;
import com.github.lasoloz.gameproj.math.Vec2f;

public class Movable extends Entity {
    /// TODO: rethink this method!!
    protected DirectionSprites staySprites;
    protected DirectionSprites moveSprites;
    protected int spriteKey;

    protected Vec2f velocity;
    protected float maxVel;

    public Movable(Drawable baseSprite) {
        super(baseSprite);
        spriteKey = 0;
    }

    public void addStaySprites(DirectionSprites staySprites) {
        this.staySprites = staySprites;
    }

    public void addMoveSprites(DirectionSprites moveSprites) {
        this.moveSprites = moveSprites;
    }

//    @Override
//    protected void changeAnimation(int key) {
////        if (sta)
//    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        pos.addTo(velocity);
    }

    @Override
    public void draw(SpriteBatch batch) {
        /// TODO: rethink stuff stated at top, and then implement this.
    }
}
