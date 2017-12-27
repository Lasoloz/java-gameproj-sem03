package com.github.lasoloz.gameproj.control.details;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.Disposable;
import com.github.lasoloz.gameproj.control.GameInput;
import com.github.lasoloz.gameproj.graphics.GraphicsException;
import com.github.lasoloz.gameproj.graphics.TerrainAssets;
import com.github.lasoloz.gameproj.math.Vec2f;

public class GameState implements Disposable {
    private OrthographicCamera camera;
    private GameInput input;
    private Vec2f playerPos;
    private Vec2f screenSize;
    private int displayDiv;

    private float timeSinceStart;
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
        camera = new OrthographicCamera(
                screenSize.getX() / displayDiv,
                screenSize.getY() / displayDiv
        );
        input = new GameInput();
        timeSinceStart = 0f;
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

    public float getTimeSinceStart() {
        return timeSinceStart;
    }

    @Override
    public void dispose() {
        terrainAssets.dispose();
    }
}
