package com.github.lasoloz.gameproj.graphics;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.github.lasoloz.gameproj.math.Vec2f;

/**
 * Class defining a set of terrains (Ground, wall and void)
 */
public class TerrainSet {
    public static final int TERRAIN_COUNT = 4;

    public static final int GROUND_TYPE = 0;
    public static final int VOID_TYPE = -1;
    public static final int WALL_TYPE = 1;


    private Drawable groundType;
    private Drawable voidType;
    private Drawable voidNType;
    private Drawable wallType;


    /**
     * Constructor of the set
     * @param atlas Texture atlas of the terrain set
     * @param prefix Prefix used for terrain extraction
     * @throws GraphicsException Exception thrown in case of failure
     */
    public TerrainSet(
            TextureAtlas atlas, String prefix
    ) throws GraphicsException {
        groundType = new SpriteWrapper(checkRegion(atlas, prefix + "ground"));
        wallType = new SpriteWrapper(checkRegion(atlas, prefix + "wall"));
        voidType = new SpriteWrapper(checkRegion(atlas, prefix + "void"));
        voidNType = new SpriteWrapper(checkRegion(atlas, prefix + "voidn"));
    }

    /**
     * Method used for checking the existence of a region
     * @param atlas Texture atlas used for terrain
     * @param name Name of the region inside the atlas
     * @return Texture region of the terrain type
     * @throws GraphicsException Exception is thrown in case of failure
     */
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


    /**
     * Draw a ground tile
     * @param batch Batch
     * @param pos Position of tile
     * @param deltaTime State time
     */
    public void drawGround(SpriteBatch batch, Vec2f pos, float deltaTime) {
        drawDrawable(groundType, batch, pos, deltaTime);
    }

    /**
     * Draw northern void
     * @param batch Batch
     * @param pos Position of tile
     * @param deltaTime State time
     */
    public void drawVoidN(SpriteBatch batch, Vec2f pos, float deltaTime) {
        Vec2f oPos = pos.copy();
        oPos.addToY(-10f);
        drawDrawable(voidNType, batch, oPos, deltaTime);
    }

    /**
     * Draw normal void
     * @param batch Batch
     * @param pos Position of tile
     * @param deltaTime State time
     */
    public void drawVoid(SpriteBatch batch, Vec2f pos, float deltaTime) {
        Vec2f oPos = pos.copy();
        oPos.addToY(-10f);
        drawDrawable(voidType, batch, oPos, deltaTime);
    }

    /**
     * Draw wall
     * @param batch Batch
     * @param pos Position of tile
     * @param deltaTime Delta time
     */
    public void drawWall(SpriteBatch batch, Vec2f pos, float deltaTime) {
        drawDrawable(wallType, batch, pos, deltaTime);
    }

    /**
     * Draw a terrain drawable
     * @param which Drawable selected for drawing
     * @param batch Batch
     * @param pos Position of terrain tile
     * @param deltaTime Delta time
     */
    private void drawDrawable(
            Drawable which,
            SpriteBatch batch,
            Vec2f pos,
            float deltaTime
    ) {
        which.draw(batch, pos, deltaTime);
    }
}
