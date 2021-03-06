package com.github.lasoloz.gameproj.control;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;
import com.github.lasoloz.gameproj.blueprints.Action;
import com.github.lasoloz.gameproj.blueprints.Blueprint;
import com.github.lasoloz.gameproj.control.details.GameMap;
import com.github.lasoloz.gameproj.control.details.GameMapTile;
import com.github.lasoloz.gameproj.control.details.GameState;
import com.github.lasoloz.gameproj.control.details.Observer;
import com.github.lasoloz.gameproj.entitites.Instance;
import com.github.lasoloz.gameproj.graphics.Drawable;
import com.github.lasoloz.gameproj.graphics.TerrainCollection;
import com.github.lasoloz.gameproj.graphics.TerrainSet;
import com.github.lasoloz.gameproj.math.Vec2f;
import com.github.lasoloz.gameproj.math.Vec2i;

/**
 * Class responsible for rendering the contents of the game map
 * Private members:
 * spriteBatch - terrain and unit rendering batch
 * shapeRenderer - shape rendering batch
 * notSeenMask - mask for known tiles not being seen
 */
public class FieldRenderer implements Observer, Disposable {
    private SpriteBatch spriteBatch; // For drawing terrain
    private ShapeRenderer shapeRenderer; // For drawing grid:

    private Texture notSeenMask;

    /**
     * Construct a new field renderer
     */
    public FieldRenderer() {
        spriteBatch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        // TODO: Reformat the loading of the mask!
        notSeenMask = new Texture("other/not_seen_mask.png");
    }

    @Override
    public void update(GameState gameState) {
        // Draw game field on update:

        // Draw map terrain data and units:
        renderMap(gameState);


        // Draw grid on request:
        if (gameState.getInput().getGridState()) {
            drawGrid(gameState);
        }
        // Draw highlighting over selected grid element:
        drawSelected(gameState);
    }


    /**
     * Renders the game map, showing the the contents of every tile (instance
     * over terrain tile)
     * @param gameState Game state provided by controller
     */
    private void renderMap(GameState gameState) {
        // Get map data:
        GameMap map = gameState.getMap();
        // Map dimensions:
        int height = map.getHeight();
        int width = map.getWidth();

        // Get state time for animating units:
        float time = gameState.getStateTime();

        // Get terrain collection for drawing map:
        TerrainCollection terrainCollection = map.getTerrainCollection();

        // Get top left point of the map and a positional iterator for
        // rendering:
        Vec2f topLeft = new Vec2f(0, (height - 1) * GameState.gridSize.getY());
        Vec2f iter = topLeft.copy();


        // Set up drawing batches and renderers:
        OrthographicCamera camera = gameState.getCamera();
        spriteBatch.setProjectionMatrix(camera.combined);
        spriteBatch.begin();
        Gdx.gl.glEnable(GL20.GL_BLEND);


        // Iterate through map data:
        for (int i = 0; i < height; ++i) {
            for (int j = 0; j < width; ++j) {
                // Get one of the terrain sets:
                TerrainSet current = terrainCollection.getSet(i + 3*j);
                GameMapTile tile = map.getGameMapTile(j, i);

                /// TODO: Reformat this mess down there VVVV!
                if (tile.isKnown()) {
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
                            // Northern parts of voids (different texture):
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


                    // Is tile seen, or not?
                    if (tile.isSeen()) {
                        // Draw instances:
                        Instance instance = map.getInstance(j, i);
                        if (instance != null) {
                            Blueprint bp = instance.getBlueprint();
                            Drawable sp = bp.getActionImage(Action.ACT_IDLE);
                            sp.draw(spriteBatch, iter, time);
                        }
                    } else {
                        // Draw not seen mask:
                        spriteBatch.draw(
                                notSeenMask,
                                iter.x,
                                iter.y,
                                GameState.gridSize.x,
                                GameState.gridSize.y
                        );
                    }
                }


                // Iterate on position horizontally:
                iter.addToX(GameState.gridSize.getX());
            }

            // Iterate on position vertically:
            iter.setX(0f);
            iter.addToY(-GameState.gridSize.getY());
        }

        // End of drawing:
        spriteBatch.end();
    }


    /**
     * Draw map grid
     * @param gameState Game state provided by controller.
     */
    private void drawGrid(GameState gameState) {
        // Get camera and screen dimensions:
        OrthographicCamera camera = gameState.getCamera();
        Vec2i screenSize = gameState.getScreenSize();

        // Grid dimensions:
        float gridX = GameState.gridSize.getX();
        float gridY = GameState.gridSize.getY();

        float width;
        // Select grid line's width:
        if (gameState.getDisplayDiv() > 2) {
            width = 1f;
        } else {
            width = 2f;
        }

        // Round up to a grid point:
        Vector2 bottomLeft = getClosestGridPoint(new Vec2f(
                camera.position.x - screenSize.x / 2,
                camera.position.y - screenSize.y / 2
        ));
        // Bounds of the grid
        bottomLeft.x = Math.max(0f, bottomLeft.x);
        bottomLeft.y = Math.max(0f, bottomLeft.y);

        Vector2 topRight = bottomLeft.cpy().
                add(screenSize.x + gridX, screenSize.y + gridY);
        // Top-right bounds:
        topRight.x = Math.min(gameState.getMapWidth(), topRight.x);
        topRight.y = Math.min(gameState.getMapHeight(), topRight.y);

        // Create grid iterators:
        Vector2 iter0 = bottomLeft.cpy();
        Vector2 iter1 = new Vector2(bottomLeft.x, topRight.y);

        // Initialize shape renderer:
        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.BLUE);
        // Start drawing vertical lines:
        while (iter0.x <= topRight.x) {
            shapeRenderer.rectLine(iter0, iter1, width);
            iter0.x += gridX;
            iter1.x += gridX;
        }

        // Reinitialize iterators:
        iter0 = bottomLeft.cpy();
        iter1 = new Vector2(topRight.x, bottomLeft.y);
        // Start drawing horizontal lines:
        while (iter0.y <= topRight.y) {
            shapeRenderer.rectLine(iter0, iter1, width);
            iter0.y += gridY;
            iter1.y += gridY;
        }

        // End shape:
        shapeRenderer.end();
    }

    /**
     * Draws a filled rectangle over the selected tile in the map.
     * @param gameState GameState provided by controller
     */
    private void drawSelected(GameState gameState) {
        // Get selected position (mouse hover) from game input:
        Vec2f selectedPos = gameState.getInput().getRelativeMouseCoord();
        // Check if out of map bounds:
        if (
                selectedPos.getX() < 0 ||
                selectedPos.getX() > gameState.getMapWidth() - 1f ||
                selectedPos.getY() < 0 ||
                selectedPos.getY() > gameState.getMapHeight()
        ) {
            return;
        }
        // Get bottom left point of the highlight rectangle:
        Vector2 bottomLeft = getClosestGridPoint(selectedPos);
        // Start drawing shape:
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setProjectionMatrix(gameState.getCamera().combined);
        Gdx.gl.glEnable(GL20.GL_BLEND);

        shapeRenderer.setColor(.1f, 0.05f, 0.8f, .25f);

        // Render rectangle:
        shapeRenderer.rect(
                bottomLeft.x,
                bottomLeft.y,
                GameState.gridSize.getX(),
                GameState.gridSize.getY()
        );
        shapeRenderer.end();
    }

    /**
     * Returns the bottom-left point of a tile in real world vector dimensions,
     * if the specified point is in the tile
     * @param point Point to be checked
     * @return Vector specifying bottom-left point of the tile
     */
    private Vector2 getClosestGridPoint(Vec2f point) {
        // Floor to grid point:
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
