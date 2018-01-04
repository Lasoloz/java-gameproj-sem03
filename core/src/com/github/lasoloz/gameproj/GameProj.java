package com.github.lasoloz.gameproj;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.github.lasoloz.gameproj.control.FieldRenderer;
import com.github.lasoloz.gameproj.control.GameController;
import com.github.lasoloz.gameproj.control.details.GameState;
import com.github.lasoloz.gameproj.control.details.Subject;
import com.github.lasoloz.gameproj.entitites.Blueprint;
import com.github.lasoloz.gameproj.entitites.BlueprintException;
import com.github.lasoloz.gameproj.entitites.BlueprintSet;
import com.github.lasoloz.gameproj.graphics.GraphicsException;
import com.github.lasoloz.gameproj.math.Vec2f;



public class GameProj extends ApplicationAdapter {
    private GameState gameState;
    private GameController gameController;
    private FieldRenderer fieldRenderer;


    @Override
    public void create() {
        try {
            BlueprintSet test = new BlueprintSet("blueprints/mainSet.bpset.json");

            ///
            gameState = new GameState(
                    new Vec2f(
                            Gdx.graphics.getWidth(),
                            Gdx.graphics.getHeight()
                    ),
                    4
            );
            if (!gameState.loadMap("maps/dungeon0.map.json")) {
                Gdx.app.error("GameProj", "Failed to load map!");
                Gdx.app.exit();
            }
            gameController = new GameController(gameState);
            fieldRenderer = new FieldRenderer();

            gameController.attach(fieldRenderer);
        } catch (BlueprintException ex) {
            System.out.println(ex.getMessage());
        }
    }


    @Override
    public void render() {
        Gdx.gl.glClearColor(.1f, .1f, .1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        gameController.update();
    }

    @Override
    public void resize(int width, int height) {
        gameController.resize(width, height);
    }

    @Override
    public void dispose() {
        fieldRenderer.dispose();
        gameState.dispose();
    }
}
