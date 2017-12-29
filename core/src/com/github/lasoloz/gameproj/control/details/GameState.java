package com.github.lasoloz.gameproj.control.details;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.TimeUtils;
import com.github.lasoloz.gameproj.control.GameInput;
import com.github.lasoloz.gameproj.graphics.GraphicsException;
import com.github.lasoloz.gameproj.graphics.TerrainAssets;
import com.github.lasoloz.gameproj.math.Vec2f;

public class GameState implements Disposable {
    public final static Vec2f gridSize = new Vec2f(28f, 16f);

    private GameMap map;

    private OrthographicCamera camera;
    private GameInput input;
    private Vec2f playerPos;
    private Vec2f screenSize;
    private int displayDiv;

    private long time;
    private long displayDivChangeTime;
    private TerrainAssets terrainAssets;



    public GameState(
            Vec2f screenSize,
            int displayDiv,
            String currentTerrainAssetPath) throws GraphicsException {
        // Throws exception:
        terrainAssets = new TerrainAssets(currentTerrainAssetPath);

        this.screenSize = screenSize.copy();
        this.playerPos = new Vec2f(0f, 0f);
        this.displayDiv = displayDiv;
        createCamera();
        input = new GameInput();
        time = 0;
        displayDivChangeTime = 0;

        map = new GameMap();
    }

    public OrthographicCamera getCamera() {
        return camera;
    }

    public GameInput getInput() {
        return input;
    }

    public Vec2f getPlayerPos() {
        return playerPos;
    }

    public Vec2f getScreenSize() {
        return screenSize;
    }

    public int getDisplayDiv() {
        return displayDiv;
    }

    public TerrainAssets getTerrainAssets() {
        return terrainAssets;
    }

    public long getTime() {
        return time;
    }

    public float getStateTime() {
        return time / 1000f;
    }

    public void updateTime() {
        time = TimeUtils.millis();
    }

    @Override
    public void dispose() {
        terrainAssets.dispose();
    }

    public void incrementDisplayDiv() {
        if (displayDiv < 8) {
            displayDiv *= 2;
            createCamera();
            displayDivChangeTime = time;
        }
    }

    public void decrementDisplayDiv() {
        if (displayDiv > 1) {
            displayDiv /= 2;
            createCamera();
            displayDivChangeTime = time;
        }
    }


    public boolean loadMap(String mapFileName) {
        return map.loadMap(mapFileName);
    }

    public GameMap getMap() {
        return map;
    }

    public float getMapWidth() {
        return gridSize.getX() * map.getWidth();
    }

    public float getMapHeight() {
        return gridSize.getY() * map.getHeight();
    }



    private void createCamera() {
        camera = new OrthographicCamera(
                screenSize.getX() / displayDiv,
                screenSize.getY() / displayDiv
        );
    }
}
