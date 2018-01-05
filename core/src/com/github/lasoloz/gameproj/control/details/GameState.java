package com.github.lasoloz.gameproj.control.details;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.TimeUtils;
import com.github.lasoloz.gameproj.control.GameInput;
import com.github.lasoloz.gameproj.math.Vec2f;
import javafx.util.Pair;

public class GameState implements Disposable {
    public final static Vec2f gridSize = new Vec2f(28f, 16f);

    private GameMap map;

    private OrthographicCamera camera;
    private GameInput input;
    private Vec2f playerPos;
    private Vec2f screenSize;
    private int displayDiv;

    private long time;
    private long startTime;
    private long displayDivChangeTime;



    public GameState(
            Vec2f screenSize,
            int displayDiv) {
        this.screenSize = screenSize.copy();
        this.playerPos = new Vec2f(0f, 0f);
        this.displayDiv = displayDiv;
        createCamera();
        input = new GameInput();
        startTime = TimeUtils.millis();
        time = startTime;
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

    public long getTime() {
        return time;
    }

    public float getStateTime() {
        return (time - startTime) / 1000f;
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


    public int[] getRelativeMouseGridPos() {
        Vec2f pos = input.getRelativeMouseCoord();
        int[] result = new int[2];
        result[0] = (int) Math.floor(pos.getX() / gridSize.getX());
        result[1] = map.getHeight() - 1 - (int) Math.floor(
                pos.getY() / gridSize.getY()
        );
        return result;
    }



    private void createCamera() {
        camera = new OrthographicCamera(
                screenSize.getX() / displayDiv,
                screenSize.getY() / displayDiv
        );
    }
}
