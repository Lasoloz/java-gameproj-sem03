package com.github.lasoloz.gameproj;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL20;
import com.github.lasoloz.gameproj.control.FieldRenderer;
import com.github.lasoloz.gameproj.control.GameController;
import com.github.lasoloz.gameproj.control.InfoRenderer;
import com.github.lasoloz.gameproj.ui.UIManager;
import com.github.lasoloz.gameproj.ui.Updatable;


public class GameProj extends ApplicationAdapter {
    private FieldRenderer fieldRenderer;
    private InfoRenderer infoRenderer;

    private FPSLogger fpsLogger;


    private UIManager uiManager;
    private Updatable manager;
    private boolean inMenu;

    @Override
    public void create() {
        uiManager = UIManager.getUIManager();
        manager = uiManager;
        inMenu = true;

        fieldRenderer = new FieldRenderer();
        infoRenderer = new InfoRenderer();

        fpsLogger = new FPSLogger();
    }


    @Override
    public void render() {
        Gdx.gl.glClearColor(.1f, .1f, .1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (!manager.update()) {
            if (inMenu) {
                GameController gc = uiManager.getGameController();
                gc.attach(fieldRenderer);
                gc.attach(infoRenderer);
                manager = gc;
                inMenu = false;
            } else {
                inMenu = true;
                manager = uiManager;
                uiManager.overrideControl();
            }
        }

        fpsLogger.log(); /// TODO: Get better alternative!
    }

    @Override
    public void resize(int width, int height) {
        manager.resize(width, height);
    }

    @Override
    public void dispose() {
//        gameController.detach(infoRenderer);
//        gameController.detach(fieldRenderer);
        infoRenderer.dispose();
        fieldRenderer.dispose();
        uiManager.dispose();
//        cursorSet.dispose();
    }
}
