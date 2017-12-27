package com.github.lasoloz.gameproj.entitites;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.github.lasoloz.gameproj.blueprints.InstanceType;
import com.github.lasoloz.gameproj.math.Vec2f;

public abstract class Instance {
    public final static Vec2f gridSize = new Vec2f(24f, 16f);

    protected Vec2f pos;

    public Instance(Vec2f pos) {
        this.pos = pos.copy();
    }


    public Vec2f getPos() {
        return pos.copy();
    }


    public abstract void draw(SpriteBatch batch, float deltaTime);


    public InstanceType getInstanceType() {
        return InstanceType.INSTANCE;
    }
}
