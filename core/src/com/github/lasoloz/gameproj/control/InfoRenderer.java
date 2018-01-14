package com.github.lasoloz.gameproj.control;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Disposable;
import com.github.lasoloz.gameproj.control.details.GameMap;
import com.github.lasoloz.gameproj.control.details.GameState;
import com.github.lasoloz.gameproj.control.details.Observer;
import com.github.lasoloz.gameproj.entitites.Instance;
import com.github.lasoloz.gameproj.graphics.TerrainSet;
import com.github.lasoloz.gameproj.math.Vec2i;

/**
 * Class extending `Observer` for rendering information about the units and/or
 * player
 * private members:
 * textBatch - Batch used for text rendering
 * shapeRenderer - Renderer used for rendering rectangles
 * font - Font used by the renderer
 * FONT_SIZE = font size for information
 * BORDERS = Borders around the rectangle
 * HEALTH_BAR_THICKNESS = Thickness of the health bar
 * @see Observer
 */
public class InfoRenderer implements Observer, Disposable {
    private SpriteBatch textBatch;
    private ShapeRenderer shapeRenderer;
    private BitmapFont font;
    private static final int FONT_SIZE = 22;
    private static final int BORDERS = 4;
    private static final int HEALTH_BAR_THICKNESS = 10;

    /**
     * Constructor
     */
    public InfoRenderer() {
        // Sprite renderer:
        textBatch = new SpriteBatch();

        // Shape renderer:
        shapeRenderer = new ShapeRenderer();

        // Font
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(
                Gdx.files.internal("fonts/Courier Prime/Courier Prime.ttf")
        );
        FreeTypeFontGenerator.FreeTypeFontParameter parameter =
                new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = FONT_SIZE;
        font = generator.generateFont(parameter);
        generator.dispose();
    }

    @Override
    public void update(GameState gameState) {
        printTileInformation(gameState);
        printPlayerInformation(gameState);
    }

    /**
     * Print information about player (health bar, others)
     * @param gameState Game state object
     * @see GameState
     */
    private void printPlayerInformation(GameState gameState) {
        // Get player position:
        Vec2i pos = gameState.getPlayerPos();

        // Get player instance:
        Instance playerInstance = gameState.getMap().getInstance(pos.x, pos.y);

        // Draw health bar:
        drawHealthBar(
                gameState,
                playerInstance.getHealth(),
                playerInstance.getBlueprint().getMaxHealth()
        );
        // Print player health and loot value:
//        drawPlayerHealthAndLootValue
    }

    /**
     * Draw health bar
     * @param gameState Game state for retrieving information about player
     * @param health Current player health
     * @param maxHealth Player's max health
     */
    private void drawHealthBar(GameState gameState, int health, int maxHealth) {
        // Get length of opaque rectangle:
        int length = gameState.getScreenSize().x - BORDERS * 2;

        // Draw base rect:
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setProjectionMatrix(gameState.getUiCamera().combined);
        shapeRenderer.setColor(0f, 0f, 0f, .7f);
        Gdx.gl.glEnable(GL20.GL_BLEND);

        int top = gameState.getScreenSize().y - BORDERS - HEALTH_BAR_THICKNESS;

        shapeRenderer.rect(
                BORDERS,
                top,
                length,
                HEALTH_BAR_THICKNESS
        );


        int healthLen = (length - 2) * health / maxHealth;

        shapeRenderer.setColor(.2f, 5f, .1f, 1f);
        shapeRenderer.rect(
                BORDERS + 1,
                top + 1,
                healthLen,
                HEALTH_BAR_THICKNESS - 2
        );

        shapeRenderer.end();
    }

    /**
     * Print information about highlighted tile and about its content
     * @param gameState Game state object
     * @see GameState
     */
    private void printTileInformation(GameState gameState) {
        // Draw information on update:
        // Get tile position:
        Vec2i pos = gameState.getRelativeMouseGridPos();

        // Create new message:
        String message;

        if (gameState.getMap().inMap(pos)) {
            // Get unit information
            message = "(" + pos.x + ", " + pos.y + ")";
            GameMap map = gameState.getMap();
            int data = map.getData(pos.x, pos.y);
            // Print terrain information:
            message += getTerrainInformation(data);
            // Get instance information:
            Instance instance = map.getInstance(pos.x, pos.y);
            message += getInstanceInformation(instance);
        } else {
            message = " - ";
        }

        // Get length of opaque rectangle:
        int length = gameState.getScreenSize().x - BORDERS * 2;

        // Drawing part;
        // Draw base opaque rectangle:
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setProjectionMatrix(gameState.getUiCamera().combined);
        Gdx.gl.glEnable(GL20.GL_BLEND);
        shapeRenderer.setColor(0f, 0f, 0f, .7f);
        shapeRenderer.rect(BORDERS, BORDERS, length, FONT_SIZE + 2);
        shapeRenderer.end();
        // Draw text information:
        textBatch.begin();
        textBatch.setProjectionMatrix(gameState.getUiCamera().combined);
        font.draw(textBatch, message, BORDERS + 2, FONT_SIZE);
        textBatch.end();
    }


    /**
     * Get terrain information
     * @param data Terrain code of the tile
     * @return String containing tile information
     */
    private String getTerrainInformation(int data) {
        // Get terrain information:
        switch (data) {
            case TerrainSet.GROUND_TYPE:
                return " Ground;";
            case TerrainSet.WALL_TYPE:
                return " Wall;";
            case TerrainSet.VOID_TYPE:
                return " Void;";
        }

        return " Unknown;";
    }

    /**
     * Get information about instance
     * @param instance Selected instance
     * @return String information about instance
     */
    private String getInstanceInformation(Instance instance) {
        // Get information about instance:
        // Check, if tile contains instance:
        if (instance == null) {
            return "";
        }

        // Otherwise return information:
        return " " + instance.getInfo();
//        return " " + instance.getBlueprint().getInfo();
    }

    @Override
    public void dispose() {
        font.dispose();
        textBatch.dispose();
    }
}
