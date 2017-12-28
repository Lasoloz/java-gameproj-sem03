package com.github.lasoloz.gameproj.control;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.github.lasoloz.gameproj.control.details.GameState;
import com.github.lasoloz.gameproj.control.details.Subject;
import com.github.lasoloz.gameproj.math.Vec2f;

public class GameController extends Subject {
    public GameController(GameState gameState) {
        super(gameState);
        Gdx.input.setInputProcessor(gameState.getInput());
    }


    @Override
    public void update() {
        gameState.updateTime();
        int scrollState = gameState.getInput().getScrollState();
        if (scrollState < 0) {
            gameState.incrementDisplayDiv();
        } else if (scrollState > 0) {
            gameState.decrementDisplayDiv();
        }
        gameState.getCamera().update();
        updateCamera();
        super.update();
    }


    public void resize(int width, int height) {
        OrthographicCamera camera = gameState.getCamera();
        camera.viewportWidth = width / gameState.getDisplayDiv();
        camera.viewportHeight = height / gameState.getDisplayDiv();
        Vec2f screenSize = gameState.getScreenSize();
        screenSize.setX(width);
        screenSize.setY(height);
    }



    private void updateCamera() {
        GameInput input = gameState.getInput();
        Vec2f f = input.getCameraFocus(
                gameState.getPlayerPos(),
                gameState.getScreenSize(),
                gameState.getDisplayDiv()
        );
        OrthographicCamera camera = gameState.getCamera();
        camera.position.x = f.getX();
        camera.position.y = f.getY();
    }
}
