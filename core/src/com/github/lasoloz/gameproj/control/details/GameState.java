package com.github.lasoloz.gameproj.control.details;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.TimeUtils;
import com.github.lasoloz.gameproj.control.GameInput;
import com.github.lasoloz.gameproj.graphics.CursorSet;
import com.github.lasoloz.gameproj.math.Vec2f;
import com.github.lasoloz.gameproj.math.Vec2i;

public class GameState implements Disposable {
    public final static Vec2f gridSize = new Vec2f(28f, 16f);
    public final static long STEP_TIME_DELTA = 500;
    private final static float CAMERA_MOVEMENT_MAX_DIST = 2;

    private GameMap map;

    private OrthographicCamera camera;
    private OrthographicCamera uiCamera;
    private GameInput input;

    private Vec2i playerPos;
    private Vec2f cameraPos;

    private Vec2i screenSize;
    private int displayDiv;

    private long stepTime;

    private long time;
    private long startTime;


    private CursorSet cursorSet; // Note! Disposed in main class!



    public GameState(
            Vec2i screenSize,
            int displayDiv) {
        this.screenSize = screenSize.copy();
        this.displayDiv = displayDiv;
        createCamera();
        input = new GameInput();
        startTime = TimeUtils.millis();
        time = startTime;

        map = new GameMap();
        playerPos = new Vec2i(0, 0);
        cameraPos = new Vec2f(0, 0);

        // Step time:
        stepTime = time;
    }

    public OrthographicCamera getCamera() {
        return camera;
    }

    public OrthographicCamera getUiCamera() {
        return uiCamera;
    }

    public GameInput getInput() {
        return input;
    }

    public Vec2f getRealPlayerPos() {
        return new Vec2f(
                playerPos.x * gridSize.x + gridSize.x / 2,
                (map.getHeight() - playerPos.y - 1) * gridSize.y +
                        // ^ invert vertical coordinates ('cause OpenGL)
                        gridSize.y / 2
        );
    }

    public Vec2i getPlayerPos() {
        return playerPos;
    }

    public Vec2f getCameraPos() {
        return cameraPos;
    }

    public void moveCameraTowardsPlayer() {
        Vec2f playerPos = getRealPlayerPos();
        float dist = cameraPos.dist(playerPos);
        if (cameraPos.dist(playerPos) > CAMERA_MOVEMENT_MAX_DIST) {
            float dx = playerPos.x - cameraPos.x;
            float dy = playerPos.y - cameraPos.y;
            float nx = dx * CAMERA_MOVEMENT_MAX_DIST / dist;
            float ny = dy * CAMERA_MOVEMENT_MAX_DIST / dist;
            cameraPos.x += nx;
            cameraPos.y += ny;
        }
    }

    public Vec2i getScreenSize() {
        return screenSize;
    }

    public int getDisplayDiv() {
        return displayDiv;
    }

    public long getTime() {
        return time;
    }

    public float getStateTime() {
        return (time - startTime) / 1000f;
    }

    public boolean readyForStep() {
        return time - stepTime > STEP_TIME_DELTA;
    }

    public void step() {
        stepTime = time;
    }

    public void updateTime() {
        time = TimeUtils.millis();
    }

    @Override
    public void dispose() {
        // TODO: EMPTY!!!
    }

    public void incrementDisplayDiv() {
        if (displayDiv < 8) {
            displayDiv *= 2;
            createCamera();
        }
    }

    public void decrementDisplayDiv() {
        if (displayDiv > 1) {
            displayDiv /= 2;
            createCamera();
        }
    }


    public boolean loadMap(String mapFileName) {
        if (map.loadMap(mapFileName)) {
            playerPos = map.getOriginalPlayerPos();
            cameraPos = getRealPlayerPos();
            return true;
        } else {
            return false;
        }
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

    public Vec2i getRelativeMouseGridPos() {
        Vec2f pos = input.getRelativeMouseCoord();
        return new Vec2i(
                (int) Math.floor(pos.getX() / gridSize.x),
                map.getHeight() - 1 - (int) Math.floor(
                        pos.y / gridSize.y
                ) // Invert vertical coordinates ('cause OpenGL)
        );
    }



    private void createCamera() {
        camera = new OrthographicCamera(
                screenSize.x / displayDiv,
                screenSize.y / displayDiv
        );
        uiCamera = new OrthographicCamera(screenSize.x, screenSize.y);
    }


    public void addCursorSet(CursorSet cursorSet) {
        this.cursorSet = cursorSet;
    }

    public CursorSet getCursorSet() {
        return cursorSet;
    }
}
