package com.github.lasoloz.gameproj.graphics;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.github.lasoloz.gameproj.math.Vec2f;

public class TerrainSet {
    public static final int TERRAIN_COUNT = 4;

    public static final int GROUND_TYPE = 0;
    public static final int VOID_TYPE = -1;
    public static final int WALL_TYPE = 1;


    private Drawable groundType;
    private Drawable voidType;
    private Drawable voidNType;
    private Drawable wallType;


    public TerrainSet(
            TextureAtlas atlas, String prefix
    ) throws GraphicsException {
        groundType = new SpriteWrapper(checkRegion(atlas, prefix + "ground"));
        wallType = new SpriteWrapper(checkRegion(atlas, prefix + "wall"));
        voidType = new SpriteWrapper(checkRegion(atlas, prefix + "void"));
        voidNType = new SpriteWrapper(checkRegion(atlas, prefix + "voidn"));
    }

    private TextureRegion checkRegion(
            TextureAtlas atlas, String name
    ) throws GraphicsException {
        TextureRegion region = atlas.findRegion(name);
        if (region == null) {
            throw new GraphicsException(
                    "TerrainSet",
                    "Region `" + region + "` does not exist!"
            );
        }
        return region;
    }


    public void drawGround(SpriteBatch batch, Vec2f pos, float deltaTime) {
        drawDrawable(groundType, batch, pos, deltaTime);
    }

    public void drawVoidN(SpriteBatch batch, Vec2f pos, float deltaTime) {
        Vec2f oPos = pos.copy();
        oPos.addToY(-10f);
        drawDrawable(voidNType, batch, oPos, deltaTime);
    }

    public void drawVoid(SpriteBatch batch, Vec2f pos, float deltaTime) {
        Vec2f oPos = pos.copy();
        oPos.addToY(-10f);
        drawDrawable(voidType, batch, oPos, deltaTime);
    }

    public void drawWall(SpriteBatch batch, Vec2f pos, float deltaTime) {
        drawDrawable(wallType, batch, pos, deltaTime);
    }

    private void drawDrawable(
            Drawable which,
            SpriteBatch batch,
            Vec2f pos,
            float deltaTime
    ) {
        which.draw(batch, pos, deltaTime);
    }
}
