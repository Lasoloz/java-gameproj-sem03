package com.github.lasoloz.gameproj.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.github.lasoloz.gameproj.graphics.Drawable;
import com.github.lasoloz.gameproj.math.Vec2f;

public class Entity {
    protected float lifetime;
    protected float animationTime;
    protected Drawable baseSprite;

    protected Vec2f pos;
    protected Vec2f size;


    public Entity(Drawable baseSprite) {
        this.baseSprite = baseSprite;
        lifetime = 0f;
        animationTime = 0f;
    }


//    protected void changeAnimation(int key) {
//        animationTime = 0f;
//    }

    public void update(float deltaTime) {
        lifetime += deltaTime;
        animationTime += deltaTime;
    }

    public void draw(SpriteBatch batch) {
        baseSprite.draw(batch, pos.getX(), pos.getY(), animationTime);
    }


    public void moveRel(Vec2f diff) {
        pos.addTo(diff);
    }


    public void moveAbs(Vec2f pos) {
        this.pos.set(pos);
    }

    public void setSize(Vec2f size) {
        this.size.set(size);
    }

    public void scale(Vec2f scale) {
        size.setX(size.getX() * scale.getX());
        size.setY(size.getY() * scale.getY());
    }
}
