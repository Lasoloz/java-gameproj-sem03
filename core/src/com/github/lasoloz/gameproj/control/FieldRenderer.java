package com.github.lasoloz.gameproj.control;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Disposable;
import com.github.lasoloz.gameproj.control.details.GameState;
import com.github.lasoloz.gameproj.control.details.Observer;
import com.github.lasoloz.gameproj.graphics.TerrainSet;
import com.github.lasoloz.gameproj.math.Vec2f;

public class FieldRenderer implements Observer, Disposable {
    SpriteBatch spriteBatch;
    ShapeRenderer shapeRenderer;

    public FieldRenderer() {
        spriteBatch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
    }

    @Override
    public void update(GameState gameState) {
        TerrainSet mainTs = gameState.getTerrainAssets().getTerrainSet();
        TerrainSet altTs = gameState.getTerrainAssets().getTerrainSetAlt();
        float time = gameState.getTimeSinceStart();


        spriteBatch.setProjectionMatrix(gameState.getCamera().combined);
        spriteBatch.begin();
        mainTs.drawGround(spriteBatch, new Vec2f(0f, 0f), time);
        mainTs.drawVoid(spriteBatch, new Vec2f(28f, 0f), time);
        mainTs.drawVoidN(spriteBatch, new Vec2f(28f, 16f), time);
        altTs.drawWall(spriteBatch, new Vec2f(0f, 16f), time);
        spriteBatch.end();
    }

    @Override
    public void dispose() {
        spriteBatch.dispose();
        shapeRenderer.dispose();
    }
}
