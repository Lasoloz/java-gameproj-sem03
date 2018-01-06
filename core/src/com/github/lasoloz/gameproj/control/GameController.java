package com.github.lasoloz.gameproj.control;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.github.lasoloz.gameproj.blueprints.Direction;
import com.github.lasoloz.gameproj.control.details.GameState;
import com.github.lasoloz.gameproj.control.details.Subject;
import com.github.lasoloz.gameproj.math.Vec2f;
import com.github.lasoloz.gameproj.math.Vec2i;

public class GameController extends Subject {
    public GameController(GameState gameState) {
        super(gameState);
        Gdx.input.setInputProcessor(gameState.getInput());
    }


    @Override
    public void update() {
        gameState.updateTime();

        updateUnits();

        int scrollState = gameState.getInput().getScrollState();
        if (scrollState < 0) {
            gameState.incrementDisplayDiv();
        } else if (scrollState > 0) {
            gameState.decrementDisplayDiv();
        }
        updateCamera();
        super.update();
    }


    public void resize(int width, int height) {
        int displayDiv = gameState.getDisplayDiv();
        OrthographicCamera camera = gameState.getCamera();
        camera.viewportWidth = width / displayDiv;
        camera.viewportHeight = height / displayDiv;

        OrthographicCamera uiCamera = gameState.getUiCamera();
        uiCamera.viewportWidth = width;
        uiCamera.viewportHeight = height;

        Vec2i screenSize = gameState.getScreenSize();
        screenSize.x = width;
        screenSize.y = height;
    }



    private void updateCamera() {
        // Get field renderer camera, and update based on player position:
        GameInput input = gameState.getInput();
        Vec2f f = input.getCameraFocus(
                gameState.getRealPlayerPos(),
                gameState.getScreenSize(),
                gameState.getDisplayDiv()
        );
        OrthographicCamera camera = gameState.getCamera();
        camera.position.x = f.getX();
        camera.position.y = f.getY();
        camera.update();

        // UI renderer camera:
        OrthographicCamera uiCamera = gameState.getUiCamera();
        Vec2i screenSize = gameState.getScreenSize();
        uiCamera.position.x = screenSize.x / 2;
        uiCamera.position.y = screenSize.y / 2;
        uiCamera.update();
    }


    private void updateUnits() {
        if (gameState.isWaitingForPlayer()) {
            // Check, if player did something (mouse click)
            GameInput gameInput = gameState.getInput();

            if (gameInput.isLeftPressed()) {
                System.out.println(getDirectionFromCoord());
            }
        } else {
            UnitLogic.updateUnits(gameState);
        }
    }

    private Direction getDirectionFromCoord() {
        Vec2i mouseCoord = gameState.getInput().getMouseCoord();
        Vec2i screenSize = gameState.getScreenSize();

        float screenRatio = (float) screenSize.y / (float) screenSize.x;
        float mainRatio = (float) mouseCoord.y / mouseCoord.x;
        float secondaryRatio = (float) mouseCoord.y / (float) (
                screenSize.x - mouseCoord.x
        );

        if (mainRatio > screenRatio) {
            if (secondaryRatio > screenRatio) {
                return Direction.DIR_SOUTH;
            } else {
                return Direction.DIR_WEST;
            }
        } else {
            if (secondaryRatio > screenRatio) {
                return Direction.DIR_EAST;
            } else {
                return Direction.DIR_NORTH;
            }
        }
    }
}
