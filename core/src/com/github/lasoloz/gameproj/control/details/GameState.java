package com.github.lasoloz.gameproj.control.details;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.github.lasoloz.gameproj.control.GameInput;
import com.github.lasoloz.gameproj.math.Vec2f;

public class GameState {
    private OrthographicCamera camera;
    private GameInput input;
    private Vec2f playerPos;
    private Vec2f screenSize;
    private int displayDiv;

    private SpriteBatch gameBatch;
    private SpriteBatch inputBatch;
    private SpriteBatch debugBatch;


    public GameState(Vec2f screenSize, int displayDiv) {
        this.screenSize = screenSize.copy();
        this.playerPos = new Vec2f(0f, 0f);
        this.displayDiv = displayDiv;
        camera = new OrthographicCamera(
                screenSize.getX() / displayDiv,
                screenSize.getY() / displayDiv
        );
        input = new GameInput();
        gameBatch = new SpriteBatch();
        inputBatch = new SpriteBatch();
        debugBatch = new SpriteBatch();
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

    public SpriteBatch getGameBatch() {
        return gameBatch;
    }

    public SpriteBatch getInputBatch() {
        return inputBatch;
    }

    public SpriteBatch getDebugBatch() {
        return debugBatch;
    }
}
