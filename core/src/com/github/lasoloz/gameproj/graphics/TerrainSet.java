package com.github.lasoloz.gameproj.graphics;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;
import com.github.lasoloz.gameproj.math.Vec2f;

public class TerrainSet implements Disposable {
    public static final int TERRAIN_COUNT = 4;

    public static final int GROUND_TYPE = 0;
    public static final int VOID_TYPE = -1;
    public static final int WALL_TYPE = 1;


    private Drawable groundType;
    private Drawable voidType;
    private Drawable voidNType;
    private Drawable wallType;


    public TerrainSet(
            Drawable groundType,
            Drawable wallType,
            Drawable voidType,
            Drawable voidNType
            ) {
        this.groundType = groundType;
        this.voidType = voidType;
        this.voidNType = voidNType;
        this.wallType = wallType;
    }

    public TerrainSet(Drawable[] terrains) throws GraphicsException {
        if (terrains.length < TERRAIN_COUNT) {
            throw new GraphicsException(
                    "TerrainSet",
                    "You must supply at least 4 enough `Drawable`'s!"
            );
        }

        this.groundType = terrains[0];
        this.wallType = terrains[1];
        this.voidType = terrains[2];
        this.voidNType = terrains[3];
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
        which.draw(batch, pos.getX(), pos.getY(), deltaTime);
    }

    @Override
    public void dispose() {
        groundType.dispose();
        wallType.dispose();
        voidType.dispose();
        voidNType.dispose();
    }
}
