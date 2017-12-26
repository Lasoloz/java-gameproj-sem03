package com.github.lasoloz.gameproj.entitites;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.github.lasoloz.gameproj.math.Vec2f;

public abstract class Instance {
    protected Vec2f pos;

    public Instance(Vec2f pos) {
        this.pos = pos.copy();
    }


    public Vec2f getPos() {
        return pos.copy();
    }


    public abstract void draw(SpriteBatch batch, int height, float deltaTime);
}
