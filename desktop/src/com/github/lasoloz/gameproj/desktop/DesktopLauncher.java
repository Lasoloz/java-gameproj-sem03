package com.github.lasoloz.gameproj.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.github.lasoloz.gameproj.GameProj;

public class DesktopLauncher {
    public static void main (String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

        config.title = "The Secret Dungeon";
        config.width = 1024;
        config.height = 768;
        new LwjglApplication(new GameProj(), config);
    }
}
