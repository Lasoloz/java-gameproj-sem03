package com.github.lasoloz.gameproj.entitites;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.github.lasoloz.gameproj.blueprints.MoveDir;
import com.github.lasoloz.gameproj.blueprints.TerrainTileBlueprint;
import com.github.lasoloz.gameproj.math.Vec2f;

public final class TerrainTile extends Instance {
    public final static float HEIGHT_UNIT = 10f;

    private TerrainTileBlueprint blueprint;

    // Height of the terrain object:
    // 0     -> the terrain is on ground, 0 units of wall are drawn
    // n > 0 -> the terrain is n units high, n units of walls are down below
    private int height;


    // Content of the terrain element:
    private Instance content; /// TODO: Maybe it won't be `Instance`


    public TerrainTile(Vec2f pos, TerrainTileBlueprint blueprint, int height) {
        super(pos);
        this.blueprint = blueprint;
        if (height < 0) {
            this.height = 0;
        } else {
            this.height = height;
        }

        content = null;
    }


    public int getHeight() {
        return height;
    }


    @Override
    public void draw(SpriteBatch batch, int height, float deltaTime) {
        float posX = pos.getX();
        float posY = pos.getY() + HEIGHT_UNIT * height;
        // Draw wall elements:
        for (int i = 0; i < this.height; ++i) {
            blueprint.getWall().draw(
                    batch,
                    posX,
                    posY + HEIGHT_UNIT * i,
                    deltaTime
            );
        }

        // Draw ground element:
        blueprint.getGround().draw(
                batch,
                posX,
                posY + this.height * HEIGHT_UNIT,
                deltaTime
        );

        // Draw content:
        if (content != null) {
            content.draw(batch, height + this.height, deltaTime);
        }
    }


    public void addContent(Instance content) {
        this.content = content;
    }


    public Instance getContent() {
        return content;
    }


    public int getBoundHeight(MoveDir dir) {
        return blueprint.getBoundModifier(dir) + height;
    }
}
