package com.github.lasoloz.gameproj.control;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;
import com.github.lasoloz.gameproj.control.details.GameMap;
import com.github.lasoloz.gameproj.control.details.GameState;
import com.github.lasoloz.gameproj.control.details.Observer;
import com.github.lasoloz.gameproj.entitites.Blueprint;
import com.github.lasoloz.gameproj.entitites.Instance;
import com.github.lasoloz.gameproj.entitites.Property;
import com.github.lasoloz.gameproj.graphics.Drawable;
import com.github.lasoloz.gameproj.graphics.TerrainCollection;
import com.github.lasoloz.gameproj.graphics.TerrainSet;
import com.github.lasoloz.gameproj.math.Vec2f;

public class FieldRenderer implements Observer, Disposable {
    private SpriteBatch spriteBatch;
    private ShapeRenderer shapeRenderer;

    public FieldRenderer() {
        spriteBatch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
    }

    @Override
    public void update(GameState gameState) {
        spriteBatch.setProjectionMatrix(gameState.getCamera().combined);
        spriteBatch.begin();
        renderMap(gameState);
        spriteBatch.end();

        if (gameState.getInput().getGridState()) {
            drawGrid(gameState);
        }
        drawSelected(gameState);
    }


    private void renderMap(GameState gameState) {
        GameMap map = gameState.getMap();
        int height = map.getHeight();
        int width = map.getWidth();

        float time = gameState.getStateTime();

        TerrainCollection terrainCollection = map.getTerrainCollection();

        Vec2f topLeft = new Vec2f(0, (height - 1) * GameState.gridSize.getY());
        Vec2f iter = topLeft.copy();

        for (int i = 0; i < height; ++i) {
            for (int j = 0; j < width; ++j) {
                TerrainSet current = terrainCollection.getSet(i + 3*j);
                int data = map.getData(j, i);

                // Draw terrain:
                switch (data) {
                    case TerrainSet.GROUND_TYPE:
                        current.drawGround(spriteBatch, iter, time);
                        break;
                    case TerrainSet.WALL_TYPE:
                        current.drawWall(spriteBatch, iter, time);
                        break;
                    case TerrainSet.VOID_TYPE:
                        boolean voidN;
                        if (i < 1) {
                            voidN = false;
                        } else {
                            int topData = map.getData(j, i - 1);
                            voidN = topData != TerrainSet.VOID_TYPE;
                        }

                        if (voidN) {
                            current.drawVoidN(spriteBatch, iter, time);
                        } else {
                            current.drawVoid(spriteBatch, iter, time);
                        }
                        break;
                    default:
                        // Do nothing... Reformat this, please (myself)!
                }


                // Draw instances:
                Instance unit = map.getInstance(j, i);
                if (unit != null) {
                    Blueprint bp = unit.getBlueprint();
                    Drawable sp = bp.getDrawableProperty(Property.PR_IDLE);
                    sp.draw(spriteBatch, iter, time);
                }


                iter.addToX(GameState.gridSize.getX());
            }

            iter.setX(0f);
            iter.addToY(-GameState.gridSize.getY());
        }
    }

    private void drawGrid(GameState gameState) {
        OrthographicCamera camera = gameState.getCamera();
        Vec2f screenSize = gameState.getScreenSize();

        float gridX = GameState.gridSize.getX();
        float gridY = GameState.gridSize.getY();

        float width;
        if (gameState.getDisplayDiv() > 2) {
            width = 1f;
        } else {
            width = 2f;
        }

        Vector2 bottomLeft = getClosestGridPoint(new Vec2f(
                camera.position.x - screenSize.getX() / 2,
                camera.position.y - screenSize.getY() / 2
        ));
        // Bounds of the grid
        bottomLeft.x = Math.max(0f, bottomLeft.x);
        bottomLeft.y = Math.max(0f, bottomLeft.y);

        Vector2 topRight = bottomLeft.cpy().
                add(screenSize.getX() + gridX, screenSize.getY() + gridY);
        // Top-right bounds:
        topRight.x = Math.min(gameState.getMapWidth(), topRight.x);
        topRight.y = Math.min(gameState.getMapHeight(), topRight.y);

        Vector2 iter0 = bottomLeft.cpy();
        Vector2 iter1 = new Vector2(bottomLeft.x, topRight.y);

        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.BLUE);
        while (iter0.x <= topRight.x) {
            shapeRenderer.rectLine(iter0, iter1, width);
            iter0.x += gridX;
            iter1.x += gridX;
        }

        iter0 = bottomLeft.cpy();
        iter1 = new Vector2(topRight.x, bottomLeft.y);
        while (iter0.y <= topRight.y) {
            shapeRenderer.rectLine(iter0, iter1, width);
            iter0.y += gridY;
            iter1.y += gridY;
        }

        shapeRenderer.end();
    }

    private void drawSelected(GameState gameState) {
        Vec2f selectedPos = gameState.getInput().getRelativeMouseCoord();
        if (
                selectedPos.getX() < 0 ||
                selectedPos.getX() > gameState.getMapWidth() - 1f ||
                selectedPos.getY() < 0 ||
                selectedPos.getY() > gameState.getMapHeight()
        ) {
            return;
        }
        Vector2 bottomLeft = getClosestGridPoint(selectedPos);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setProjectionMatrix(gameState.getCamera().combined);
        Gdx.gl.glEnable(GL20.GL_BLEND);

        shapeRenderer.setColor(.1f, 0.05f, 0.8f, .25f);

        shapeRenderer.rect(
                bottomLeft.x,
                bottomLeft.y,
                GameState.gridSize.getX(),
                GameState.gridSize.getY()
        );
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
