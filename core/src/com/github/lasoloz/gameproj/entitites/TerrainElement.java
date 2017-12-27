package com.github.lasoloz.gameproj.entitites;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.github.lasoloz.gameproj.blueprints.InstanceType;
import com.github.lasoloz.gameproj.graphics.Drawable;
import com.github.lasoloz.gameproj.math.Vec2f;

public abstract class TerrainElement extends Instance {
    protected Drawable terrainDrawable;


    public TerrainElement(Vec2f pos, Drawable terrainDrawable) {
        super(pos);
        this.terrainDrawable = terrainDrawable;
    }


    public abstract boolean isWall();

    public abstract Instance getContent();

    @Override
    public InstanceType getInstanceType() {
        return InstanceType.TERRAIN_ELEMENT;
    }


    @Override
    public void draw(SpriteBatch batch, float deltaTime) {
        terrainDrawable.draw(batch, pos.getX(), pos.getY(), deltaTime);
    }
}
