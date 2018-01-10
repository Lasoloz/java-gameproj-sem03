package com.github.lasoloz.gameproj;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL20;
import com.github.lasoloz.gameproj.control.FieldRenderer;
import com.github.lasoloz.gameproj.control.GameController;
import com.github.lasoloz.gameproj.control.InfoRenderer;
import com.github.lasoloz.gameproj.control.details.GameState;
import com.github.lasoloz.gameproj.graphics.CursorSet;
import com.github.lasoloz.gameproj.math.Vec2i;


public class GameProj extends ApplicationAdapter {
    private GameState gameState;
    private GameController gameController;
    private FieldRenderer fieldRenderer;
    private InfoRenderer infoRenderer;

    private CursorSet cursorSet;

    private FPSLogger fpsLogger;

    @Override
    public void create() {
        gameState = new GameState(
                new Vec2i(
                        Gdx.graphics.getWidth(),
                        Gdx.graphics.getHeight()
                ),
                4
        );
        if (!gameState.loadMap("maps/dungeon0.map.json")) {
            Gdx.app.error("GameProj", "Failed to load map!");
            Gdx.app.exit();
        }
        cursorSet = new CursorSet("other/");
        gameState.addCursorSet(cursorSet);

        gameController = new GameController(gameState);
        fieldRenderer = new FieldRenderer();
        infoRenderer = new InfoRenderer();

        gameController.attach(fieldRenderer);
        gameController.attach(infoRenderer);

        fpsLogger = new FPSLogger();
    }


    @Override
    public void render() {
        Gdx.gl.glClearColor(.1f, .1f, .1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        gameController.update();
        fpsLogger.log(); /// TODO: Get better alternative!
    }

    @Override
    public void resize(int width, int height) {
        gameController.resize(width, height);
    }

    @Override
    public void dispose() {
        gameController.detach(infoRenderer);
        gameController.detach(fieldRenderer);
        infoRenderer.dispose();
        fieldRenderer.dispose();
        gameState.dispose();
        cursorSet.dispose();
    }
}
