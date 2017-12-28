package com.github.lasoloz.gameproj.control;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
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
        float time = gameState.getStateTime();

        spriteBatch.setProjectionMatrix(gameState.getCamera().combined);
        spriteBatch.begin();
        mainTs.drawGround(spriteBatch, new Vec2f(0f, 0f), time);
        mainTs.drawVoid(spriteBatch, new Vec2f(28f, 0f), time);
        mainTs.drawVoidN(spriteBatch, new Vec2f(28f, 16f), time);
        altTs.drawWall(spriteBatch, new Vec2f(0f, 16f), time);
        spriteBatch.end();

        drawGrid(gameState);
    }

    private void drawGrid(GameState gameState) {
        OrthographicCamera camera = gameState.getCamera();
        Vec2f screenSize = gameState.getScreenSize();

        float gridX = GameState.gridSize.getX();
        float gridY = GameState.gridSize.getY();

        Vector2 bottomLeft = getClosestGridPoint(new Vec2f(
                camera.position.x - screenSize.getX() / 2,
                camera.position.y - screenSize.getY() / 2
        ));

        Vector2 topRight = bottomLeft.cpy().
                add(screenSize.getX() + gridX, screenSize.getY() + gridY);

        Vector2 iter0 = bottomLeft.cpy();
        Vector2 iter1 = new Vector2(bottomLeft.x, topRight.y);

        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.BLUE);
        while (iter0.x < topRight.x) {
//            shapeRenderer.line(iter0, iter1);
            shapeRenderer.rectLine(iter0, iter1, 2f);
            iter0.x += gridX;
            iter1.x += gridX;
        }

        iter0 = bottomLeft.cpy();
        iter1 = new Vector2(topRight.x, bottomLeft.y);
        while (iter0.y < topRight.y) {
//            shapeRenderer.line(iter0, iter1);
            shapeRenderer.rectLine(iter0, iter1, 2f);
            iter0.y += gridY;
            iter1.y += gridY;
        }


        // Render selected rectangle:
        Vec2f selectedPos = gameState.getInput().getRelativeMouseCoord();
        bottomLeft = getClosestGridPoint(selectedPos);
        Gdx.gl.glEnable(GL20.GL_BLEND);
        shapeRenderer.setColor(.1f, 0.05f, 0.8f, .25f);
        shapeRenderer.rect(bottomLeft.x, bottomLeft.y, gridX, gridY);


        shapeRenderer.end();
    }

    private Vector2 getClosestGridPoint(Vec2f point) {
        final float gridX = GameState.gridSize.getX();
        final float gridY = GameState.gridSize.getY();
        return new Vector2(
                (float) Math.floor(point.getX() / gridX) * gridX,
                (float) Math.floor(point.getY() / gridY) * gridY
        );
    }

    @Override
    public void dispose() {
        spriteBatch.dispose();
        shapeRenderer.dispose();
    }
}
