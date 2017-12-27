package com.github.lasoloz.gameproj.entitites;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.github.lasoloz.gameproj.graphics.Drawable;
import com.github.lasoloz.gameproj.math.Vec2f;

public class TerrainGround extends TerrainElement {
    private Instance content;


    public TerrainGround(Vec2f pos, Drawable terrainDrawable) {
        super(pos, terrainDrawable);
        content = null;
    }


    @Override
    public void draw(SpriteBatch batch, float deltaTime) {
        terrainDrawable.draw(batch, pos.getX(), pos.getY(), deltaTime);
    }

    @Override
    public boolean isWall() {
        return false;
    }

    @Override
    public Instance getContent() {
        return content;
    }
}

