package com.github.lasoloz.gameproj.control.details;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.TimeUtils;
import com.github.lasoloz.gameproj.control.GameInput;
import com.github.lasoloz.gameproj.graphics.CursorSet;
import com.github.lasoloz.gameproj.math.Vec2f;
import com.github.lasoloz.gameproj.math.Vec2i;

import java.util.ArrayList;

/**
 * Class used as a collection of all game state specific data
 * Private members:
 * map - Game map object
 * camera - Camera used for field rendering
 * uiCamera - Camera used for information rendering
 * input - Object collecting input information
 * playerPos - Players position on map
 * currentEnemyPositions - enemy positions on the map
 * cameraPos - current camera position
 * screenSize - Current screen size
 * displayDiv - Number used for dividing screen for a zoomed "pixelish" style
 * stepTime - How long should one step last (time between two actions)
 * time - Current time in the game
 * startTime - Start time of the game
 * cursorSet - Cursor collection for the game cursors
 */
public class GameState {
    public final static Vec2f gridSize = new Vec2f(28f, 16f);
    private final static long STEP_TIME_DELTA = 250;
    private final static float CAMERA_MOVEMENT_MAX_DIST = 2;

    private GameMap map;

    private OrthographicCamera camera;
    private OrthographicCamera uiCamera;
    private GameInput input;

    private Vec2i playerPos;
    private ArrayList<Vec2i> currentEnemyPositions;
    private Vec2f cameraPos;

    private Vec2i screenSize;
    private int displayDiv;

    private long stepTime;

    private long time;
    private long startTime;

    private boolean playerWon;


    private CursorSet cursorSet; // Note! Disposed in main class!


    /**
     * Constructor
     * @param screenSize Default screen size
     * @param displayDiv Default display divider
     */
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

    /**
     * Get orthographic field renderer camera
     * @return Field renderer camera
     */
    public OrthographicCamera getCamera() {
        return camera;
    }

    /**
     * Get information renderer ui camera
     * @return Information renderer camera
     */
    public OrthographicCamera getUiCamera() {
        return uiCamera;
    }

    /**
     * Get input object
     * @return Input object for, which is responsible for collecting input
     */
    public GameInput getInput() {
        return input;
    }

    /**
     * Get real position of the player (position on camera, not in map tiles
     * @return Vector specifying position in the world
     */
    public Vec2f getRealPlayerPos() {
        return new Vec2f(
                playerPos.x * gridSize.x + gridSize.x / 2,
                (map.getHeight() - playerPos.y - 1) * gridSize.y +
                        // ^ invert vertical coordinates ('cause OpenGL)
                        gridSize.y / 2
        );
    }

    /**
     * Get playr position (position on map)
     * @return Vector specifying player's position
     */
    public Vec2i getPlayerPos() {
        return playerPos;
    }

    /**
     * Get camera position
     * @return Vector specifying camera position
     */
    public Vec2f getCameraPos() {
        return cameraPos;
    }

    /**
     * Move camera towards player, with a maximum speed
     */
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

    /**
     * Get size of the screen
     * @return Vector specifying screen size
     */
    public Vec2i getScreenSize() {
        return screenSize;
    }

    /**
     * Get display divider number
     * @return Number used for dividing display
     */
    public int getDisplayDiv() {
        return displayDiv;
    }

    /**
     * Get time current game time
     * @return Current game time
     */
    public long getTime() {
        return time;
    }

    /**
     * Get current state time used for animating units
     * @return Current state time
     */
    public float getStateTime() {
        return (time - startTime) / 1000f;
    }

    /**
     * Return if we are ready for step (move again)
     * @return `readyForStep` status
     */
    public boolean readyForStep() {
        return time - stepTime > STEP_TIME_DELTA;
    }

    /**
     * Step one (action happened now)
     */
    public void step() {
        stepTime = time;
    }

    /**
     * Update current time
     */
    public void updateTime() {
        time = TimeUtils.millis();
    }

    /**
     * Increment display div (zoom in)
     */
    public void incrementDisplayDiv() {
        if (displayDiv < 8) {
            displayDiv *= 2;
            createCamera();
        }
    }

    /**
     * Decrement display div (zoom out)
     */
    public void decrementDisplayDiv() {
        if (displayDiv > 1) {
            displayDiv /= 2;
            createCamera();
        }
    }


    /**
     * Load map
     * @param mapFileName map file name
     * @return Success state of load
     */
    public boolean loadMap(String mapFileName) {
        if (map.loadMap(mapFileName)) {
            playerPos = map.getOriginalPlayerPos();
            cameraPos = getRealPlayerPos();
            currentEnemyPositions = map.getOriginalEnemyPositions();
            playerWon = false;
            return true;
        } else {
            return false;
        }
    }

    /**
     * Get current map
     * @return Game map object
     */
    public GameMap getMap() {
        return map;
    }

    /**
     * Get real world map width
     * @return Map width multiplied with grid cell with
     */
    public float getMapWidth() {
        return gridSize.getX() * map.getWidth();
    }

    /**
     *Get real world map height
     * @return Map height multiplied with grid cell height
     */
    public float getMapHeight() {
        return gridSize.getY() * map.getHeight();
    }

    /**
     * Get mouse position on the game map grid
     * @return Vector specifying mouse position on the grid
     */
    public Vec2i getRelativeMouseGridPos() {
        Vec2f pos = input.getRelativeMouseCoord();
        return new Vec2i(
                (int) Math.floor(pos.getX() / gridSize.x),
                map.getHeight() - 1 - (int) Math.floor(
                        pos.y / gridSize.y
                ) // Invert vertical coordinates ('cause OpenGL)
        );
    }


    /**
     * Create cameras
     */
    private void createCamera() {
        camera = new OrthographicCamera(
                screenSize.x / displayDiv,
                screenSize.y / displayDiv
        );
        uiCamera = new OrthographicCamera(screenSize.x, screenSize.y);
    }


    /**
     * Add cursor set
     * @param cursorSet Add cursor set for rendering
     */
    public void addCursorSet(CursorSet cursorSet) {
        this.cursorSet = cursorSet;
    }

    /**
     * Get cursor set
     * @return Cursor set containing multiple cursor pixmaps
     */
    public CursorSet getCursorSet() {
        return cursorSet;
    }


    /**
     * Get current enemy positions
     * @return list contaning enemy positions
     */
    public ArrayList<Vec2i> getCurrentEnemyPositions() {
        return currentEnemyPositions;
    }

    /**
     * Reset enemy positions (deletes old enemy positions
     */
    public void resetEnemyPositions() {
        currentEnemyPositions = new ArrayList<Vec2i>(
                currentEnemyPositions.size()
        );
    }

    /**
     *  Adds an enemy position to the new enmey positions array
     * @param enemyPos Position of an enemy waiting to be registered
     */
    public void addEnemyPosition(Vec2i enemyPos) {
        this.currentEnemyPositions.add(enemyPos);
    }


    /**
     * Set win status to true
     */
    public void win() {
        playerWon = true;
    }

    /**
     * Return if player won
     * @return Win condition
     */
    public boolean didPlayerWin() {
        return playerWon;
    }
}
