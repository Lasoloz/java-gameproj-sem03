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

public class InfoRenderer implements Observer, Disposable {
    private SpriteBatch textBatch;
    private ShapeRenderer shapeRenderer;
    private BitmapFont font;
    private static final int FONT_SIZE = 22;
    private static final int BORDERS = 4;

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
        // Draw information on update:
        // Get
        Vec2i pos = gameState.getRelativeMouseGridPos();

        String message;

        if (pos.x < 0 ||
                pos.x >= gameState.getMap().getWidth() ||
                pos.y < 0 ||
                pos.y >= gameState.getMap().getHeight()
                ) {
            message = " - ";
        } else {
            // Get unit information
            message = "(" + pos.x + ", " + pos.y + ")";
            GameMap map = gameState.getMap();
            int data = map.getData(pos.x, pos.y);
            // Print terrain information:
            message += getTerrainInformation(data);
            // Get instance information:
            Instance instance = map.getInstance(pos.x, pos.y);
            message += getInstanceInformation(instance);
        }

//        // Get information about game state:
//        if (gameState.readyForStep()) {
//            message += " - Please, perform action!";
//        } else {
//            message += " - Performing command...";
//        }

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
