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

public class InfoRenderer implements Observer, Disposable {
    private SpriteBatch textBatch;
    private ShapeRenderer shapeRenderer;
    private BitmapFont font;
    private static final int FONT_SIZE = 36;
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
        int[] p = gameState.getRelativeMouseGridPos();
        int x = p[0];
        int y = p[1];

        String message;

        if (x < 0 ||
                x >= gameState.getMap().getWidth() ||
                y < 0 ||
                y >= gameState.getMap().getHeight()
                ) {
            message = " - ";
        } else {
            // Get unit information
            message = "(" + x + ", " + y + ")";
            GameMap map = gameState.getMap();
            int data = map.getData(x, y);
            // Print terrain information:
            message += getTerrainInformation(data);
            // Get instance information:
            Instance instance = map.getInstance(x, y);
            message += getInstanceInformation(instance);
        }

        int length = (int) gameState.getScreenSize().getX() - BORDERS * 2;

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        Gdx.gl.glEnable(GL20.GL_BLEND);
        shapeRenderer.setColor(0f, 0f, 0f, .7f);
        shapeRenderer.rect(BORDERS, BORDERS, length, FONT_SIZE + 2);
        shapeRenderer.end();
        textBatch.begin();
        font.draw(textBatch, message, BORDERS + 2, FONT_SIZE);
        textBatch.end();
    }


    private String getTerrainInformation(int data) {
        switch (data) {
            case TerrainSet.GROUND_TYPE:
                return " Ground;";
            case TerrainSet.WALL_TYPE:
                return " Wall;";
            case TerrainSet.VOID_TYPE:
                return " Void;";
            default:
                return " Unknown;";
        }
    }

    private String getInstanceInformation(Instance instance) {
        if (instance == null) {
            return "";
        }

        return " " + instance.getBlueprint().getInfo();
    }

    @Override
    public void dispose() {
        font.dispose();
        textBatch.dispose();
    }
}
