package com.github.lasoloz.gameproj.entitites;

import com.github.lasoloz.gameproj.blueprints.InstanceType;
import com.github.lasoloz.gameproj.graphics.Drawable;
import com.github.lasoloz.gameproj.math.Vec2f;

public class TerrainWall extends TerrainElement {
    public TerrainWall(Vec2f pos, Drawable terrainDrawable) {
        super(pos, terrainDrawable);
    }

    @Override
    public boolean isWall() {
        return true;
    }

    @Override
    public Instance getContent() {
        return this;
    }

    @Override
    public InstanceType getInstanceType() {
        return InstanceType.TERRAIN_WALL;
    }
}
